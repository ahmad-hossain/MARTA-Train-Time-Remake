package com.github.godspeed010.martatraintime.feature_train.presentation.trains

import com.github.godspeed010.martatraintime.feature_train.domain.util.TrainOrder

sealed class TrainsEvent {
    data class Order(val trainOrder: TrainOrder) : TrainsEvent()
    object ToggleOrderSection : TrainsEvent()
}