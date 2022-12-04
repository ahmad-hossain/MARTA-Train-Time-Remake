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
import com.github.godspeed010.martatraintime.R
import com.google.android.gms.location.*
import kotlinx.coroutines.delay
import java.util.concurrent.Executor

private const val TAG = "LocationMonitor"
private const val NOTIFICATION_CHANNEL_ID = "LocationMonitorChannel"
private const val NOTIFICATION_CHANNEL_NAME = "Stop Arrival Status"
private const val NOTIFICATION_CHANNEL_DESCRIPTION = "Stop Arrival Alert"
private const val NOTIFICATION_ID = 1
private const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"

class LocationMonitor : Service() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val locationRequest =
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5 * 1000).apply {
            setMinUpdateIntervalMillis(4 * 1000)
            setIntervalMillis(5 * 1000)
            setMinUpdateDistanceMeters(3f)
        }.build()
    private val locationListener = LocationListener { location -> updateLocation(location) }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ACTION_STOP_SERVICE) {
            stopSelf()
        }

        /** TODO move onCreate logic into onStartCommand.
         *  Check for START_SERVICE Flag. Then change this to START_STICKY
         *  */
        return START_NOT_STICKY
    }

    @SuppressLint("MissingPermission")
    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate")

        createNotificationChannel()

        val stopServiceIntent =
            Intent(this, LocationMonitor::class.java).apply { action = ACTION_STOP_SERVICE }
        val stopServicePIntent =
            PendingIntent.getService(this, 0, stopServiceIntent, PendingIntent.FLAG_CANCEL_CURRENT)
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground).setContentTitle("My notification")
            .setContentText("Hello World!").setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(R.drawable.ic_launcher_foreground, "Stop", stopServicePIntent)

        startForeground(NOTIFICATION_ID, notificationBuilder.build())

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            Executor { it.run() },
            locationListener,
        )
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = NOTIFICATION_CHANNEL_NAME
            val descriptionText = NOTIFICATION_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun updateLocation(location: Location) {
        Log.i(TAG, "updateLocation(): location=${location.latitude}, ${location.longitude}")
    }

}