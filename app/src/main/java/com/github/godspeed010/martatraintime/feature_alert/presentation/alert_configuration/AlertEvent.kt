package com.github.godspeed010.martatraintime.feature_alert.presentation.alert_configuration

import android.content.Context

sealed class AlertEvent {
    data class StartClicked(val context: Context) : AlertEvent()
    data class LatitudeEntered(val latitude: String) : AlertEvent()
    data class LongitudeEntered(val longitude: String) : AlertEvent()
}
