package com.github.godspeed010.martatraintime.feature_train.data.remote

import com.github.godspeed010.martatraintime.feature_train.domain.model.Train
import okhttp3.ResponseBody
import retrofit2.http.GET

interface TrainApi {

    @GET("/RealtimeTrain/RestServiceNextTrain/GetRealtimeArrivals")
    suspend fun getTrains(): List<Train>

    @GET("/TMGTFSRealTimeWebService/vehicle/vehiclepositions.pb")
    suspend fun getGtfs(): ResponseBody

    companion object {
        const val BASE_URL = "https://gtfs-rt.itsmarta.com/"
    }
}