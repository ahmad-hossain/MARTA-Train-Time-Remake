package com.github.godspeed010.martatraintime.feature_alert.domain.monitor

import android.location.Location
import kotlinx.coroutines.flow.SharedFlow

interface LocationMonitor {

    val locationFlow: SharedFlow<Location>

    fun start()

    fun stop()
}