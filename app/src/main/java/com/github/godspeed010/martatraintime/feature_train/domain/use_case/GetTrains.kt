package com.github.godspeed010.martatraintime.feature_train.domain.use_case

import com.github.godspeed010.martatraintime.feature_train.domain.model.Train
import com.github.godspeed010.martatraintime.feature_train.domain.repository.TrainRepository

class GetTrains(
    private val repository: TrainRepository
) {

    suspend operator fun invoke(): List<Train> {
        return repository.getTrains()
    }
}