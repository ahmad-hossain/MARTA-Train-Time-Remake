package com.github.godspeed010.martatraintime.feature_alert.domain.use_case

import javax.inject.Inject

class ValidLatitude @Inject constructor() {

    operator fun invoke(latitude: String): Boolean {
        return latitude.toIntOrNull() in -90..90
    }
}