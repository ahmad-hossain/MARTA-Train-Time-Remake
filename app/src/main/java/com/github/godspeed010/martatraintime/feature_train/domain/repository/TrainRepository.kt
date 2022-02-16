package com.github.godspeed010.martatraintime.feature_train.domain.repository

import com.github.godspeed010.martatraintime.feature_train.domain.model.Train

interface TrainRepository {

    suspend fun getTrains() : List<Train>
}