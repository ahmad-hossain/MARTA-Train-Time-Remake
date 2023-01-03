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
                    is TrainOrder.Station -> trains.sortedBy { it.station.lowercase() }
                    is TrainOrder.Line -> trains.sortedBy { it.line.lowercase() }
                    is TrainOrder.Direction -> trains.sortedBy { it.direction.lowercase() }
                    is TrainOrder.WaitTime -> trains.sortedBy { it.waitingTime.appropriateSortingValue() }
                }
            }
            is OrderType.Descending -> {
                when (trainOrder) {
                    is TrainOrder.Station -> trains.sortedByDescending { it.station.lowercase() }
                    is TrainOrder.Line -> trains.sortedByDescending { it.line.lowercase() }
                    is TrainOrder.Direction -> trains.sortedByDescending { it.direction.lowercase() }
                    is TrainOrder.WaitTime -> trains.sortedByDescending { it.waitingTime.appropriateSortingValue() }
                }
            }
        }
    }
}