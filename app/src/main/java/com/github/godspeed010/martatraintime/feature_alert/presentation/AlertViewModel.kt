package com.github.godspeed010.martatraintime.feature_alert.presentation

import android.Manifest
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.github.godspeed010.martatraintime.feature_alert.domain.monitor.LocationMonitor
import com.github.godspeed010.martatraintime.feature_alert.presentation.alert_configuration.AlertEvent
import com.github.godspeed010.martatraintime.feature_alert.presentation.alert_configuration.AlertState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "AlertViewModel"

@HiltViewModel
class AlertViewModel @Inject constructor(): ViewModel() {

    var alertScreenState = mutableStateOf(AlertState())
        private set

    val requiredPermissions = mutableListOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    )
    get() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            field.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            return field
        }
        return field
    }

    fun onEvent(event: AlertEvent) {
        when(event) {
            is AlertEvent.StartClicked -> {
                Log.i(TAG, "StartClicked")

                val intent = Intent(event.context, LocationMonitor::class.java)
                event.context.startService(intent)
            }
            is AlertEvent.LatitudeEntered -> {
                TODO()
            }
            is AlertEvent.LongitudeEntered -> {
                // if not valid coord, disable button
                // else, enable btn if needed. update coord
            }
        }
    }
}