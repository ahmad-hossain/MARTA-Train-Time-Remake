package com.github.godspeed010.martatraintime.feature_train.data.repository

import com.github.godspeed010.martatraintime.feature_train.data.remote.TrainApi
import com.github.godspeed010.martatraintime.feature_train.domain.model.Train
import com.github.godspeed010.martatraintime.feature_train.domain.repository.TrainRepository
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class TrainRepositoryImpl(
    private val api: TrainApi
) : TrainRepository {

    override suspend fun getTrains(): List<Train> {
        try {
            return api.getTrains()
        } catch (e: IOException) {
            Timber.e(e, "IOException")
        } catch (e: HttpException) {
            Timber.e(e, "HttpException ${e.code()}")
        }
        return emptyList()
    }
}