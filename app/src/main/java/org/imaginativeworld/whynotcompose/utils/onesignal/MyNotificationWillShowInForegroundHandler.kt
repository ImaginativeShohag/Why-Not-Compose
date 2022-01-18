package org.imaginativeworld.whynotcompose.utils.onesignal

import android.app.Application
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.onesignal.OSNotificationReceivedEvent
import com.onesignal.OneSignal
import org.imaginativeworld.whynotcompose.utils.Constants
import org.json.JSONException
import timber.log.Timber

// Runs before displaying a notification while the app is in focus. Use this handler to decide if the notification should show or not.
class MyNotificationWillShowInForegroundHandler(
    private val application: Application
) : OneSignal.OSNotificationWillShowInForegroundHandler {
    override fun notificationWillShowInForeground(notificationReceivedEvent: OSNotificationReceivedEvent?) {
        // Get custom additional data you sent with the notification
        val data = notificationReceivedEvent?.notification?.additionalData

        Timber.i("Additional Data: $data")

        // Complete with a notification means it will show
        notificationReceivedEvent?.complete(notificationReceivedEvent.notification)

        // --------------------------------------------------------------------
        // Send broadcast
        // --------------------------------------------------------------------
        val intent = Intent(Constants.BROADCAST_ACTION_NOTIFICATIONS)

        try {
            data?.getString("value")?.let { value ->
                intent.putExtra("value", value)
            }
        } catch (e: JSONException) {
            Timber.d(e)
        }

        LocalBroadcastManager.getInstance(application.applicationContext)
            .sendBroadcast(intent)
    }
}