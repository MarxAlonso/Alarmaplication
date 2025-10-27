package com.example.alarmaplication

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast

class NotificationActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return
        when (intent.action) {
            "ACTION_TAKEN" -> {
                Toast.makeText(context, "✅ ¡Bien hecho! Pastilla tomada.", Toast.LENGTH_SHORT).show()
            }
            "ACTION_NOT_TAKEN" -> {
                Toast.makeText(context, "🔁 Se repetirá en 1 minuto",
                    Toast.LENGTH_SHORT).show()
                val alarmManager = context.getSystemService(Context.
                ALARM_SERVICE) as AlarmManager
                val newIntent = Intent(context, AlarmReceiver::class.
                java)
                val pendingIntent = PendingIntent.getBroadcast(
                    context, 0, newIntent, PendingIntent.FLAG_IMMUTABLE
                            or PendingIntent.FLAG_UPDATE_CURRENT
                )
                val triggerAt = System.currentTimeMillis() + 60 * 1000
// 1 minuto
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if (alarmManager.canScheduleExactAlarms()) {
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                            triggerAt, pendingIntent)
                    } else {
                        val intent = Intent(android.provider.Settings.
                        ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        context.startActivity(intent)
                    }
                } else {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                        triggerAt, pendingIntent)
                }
            }
        }
    }
}