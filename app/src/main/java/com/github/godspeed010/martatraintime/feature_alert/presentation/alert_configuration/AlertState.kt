package com.github.godspeed010.martatraintime.feature_alert.presentation.alert_configuration

data class AlertState(
    val latitudeText: String = "",
    val longitudeText: String = "",
    val latitudeNum: Double = 0.0,
    val longitudeNum: Double = 0.0,
    val isButtonEnabled: Boolean = true,
)