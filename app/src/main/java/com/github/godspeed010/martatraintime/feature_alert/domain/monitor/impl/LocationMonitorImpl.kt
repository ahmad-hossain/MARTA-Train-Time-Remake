package com.github.godspeed010.martatraintime.feature_alert.domain.monitor.impl

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.github.godspeed010.martatraintime.feature_alert.domain.monitor.LocationMonitor
import com.google.android.gms.location.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LocationMonitorImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : LocationMonitor {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private val mutableLocationFlow = MutableSharedFlow<Location>()
    override val locationFlow: SharedFlow<Location>
        get() = mutableLocationFlow.asSharedFlow()

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val locationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY, LOCATION_MAX_UPDATE_INTERVAL_MS
    ).apply {
        setMinUpdateIntervalMillis(LOCATION_MIN_UPDATE_INTERVAL_MS)
        setIntervalMillis(LOCATION_MAX_UPDATE_INTERVAL_MS)
        setMinUpdateDistanceMeters(LOCATION_MIN_UPDATE_DISTANCE_M)
    }.build()
    private val locationListener = LocationListener {
        scope.launch { mutableLocationFlow.emit(it) }
    }

    @SuppressLint("MissingPermission")
    override fun start() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            Executor { it.run() },
            locationListener,
        )
    }

    override fun stop() {
        job.cancel()
        fusedLocationProviderClient.removeLocationUpdates(locationListener)
    }

    companion object {
        private val LOCATION_MAX_UPDATE_INTERVAL_MS = TimeUnit.SECONDS.toMillis(10L)
        private val LOCATION_MIN_UPDATE_INTERVAL_MS = TimeUnit.SECONDS.toMillis(6L)
        private const val LOCATION_MIN_UPDATE_DISTANCE_M = 15f
    }
}