package com.lilcode.example.localnotifydemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ClipDescription
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {

    private var notificationManager: NotificationManager? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(
            "com.lilcode.example.localnotifydemo",
            "Notify News",
            "Example news Channel"
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(id: String, name: String, description: String) {
        val importance = NotificationManager.IMPORTANCE_LOW
        // 채널 인스턴스 생성 및 초기화
        val channel = NotificationChannel(id, name, importance).apply {
            this.description = description
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        }

        notificationManager?.createNotificationChannel(channel)

    }
}