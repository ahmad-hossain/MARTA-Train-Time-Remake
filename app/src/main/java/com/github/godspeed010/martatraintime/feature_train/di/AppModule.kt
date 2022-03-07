package com.github.godspeed010.martatraintime.feature_train.di

import com.github.godspeed010.martatraintime.feature_train.data.remote.TrainApi
import com.github.godspeed010.martatraintime.feature_train.data.repository.TrainRepositoryImpl
import com.github.godspeed010.martatraintime.feature_train.domain.repository.TrainRepository
import com.github.godspeed010.martatraintime.feature_train.domain.use_case.GetTrains
import com.github.godspeed010.martatraintime.feature_train.domain.use_case.OrderTrains
import com.github.godspeed010.martatraintime.feature_train.domain.use_case.SearchTrains
import com.github.godspeed010.martatraintime.feature_train.domain.use_case.TrainsUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTrainsUseCases(repository: TrainRepository): TrainsUseCases {
        return TrainsUseCases(
            GetTrains(repository),
            OrderTrains(),
            SearchTrains()
        )
    }

    @Singleton
    @Provides
    fun provideTrainRepository(api: TrainApi) : TrainRepository {
        return TrainRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideTrainApi() : TrainApi {
        return Retrofit.Builder()
            .baseUrl(TrainApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TrainApi::class.java)
    }
}