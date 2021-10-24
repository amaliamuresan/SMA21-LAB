package com.example.lab3

import android.content.Intent

import android.os.IBinder

import androidx.core.content.ContextCompat.startActivity

import androidx.test.core.app.ApplicationProvider.getApplicationContext

import androidx.core.app.ServiceCompat.stopForeground

import android.R
import android.app.*

import androidx.core.app.NotificationCompat

import android.content.Context.NOTIFICATION_SERVICE

import androidx.core.content.ContextCompat.getSystemService

import android.os.Build

import android.content.Context
import android.util.Log
import androidx.core.app.ServiceCompat
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider


class ForegroundImageService : Service() {
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d("ForegroundImageService", "onStartCommand")
        if (intent.action == STARTFOREGROUND_ACTION) {
            val notificationIntent = Intent(this, ImageActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
            val channelID = "my channel id"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelID,
                    "my channel name",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                channel.setShowBadge(false)
                channel.setSound(null, null)
                (getSystemService(NOTIFICATION_SERVICE) as NotificationManager?)!!.createNotificationChannel(
                    channel
                )
            }
            val notification: Notification = NotificationCompat.Builder(this, channelID)
                .setContentTitle("Image download")
                .setTicker("Ticker")
                .setContentText("Please wait a few seconds ...")
                .setSmallIcon(R.drawable.toast_frame)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build()
            startForeground(FOREGROUND_SERVICE_ID, notification)
            val param = intent.getStringExtra(WebSearchActivity.EXTRA_URL)
            DownloadImageTask(this).execute(param)
        } else if (intent.action == STOPFOREGROUND_ACTION) {
            stopForeground(true)
            stopSelf()

            // start second activity to show result
            val anotherIntent = Intent(
                ApplicationProvider.getApplicationContext<Context>(),
                ImageActivity::class.java
            )
            startActivity(anotherIntent)
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        const val STARTFOREGROUND_ACTION = "myaction.startforeground"
        const val STOPFOREGROUND_ACTION = "myaction.stopforeground"
        const val FOREGROUND_SERVICE_ID = 1234
    }
}