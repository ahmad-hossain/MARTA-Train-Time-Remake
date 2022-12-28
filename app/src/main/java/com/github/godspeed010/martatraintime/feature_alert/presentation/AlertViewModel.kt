package com.github.godspeed010.martatraintime.feature_alert.presentation

import android.Manifest
import android.content.Intent
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.github.godspeed010.martatraintime.feature_alert.domain.service.LocationMonitorService
import com.github.godspeed010.martatraintime.feature_alert.domain.use_case.AlertUseCases
import com.github.godspeed010.martatraintime.feature_alert.presentation.alert_configuration.AlertEvent
import com.github.godspeed010.martatraintime.feature_alert.presentation.alert_configuration.AlertState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "AlertViewModel"

@HiltViewModel
class AlertViewModel @Inject constructor(
    private val alertUseCases: AlertUseCases,
) : ViewModel() {

    var state by mutableStateOf(AlertState())
        private set

    val requiredPermissions = mutableListOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    )
        get() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                field.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            }
            return field
        }

    fun onEvent(event: AlertEvent) {
        when (event) {
            is AlertEvent.StartClicked -> {
                Log.i(TAG, "StartClicked")

                val destinationLocation = Location("destination").apply {
                    latitude = state.latitudeNum
                    longitude = state.longitudeNum
                }
                val intent = Intent(event.context, LocationMonitorService::class.java)
                intent.apply {
                    action = LocationMonitorService.ACTION_START_SERVICE
                    putExtra(LocationMonitorService.EXTRA_DESTINATION_LOCATION, destinationLocation)
                }
                with(event.context) {
                    stopService(intent)
                    startService(intent)
                }
            }
            is AlertEvent.LatitudeEntered -> {
                val isTextValidLat = alertUseCases.validLatitude(event.latitude)
                val newLatitudeNum = if (isTextValidLat) event.latitude.toDouble() else 0.0

                state = state.copy(
                    latitudeText = event.latitude,
                    latitudeNum = newLatitudeNum,
                    isButtonEnabled = true // TODO should only be enabled if both TextFields have no error
                )
            }
            is AlertEvent.LongitudeEntered -> {
                val isTextValidLon = alertUseCases.validLongitude(event.longitude)
                val newLongitudeNum = if (isTextValidLon) event.longitude.toDouble() else 0.0

                state = state.copy(
                    longitudeText = event.longitude,
                    longitudeNum = newLongitudeNum,
                    isButtonEnabled = true // TODO
                )
            }
        }
    }
}