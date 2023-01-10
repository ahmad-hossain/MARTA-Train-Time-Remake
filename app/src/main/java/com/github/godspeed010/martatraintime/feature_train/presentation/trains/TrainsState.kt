package com.github.godspeed010.martatraintime.feature_train.presentation.trains

import com.github.godspeed010.martatraintime.feature_train.domain.model.Train
import com.github.godspeed010.martatraintime.feature_train.domain.util.OrderType
import com.github.godspeed010.martatraintime.feature_train.domain.util.TrainOrder

data class TrainsState(
    val trains: List<Train> = emptyList(),
    val trainOrder: TrainOrder = TrainOrder.Line(OrderType.Descending),
    val isFilterDropdownExpanded: Boolean = false,
    val isSearchSectionVisible: Boolean = false,
    val searchQuery: String = "",
    val displayedTrainList: List<Train> = emptyList(),
    val isRefreshing: Boolean = false,
    val lastRefreshTimeSecs: Long = 0L,
)
