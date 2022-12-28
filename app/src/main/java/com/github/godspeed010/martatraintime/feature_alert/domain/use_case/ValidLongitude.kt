package com.github.godspeed010.martatraintime.feature_alert.domain.use_case

import javax.inject.Inject

class ValidLongitude @Inject constructor() {

    operator fun invoke(longitude: String): Boolean {
        return longitude.toIntOrNull() in -180..180
    }
}