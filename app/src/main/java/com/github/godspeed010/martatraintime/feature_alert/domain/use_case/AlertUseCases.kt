package com.github.godspeed010.martatraintime.feature_alert.domain.use_case

import javax.inject.Inject

data class AlertUseCases @Inject constructor(
    val validLatitude: ValidLatitude,
    val validLongitude: ValidLongitude,
)