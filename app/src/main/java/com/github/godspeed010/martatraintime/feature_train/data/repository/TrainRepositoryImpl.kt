package com.github.godspeed010.martatraintime.feature_train.data.repository

import android.util.Log
import com.github.godspeed010.martatraintime.feature_train.data.remote.TrainApi
import com.github.godspeed010.martatraintime.feature_train.domain.model.Train
import com.github.godspeed010.martatraintime.feature_train.domain.repository.TrainRepository
import retrofit2.HttpException
import java.io.IOException

private const val TAG = "TrainRepositoryImpl"

class TrainRepositoryImpl(
    private val api: TrainApi
) : TrainRepository {

    override suspend fun getTrains(): List<Train> {
        //todo handle IOException and HttpException
        try {
            return api.getTrains()
        } catch (e: IOException) {
            Log.e(TAG, "IOException $e.")
        } catch (e: HttpException) {
            Log.e(TAG, "HttpException ${e.code()}")
        }
        return emptyList()
    }
}