package com.example.martatraintimeremake.presentation.trains

import com.example.martatraintimeremake.domain.model.Train
import com.example.martatraintimeremake.domain.util.TrainOrder
import com.example.martatraintimeremake.domain.util.OrderType

data class TrainsState(
    val trains: List<Train> = emptyList(),
    val trainOrder: TrainOrder = TrainOrder.Line(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
