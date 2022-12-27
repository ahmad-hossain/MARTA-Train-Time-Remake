package com.github.godspeed010.martatraintime.feature_alert.domain.model

import android.location.Location

data class NotificationState(
    val runningAverage: RunningAverage = RunningAverage(0f, 0),
    val lastLocation: Location? = null,
)
