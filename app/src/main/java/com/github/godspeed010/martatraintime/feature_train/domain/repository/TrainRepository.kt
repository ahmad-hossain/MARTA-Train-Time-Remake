package com.github.godspeed010.martatraintime.feature_train.domain.repository

import com.github.godspeed010.martatraintime.feature_train.domain.model.Train
import com.google.transit.realtime.GtfsRealtime

interface TrainRepository {

    suspend fun getTrains(): List<Train>

    suspend fun getGtfs(): GtfsRealtime.FeedMessage
}