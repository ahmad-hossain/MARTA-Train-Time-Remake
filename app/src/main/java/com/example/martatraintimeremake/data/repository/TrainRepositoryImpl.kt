package com.example.martatraintimeremake.data.repository

import com.example.martatraintimeremake.data.remote.TrainApi
import com.example.martatraintimeremake.domain.model.Train
import com.example.martatraintimeremake.domain.repository.TrainRepository
import retrofit2.Response

class TrainRepositoryImpl(
    private val api: TrainApi
) : TrainRepository {

    override suspend fun getTrains(): List<Train> {
        //todo handle IOException and HttpException
        return api.getTrains()
    }
}