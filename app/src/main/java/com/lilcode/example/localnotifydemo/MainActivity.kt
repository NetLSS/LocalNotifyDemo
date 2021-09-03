package com.lilcode.example.localnotifydemo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Icon
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {

    private var notificationManager: NotificationManager? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(
            newsChannelID,
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendNotification(view: View) {

        val resultIntent = Intent(this, ResultActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        // PendingIntent.FLAG_UPDATE_CURRENT : 팬딩 인텐트가 이미 존재할경우, Extra data를 새로운 인텐트 데이터로 업데이트
        // 각각의 알림에서는 팬딩인텐트가 동일 하도록

        val icon: Icon = Icon.createWithResource(this, android.R.drawable.ic_dialog_info)
        val action: Notification.Action = Notification.Action.Builder(icon, "Open", pendingIntent).build()

        // 알림 생성
        val notification = Notification.Builder(this@MainActivity, newsChannelID)
            .setContentTitle("Example Notification")
            .setContentTitle("This is an example notification.")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setChannelId(newsChannelID)
            .setNumber(999)
            .setContentIntent(pendingIntent)
            .setActions(action)
            .build()

        val notificationID = 101 // 정수값 아무거나 (향후 알림 변경시 사용됨)

        notificationManager?.notify(notificationID, notification)
    }

    companion object {
        const val newsChannelID = "com.lilcode.example.localnotifydemo"
    }
}