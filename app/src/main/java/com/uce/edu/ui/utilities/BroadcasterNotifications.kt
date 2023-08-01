package com.uce.edu.ui.utilities

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import com.uce.edu.R
import com.uce.edu.ui.activities.NotificationActivity

class BroadcasterNotifications : BroadcastReceiver() {

    val CHANNEL:String="Notificaciones"
    val CHANNEL_ID : Int =1

    override fun onReceive(context: Context, intent: Intent) {
        val myIntent = Intent(context,NotificationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val flag =
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                PendingIntent.FLAG_IMMUTABLE
            else
                0

        val pending = PendingIntent.getActivity(context,0,myIntent,flag)

        val noti = NotificationCompat.Builder(context, CHANNEL)
        noti.setContentTitle("Primera Notificacion")
        noti.setContentText("Tienes una notificacion")
        noti.setSmallIcon(R.drawable.baseline_favorite_24)
        noti.setPriority(NotificationCompat.PRIORITY_HIGH)
        noti.setStyle(
            NotificationCompat.BigTextStyle()
            .bigText("Esta es una notificacion para recordsar que estamos redactando en Android"))

        noti.setContentIntent(pending)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(
            CHANNEL_ID, noti.build()
        )

    }
}























