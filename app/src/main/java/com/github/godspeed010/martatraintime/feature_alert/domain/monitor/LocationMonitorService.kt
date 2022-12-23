package com.github.godspeed010.martatraintime.feature_alert.domain.monitor

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
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
import com.google.android.gms.location.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import java.util.concurrent.Executor
import kotlin.math.roundToInt

class LocationMonitorService : Service() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val locationRequest =
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5 * 1000).apply {
            setMinUpdateIntervalMillis(4 * 1000)
            setIntervalMillis(5 * 1000)
            setMinUpdateDistanceMeters(3f)
        }.build()
    private val locationListener = LocationListener { location -> updateLocation(location) }
    private val mutableLocationFlow = MutableSharedFlow<Location>()

    private val stopServiceIntent by lazy {
        Intent(this, LocationMonitorService::class.java).apply { action = ACTION_STOP_SERVICE }
    }
    private val stopServicePIntent by lazy {
        PendingIntent.getService(this, 0, stopServiceIntent, PendingIntent.FLAG_CANCEL_CURRENT)
    }
    private val notificationBuilder by lazy {
        NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID).setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(getString(R.string.service_notification_title))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(R.mipmap.ic_launcher, getString(R.string.stop), stopServicePIntent)
    }

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

        startLocationMonitoring()

        scope.launch {
            mutableLocationFlow.collectLatest(::updateNotification)
        }
    }

    private fun updateNotification(location: Location) {
        Log.i(TAG, "updateNotification()")
        val timeRemaining = location.timeTo(destination).roundToInt()
        val inboxStyle = NotificationCompat.InboxStyle()
            .addLine(getString(R.string.minutes_remaining, timeRemaining))
            .addLine(getString(R.string.speed, location.speed))
        NotificationManagerCompat.from(this@LocationMonitorService)
            .notify(NOTIFICATION_ID, notificationBuilder.setStyle(inboxStyle).build())
    }

    private fun Location.timeTo(destination: Location): Float {
        val remainingDistanceM = this.distanceTo(destination)
        return remainingDistanceM / this.speed
    }

    @SuppressLint("MissingPermission")
    private fun startLocationMonitoring() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            Executor { it.run() },
            locationListener,
        )
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

    private fun updateLocation(location: Location) {
        Log.i(TAG, "updateLocation(): location=${location.latitude}, ${location.longitude}")
        scope.launch { mutableLocationFlow.emit(location) }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    companion object {
        private const val TAG = "LocationMonitor"
        private const val NOTIFICATION_CHANNEL_ID = "LocationMonitorChannel"
        private const val NOTIFICATION_CHANNEL_NAME = "Stop Arrival Status"
        private const val NOTIFICATION_CHANNEL_DESCRIPTION = "Stop Arrival Alert"
        private const val NOTIFICATION_ID = 1
        const val ACTION_START_SERVICE = "ACTION_START_SERVICE"
        private const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"
        const val EXTRA_DESTINATION_LOCATION = "EXTRA_DESTINATION_LOCATION"
    }
}