package com.example.martatraintimeremake.di

import com.example.martatraintimeremake.data.remote.TrainApi
import com.example.martatraintimeremake.data.repository.TrainRepositoryImpl
import com.example.martatraintimeremake.domain.repository.TrainRepository
import com.example.martatraintimeremake.domain.use_case.GetTrains
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
    fun provideGetTrainsUseCase(repository: TrainRepository): GetTrains {
        return GetTrains(repository)
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