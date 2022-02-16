package com.github.godspeed010.martatraintime.feature_train.domain.util

object SortingHelper {

    //if WAITING_TIME contains only text (e.g Arriving), return Int 0,
    //else return parsed Int from WAITING_TIME
    fun String.appropriateSortingValue(): Int {
        return if (".*\\d.*".toRegex().matches(this)) Integer.parseInt(this.split(" ")[0]) else 0
    }
}