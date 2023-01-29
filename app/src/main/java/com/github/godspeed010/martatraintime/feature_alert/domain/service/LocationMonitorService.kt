package com.github.godspeed010.martatraintime.feature_alert.domain.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.github.godspeed010.martatraintime.R
import com.github.godspeed010.martatraintime.feature_alert.domain.model.NotificationState
import com.github.godspeed010.martatraintime.feature_alert.domain.model.RunningAverage
import com.github.godspeed010.martatraintime.feature_alert.domain.monitor.LocationMonitor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class LocationMonitorService : Service() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    @Inject lateinit var locationMonitor: LocationMonitor

    private val mutableNotificationStateFlow = MutableStateFlow(NotificationState())
    private val notificationState: StateFlow<NotificationState>
        get() = mutableNotificationStateFlow.asStateFlow()

    @Inject lateinit var notificationBuilder: NotificationCompat.Builder

    private lateinit var destination: Location

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand(): action=${intent?.action}")
        when (intent?.action) {
            ACTION_START_SERVICE -> start(intent)
            ACTION_STOP_SERVICE -> stopSelf()
            else -> Log.w(TAG, "onStartCommand() called with unexpected action=${intent?.action}")
        }
        return START_STICKY
    }

    private fun start(intent: Intent) {
        destination = intent.extras?.getParcelable(EXTRA_DESTINATION_LOCATION)!!

        createNotificationChannel()
        startForeground(NOTIFICATION_ID, notificationBuilder.build())

        locationMonitor.start()

        scope.launch {
            launch {
                locationMonitor.locationFlow.collectLatest(::updateNotificationState)
            }
            launch {
                notificationState.collectLatest(::updateNotification)
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = NOTIFICATION_CHANNEL_NAME
            val descriptionText = NOTIFICATION_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun updateNotificationState(location: Location) {
        Log.i(TAG, "updateState(): location=${location.latitude}, ${location.longitude}")

        val runningAvg = notificationState.value.runningAverage
        val newNotificationState = NotificationState(
            runningAverage = RunningAverage(
                runningTotal = runningAvg.runningTotal + location.speed,
                dataPoints = runningAvg.dataPoints + 1
            ), lastLocation = location
        )

        scope.launch {
            mutableNotificationStateFlow.emit(newNotificationState)
        }
    }

    private fun updateNotification(notificationState: NotificationState) {
        Log.i(TAG, "updateNotification()")

        val timeRemaining = notificationState.lastLocation?.timeTo(destination)?.roundToInt()
        val avgSpeed = notificationState.runningAverage.average

        val inboxStyle = NotificationCompat.InboxStyle()
            .addLine(getString(R.string.minutes_remaining, timeRemaining))
            .addLine(getString(R.string.avg_speed, avgSpeed))

        NotificationManagerCompat.from(this@LocationMonitorService)
            .notify(NOTIFICATION_ID, notificationBuilder.setStyle(inboxStyle).build())
    }

    private fun Location.timeTo(destination: Location): Float {
        val remainingDistanceM = this.distanceTo(destination)
        return remainingDistanceM / this.speed
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        locationMonitor.stop()
    }

    companion object {
        private const val TAG = "LocationMonitor"
        const val NOTIFICATION_CHANNEL_ID = "LocationMonitorChannel"
        private const val NOTIFICATION_CHANNEL_NAME = "Stop Arrival Status"
        private const val NOTIFICATION_CHANNEL_DESCRIPTION = "Stop Arrival Alert"
        private const val NOTIFICATION_ID = 1
        const val ACTION_START_SERVICE = "ACTION_START_SERVICE"
        const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"
        const val EXTRA_DESTINATION_LOCATION = "EXTRA_DESTINATION_LOCATION"
    }
}