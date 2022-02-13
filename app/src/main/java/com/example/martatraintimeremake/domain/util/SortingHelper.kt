package com.example.martatraintimeremake.domain.util

object SortingHelper {

    //if WAITING_TIME contains only text (e.g Arriving), return Int 0,
    //else return parsed Int
    fun String.appropriateSortingValue(): Int {
        return if (".*\\d.*".toRegex().matches(this)) Integer.parseInt(this.split(" ")[0]) else 0
    }
}