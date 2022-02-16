package com.github.godspeed010.martatraintime.feature_train.domain.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}