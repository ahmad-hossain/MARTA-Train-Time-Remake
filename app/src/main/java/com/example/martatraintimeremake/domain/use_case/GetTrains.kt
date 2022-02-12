package com.example.martatraintimeremake.domain.use_case

import com.example.martatraintimeremake.domain.model.Train
import com.example.martatraintimeremake.domain.repository.TrainRepository
import retrofit2.Response

class GetTrains(
    private val repository: TrainRepository
) {

    suspend operator fun invoke(): List<Train> {
        return repository.getTrains()
    }
}