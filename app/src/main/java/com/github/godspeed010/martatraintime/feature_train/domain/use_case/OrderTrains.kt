package com.github.godspeed010.martatraintime.feature_train.domain.use_case

import com.github.godspeed010.martatraintime.feature_train.domain.model.Train
import com.github.godspeed010.martatraintime.feature_train.domain.util.OrderType
import com.github.godspeed010.martatraintime.feature_train.domain.util.SortingHelper.appropriateSortingValue
import com.github.godspeed010.martatraintime.feature_train.domain.util.TrainOrder

class OrderTrains {

    operator fun invoke(
        trains: List<Train> = emptyList(),
        trainOrder: TrainOrder = TrainOrder.Line(OrderType.Descending)
    ): List<Train> {
        return when (trainOrder.orderType) {
            is OrderType.Ascending -> {
                when (trainOrder) {
                    is TrainOrder.Station -> trains.sortedBy { it.STATION.lowercase() }
                    is TrainOrder.Line -> trains.sortedBy { it.LINE.lowercase() }
                    is TrainOrder.Direction -> trains.sortedBy { it.DIRECTION.lowercase() }
                    is TrainOrder.WaitTime -> trains.sortedBy { it.WAITING_TIME.appropriateSortingValue() }
                }
            }
            is OrderType.Descending -> {
                when (trainOrder) {
                    is TrainOrder.Station -> trains.sortedByDescending { it.STATION.lowercase() }
                    is TrainOrder.Line -> trains.sortedByDescending { it.LINE.lowercase() }
                    is TrainOrder.Direction -> trains.sortedByDescending { it.DIRECTION.lowercase() }
                    is TrainOrder.WaitTime -> trains.sortedByDescending { it.WAITING_TIME.appropriateSortingValue() }
                }
            }
        }
    }
}