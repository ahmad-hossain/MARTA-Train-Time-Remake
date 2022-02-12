package com.example.martatraintimeremake.domain.repository

import com.example.martatraintimeremake.domain.model.Train
import retrofit2.Response

interface TrainRepository {

    suspend fun getTrains() : List<Train>
}