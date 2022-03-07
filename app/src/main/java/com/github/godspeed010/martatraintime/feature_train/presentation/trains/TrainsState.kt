package com.github.godspeed010.martatraintime.feature_train.presentation.trains

import com.github.godspeed010.martatraintime.feature_train.domain.model.Train
import com.github.godspeed010.martatraintime.feature_train.domain.util.TrainOrder
import com.github.godspeed010.martatraintime.feature_train.domain.util.OrderType

data class TrainsState(
    val trains: List<Train> = emptyList(),
    val trainOrder: TrainOrder = TrainOrder.Line(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
    val isSearchSectionVisible: Boolean = false,
    val searchQuery: String = "",
    val displayedTrainList: List<Train> = emptyList()
)
