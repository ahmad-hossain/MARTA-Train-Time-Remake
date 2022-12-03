package com.github.godspeed010.martatraintime.feature_alert.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.github.godspeed010.martatraintime.feature_alert.presentation.alert_configuration.AlertEvent
import com.github.godspeed010.martatraintime.feature_alert.presentation.alert_configuration.AlertState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlertViewModel @Inject constructor(): ViewModel() {

    var alertScreenState = mutableStateOf(AlertState())
        private set

    fun onEvent(event: AlertEvent) {
        when(event) {
            AlertEvent.StartClicked -> {
                TODO()
            }
            is AlertEvent.LatitudeEntered -> {
                TODO()
            }
            is AlertEvent.LongitudeEntered -> TODO()
        }
    }
}