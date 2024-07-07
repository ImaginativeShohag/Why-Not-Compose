/*
 * Copyright 2021 Md. Mahmudul Hasan Shohag
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ------------------------------------------------------------------------
 *
 * Project: Why Not Compose!
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * Source: https://github.com/ImaginativeShohag/Why-Not-Compose
 */

package org.imaginativeworld.whynotcompose.base.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.ConnectivityManager
import android.os.Build
import android.util.Patterns
import androidx.core.app.NotificationCompat
import com.squareup.moshi.Moshi
import java.util.Date
import org.imaginativeworld.whynotcompose.base.R
import org.imaginativeworld.whynotcompose.base.network.jsonadapter.DateJsonAdapter

object Utils {

    // --------------------------------
    // Notifications
    // --------------------------------
    private var notificationId = 0

    /**
     * Check if given text is valid email address.
     */
    fun isValidEmail(target: CharSequence?): Boolean = if (target == null) {
        false
    } else {
        Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    /**
     * Get an instance of Moshi.
     */
    fun getMoshi(): Moshi = Moshi.Builder()
        .add(Date::class.java, DateJsonAdapter())
        .build()

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
                context,
                0,
                targetIntent,
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

    // --------------------------------
    // Internet Utils
    // --------------------------------
    /**
     * Check if the device is connected with the Internet.
     */
    @SuppressLint("MissingPermission")
    fun isConnectedToInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo

        return activeNetwork?.isConnected == true
    }
}
