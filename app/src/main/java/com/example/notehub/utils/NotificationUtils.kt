package com.example.notehub.utils

import android.Manifest
import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.os.Build
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.notehub.MainActivity
import com.example.notehub.R
import com.example.notehub.background.ReminderNotificationReceiver

object NotificationUtils {
    private const val CHANNEL_NAME = "ReminderChannel"
    private const val CHANNEL_DESCRIPTION = "Channel for Reminder Notifications"
    private const val NOTIFICATION_ID = 1
    private const val CHANNEL_ID = "REMINDER_CHANNEL_ID"

    fun requestPermission(activity: ComponentActivity) {
        if(!checkNotificationPermission(activity)){
            val requestPermissionLauncher = activity.registerForActivityResult(
                ActivityResultContracts.RequestPermission()) {}
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    fun createChannel(context: Context) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
            description = CHANNEL_DESCRIPTION
        }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun setReminder(context: Context, calendar: Calendar, text: String) {
        if (checkNotificationPermission(context)){
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, ReminderNotificationReceiver::class.java)
            intent.putExtra("text", text)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        } else {
            Toast.makeText(context, "Notification permission not granted", Toast.LENGTH_SHORT).show()
        }
    }

    fun showNotification(context: Context, intent: Intent) {
        if (checkNotificationPermission(context)){
            val text = intent.getStringExtra("text") ?: "fuck"
            val notification = getNotification(context, text)
            with(NotificationManagerCompat.from(context)) {
                notify(NOTIFICATION_ID, notification)
            }
        } else {
            Toast.makeText(context, "Необходимо разрешить уведомления", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getNotification(context: Context, text: String): Notification {
        val notificationIntent = getNotificationIntent(context)
        val pendingIntent = getPendingIntent(context, notificationIntent)
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.example)
            .setContentTitle("Напоминание")
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }

    private fun getNotificationIntent(context: Context): Intent {
        return Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
    }

    private fun getPendingIntent(context: Context, notificationIntent: Intent): PendingIntent {
        return PendingIntent.getActivity(
            context, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    fun checkNotificationPermission(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notificationPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
            return notificationPermission == PackageManager.PERMISSION_GRANTED
        }
        return true
    }
}