package com.github.godspeed010.martatraintime.feature_train.domain.util

sealed class TrainOrder(val orderType: OrderType) {
    class Station(orderType: OrderType) : TrainOrder(orderType)
    class Line(orderType: OrderType) : TrainOrder(orderType)
    class Direction(orderType: OrderType) : TrainOrder(orderType)
    class WaitTime(orderType: OrderType) : TrainOrder(orderType)

    fun copy(orderType: OrderType): TrainOrder {
        return when (this) {
            is Station -> Station(orderType)
            is Line -> Line(orderType)
            is Direction -> Direction(orderType)
            is WaitTime -> WaitTime(orderType)
        }
    }
}