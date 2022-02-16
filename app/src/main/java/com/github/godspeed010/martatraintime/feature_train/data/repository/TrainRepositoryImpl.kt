package com.github.godspeed010.martatraintime.feature_train.data.repository

import com.github.godspeed010.martatraintime.feature_train.data.remote.TrainApi
import com.github.godspeed010.martatraintime.feature_train.domain.model.Train
import com.github.godspeed010.martatraintime.feature_train.domain.repository.TrainRepository

class TrainRepositoryImpl(
    private val api: TrainApi
) : TrainRepository {

    override suspend fun getTrains(): List<Train> {
        //todo handle IOException and HttpException
        return api.getTrains()
    }
}