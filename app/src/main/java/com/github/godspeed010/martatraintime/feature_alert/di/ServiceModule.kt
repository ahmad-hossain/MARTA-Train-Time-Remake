package com.github.godspeed010.martatraintime.feature_alert.di

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.github.godspeed010.martatraintime.R
import com.github.godspeed010.martatraintime.feature_alert.domain.monitor.LocationMonitor
import com.github.godspeed010.martatraintime.feature_alert.domain.monitor.impl.LocationMonitorImpl
import com.github.godspeed010.martatraintime.feature_alert.domain.service.LocationMonitorService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.scopes.ServiceScoped
import javax.inject.Qualifier

@Qualifier
annotation class ServiceContext

@Module
@InstallIn(ServiceComponent::class)
interface ServiceModule {

    @Binds
    @ServiceContext
    fun provideContext(service: Service): Context

    @ServiceScoped
    @Binds
    fun provideLocationMonitor(locationMonitorImpl: LocationMonitorImpl): LocationMonitor

    companion object {
        @Provides
        fun provideStopServiceIntent(@ServiceContext context: Context): Intent =
            Intent(context, LocationMonitorService::class.java).apply {
                action = LocationMonitorService.ACTION_STOP_SERVICE
            }

        @Provides
        fun provideStopServicePendingIntent(
            @ServiceContext context: Context,
            stopServiceIntent: Intent
        ): PendingIntent {
            val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }

            return PendingIntent.getService(context, 0, stopServiceIntent, flags)
        }

        @Provides
        fun provideNotificationBuilder(
            @ServiceContext context: Context,
            stopServicePendingIntent: PendingIntent,
        ) =
            NotificationCompat.Builder(context, LocationMonitorService.NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(context.getString(R.string.service_notification_title))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(R.mipmap.ic_launcher, context.getString(R.string.stop), stopServicePendingIntent)

    }
}