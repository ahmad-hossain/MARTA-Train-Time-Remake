package com.github.godspeed010.martatraintime.feature_train.data.remote

import com.github.godspeed010.martatraintime.feature_train.domain.model.Train
import retrofit2.http.GET

interface TrainApi {

    @GET("/RealtimeTrain/RestServiceNextTrain/GetRealtimeArrivals")
    suspend fun getTrains(): List<Train>

    companion object {
        const val BASE_URL = "http://developer.itsmarta.com/"
    }
}