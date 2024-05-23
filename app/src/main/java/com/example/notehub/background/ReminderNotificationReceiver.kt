package com.example.notehub.background

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.notehub.utils.NotificationUtils

class ReminderNotificationReceiver: BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onReceive(context: Context, intent: Intent) {
        NotificationUtils.showNotification(context, intent)
    }
}