package com.github.godspeed010.martatraintime.feature_train.domain.use_case

import com.github.godspeed010.martatraintime.feature_train.domain.model.Train
import com.github.godspeed010.martatraintime.feature_train.domain.repository.TrainRepository
import com.google.transit.realtime.GtfsRealtime

class GetTrains(
    private val repository: TrainRepository
) {

    suspend operator fun invoke(): GtfsRealtime.FeedMessage {
        return repository.getGtfs()
    }
}