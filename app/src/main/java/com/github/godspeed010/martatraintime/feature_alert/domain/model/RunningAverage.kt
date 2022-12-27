package com.github.godspeed010.martatraintime.feature_alert.domain.model

data class RunningAverage(
    val runningTotal: Float,
    val dataPoints: Int,
) {
    val average: Float
        get() = runningTotal / dataPoints
}