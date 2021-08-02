/*
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * MVVM Pattern Source: https://github.com/ImaginativeShohag/Simple-MVVM
 */

package org.imaginativeworld.whynotcompose.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Patterns
import androidx.core.app.NotificationCompat
import com.squareup.moshi.Moshi
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.network.ApiClient
import java.util.*

object Utils {

    // --------------------------------
    // Notifications
    // --------------------------------
    private var notificationId = 0

    /**
     * Check if given text is valid email address.
     */
    fun isValidEmail(target: CharSequence?): Boolean {
        return if (target == null) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    /**
     * Get an instance of Moshi.
     */
    fun getMoshi(): Moshi {
        return Moshi.Builder()
            .add(Date::class.java, ApiClient.DateJsonAdapter())
            .build()
    }

    /**
     * Create and show a notification.
     *
     * Usage:
     * val targetIntent = Intent(this, XyzActivity::class.java)
     * targetIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
     *
     * Utils.showNotification(
     *     this,
     *     it.title,
     *     it.body,
     *     targetIntent
     * )
     */
    fun showNotification(
        context: Context,
        messageTitle: String?,
        messageBody: String,
        targetIntent: Intent?
    ) {

        val channelId = context.getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(messageTitle ?: context.getString(R.string.app_name))
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)


        targetIntent?.also {
            val pendingIntent = PendingIntent.getActivity(
                context, 0 /* Request code */, targetIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            notificationBuilder.setContentIntent(pendingIntent)
        }


        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "General notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "All general notifications"
            notificationManager.createNotificationChannel(channel)
        }

        if (notificationId == Int.MAX_VALUE) {
            notificationId = 0
        }

        notificationId++

        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}