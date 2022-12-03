package com.github.godspeed010.martatraintime.feature_alert.presentation.alert_configuration

sealed class AlertEvent {
    object StartClicked : AlertEvent()
    data class LatitudeEntered(val latitude: String) : AlertEvent()
    data class LongitudeEntered(val longitude: String) : AlertEvent()
}
