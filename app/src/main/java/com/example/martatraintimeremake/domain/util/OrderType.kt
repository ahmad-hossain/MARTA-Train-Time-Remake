package com.example.martatraintimeremake.domain.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}