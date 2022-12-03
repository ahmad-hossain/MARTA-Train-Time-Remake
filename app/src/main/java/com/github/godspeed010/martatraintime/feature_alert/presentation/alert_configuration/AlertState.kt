package com.github.godspeed010.martatraintime.feature_alert.presentation.alert_configuration

data class AlertState(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val isButtonEnabled: Boolean = true,
)