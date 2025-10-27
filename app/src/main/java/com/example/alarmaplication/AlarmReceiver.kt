package com.example.alarmaplication
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return
        val channelId = "alarm_channel"
// 🔹 1. Crear el canal de notificación si aún no existe
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Canal de alarmas"
            val descriptionText = "Canal para notificaciones de alarmas inteligentes"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE)
                        as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
// 🔹 2. Intent para acción "Sí"
        val yesIntent = Intent(
            context, NotificationActionReceiver::
            class.java
        ).apply {
            action = "ACTION_TAKEN"
        }
        val yesPending = PendingIntent.getBroadcast(
            context, 0, yesIntent, PendingIntent.FLAG_IMMUTABLE or
                    PendingIntent.FLAG_UPDATE_CURRENT
        )
// 🔹 3. Intent para acción "No"
        val noIntent = Intent(
            context, NotificationActionReceiver::class
                .java
        ).apply {
            action = "ACTION_NOT_TAKEN"
        }
        val noPending = PendingIntent.getBroadcast(
            context, 1, noIntent, PendingIntent.FLAG_IMMUTABLE or
                    PendingIntent.FLAG_UPDATE_CURRENT
        )
// 🔹 4. Construir la notificación con acciones
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("💊 Hora de tomar tu pastilla")
            .setContentText("¿La tomaste?")
            .addAction(
                android.R.drawable.ic_menu_info_details, "✅ Sí",
                yesPending
            )
            .addAction(
                android.R.drawable.ic_menu_today, "🔁 No",
                noPending
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
// 🔹 5. Mostrar la notificación
// 5. Mostrar la notificación (con permiso verificado)
        if (androidx.core.app.ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(context).notify(
                1001, builder
                    .build()
            )
        }
    }
}