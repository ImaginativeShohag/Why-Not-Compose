/*
 * Copyright 2022 Md. Mahmudul Hasan Shohag
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
