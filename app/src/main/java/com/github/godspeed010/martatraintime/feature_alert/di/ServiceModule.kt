package com.github.godspeed010.martatraintime.feature_alert.di

import com.github.godspeed010.martatraintime.feature_alert.domain.monitor.LocationMonitor
import com.github.godspeed010.martatraintime.feature_alert.domain.monitor.impl.LocationMonitorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
interface ServiceModule {

    @ServiceScoped
    @Binds
    fun provideLocationMonitor(locationMonitorImpl: LocationMonitorImpl): LocationMonitor
}