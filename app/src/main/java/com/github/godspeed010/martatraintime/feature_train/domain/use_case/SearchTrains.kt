package com.github.godspeed010.martatraintime.feature_train.domain.use_case

import com.github.godspeed010.martatraintime.feature_train.domain.model.Train

class SearchTrains {

    operator fun invoke(
        trains: List<Train>,
        searchQuery: String
    ): List<Train> {
        return trains.filter { train -> train.station.contains(searchQuery, ignoreCase = true) }
    }
}