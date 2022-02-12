package com.example.martatraintimeremake.data.remote

import com.example.martatraintimeremake.domain.model.Train
import retrofit2.Response
import retrofit2.http.GET

interface TrainApi {

    @GET("/RealtimeTrain/RestServiceNextTrain/GetRealtimeArrivals")
    suspend fun getTrains(): List<Train>

    companion object {
        const val BASE_URL = "http://developer.itsmarta.com/"
    }
}