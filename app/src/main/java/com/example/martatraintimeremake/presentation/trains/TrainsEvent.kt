package com.example.martatraintimeremake.presentation.trains

import com.example.martatraintimeremake.domain.util.TrainOrder

sealed class TrainsEvent {
    data class Order(val trainOrder: TrainOrder) : TrainsEvent()
    object ToggleOrderSection : TrainsEvent()
}