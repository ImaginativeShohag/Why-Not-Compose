package org.imaginativeworld.whynotcompose.utils.onesignal

import android.app.Application
import android.content.Intent
import com.onesignal.OSNotificationOpenedResult
import com.onesignal.OneSignal
import org.imaginativeworld.whynotcompose.ui.screens.MainActivity
import org.imaginativeworld.whynotcompose.utils.Constants
import timber.log.Timber

// This fires when a notification is opened by tapping on it.
class MyNotificationOpenedHandler(
    private val application: Application
) : OneSignal.OSNotificationOpenedHandler {
    override fun notificationOpened(result: OSNotificationOpenedResult?) {
        Timber.d("notificationOpened")

        Timber.i("result.getNotification().getRawPayload(): %s", result!!.notification.rawPayload)

//        OSNotificationAction.ActionType actionType = result.action.type;
//        JSONObject data = result.notification.payload.additionalData;
//        String customKey;


//        if (data != null) {
//            customKey = data.optString("customkey", null);
//            if (customKey != null)
//                Timber.i("OneSignalExample: customkey set with value: %s", customKey);
//        }
//
//        if (actionType == OSNotificationAction.ActionType.ActionTaken)
//            Timber.i("OneSignalExample: Button pressed with id: %s", result.action.actionID);

        // Pending intent
        val intent = Intent(application.applicationContext, MainActivity::class.java)

        intent.putExtra(
            Constants.INTENT_EXTRA_TARGET_KEY,
            Constants.INTENT_EXTRA_TARGET_VAL_NOTIFICATIONS
        )

        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_NEW_TASK
        application.startActivity(intent)
    }
}