package com.example.alarmaplication

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
// Canal de notificación
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "alarm_channel",
                "Canal de Alarmas",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(channel)
        }
// Permisos exactos
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(
                AlarmManager::class.java
            )
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(
                    android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                )
                startActivity(intent)
            }
        }
        val timePicker = findViewById<TimePicker>(R.id.timePicker)
        val btnSetAlarm = findViewById<Button>(R.id.btnSetAlarm)
        val tvStatus = findViewById<TextView>(R.id.tvStatus)
        timePicker.setIs24HourView(true)
        btnSetAlarm.setOnClickListener {
            val hour = timePicker.hour
            val minute = timePicker.minute
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                if (timeInMillis <= System.currentTimeMillis()) {
                    add(Calendar.DAY_OF_MONTH, 1)
                }
            }
            val intent = Intent(this, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_IMMUTABLE or
                        PendingIntent.FLAG_UPDATE_CURRENT
            )
            val alarmManager = getSystemService(
                AlarmManager::class.java
            )
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent
            )
            val msg = "💊 Alarma programada a las %02d:%02d".format(
                hour, minute
            )
            tvStatus.text = msg
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        }
    }
}