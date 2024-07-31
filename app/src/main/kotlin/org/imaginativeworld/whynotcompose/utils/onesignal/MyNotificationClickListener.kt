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
import com.onesignal.notifications.INotificationClickEvent
import com.onesignal.notifications.INotificationClickListener
import org.imaginativeworld.whynotcompose.base.utils.Constants
import org.imaginativeworld.whynotcompose.ui.screens.MainActivity
import timber.log.Timber

class MyNotificationClickListener(
    private val application: Application
) : INotificationClickListener {
    override fun onClick(event: INotificationClickEvent) {
        Timber.d("notificationOpened")

        Timber.i(
            "result.getNotification().getRawPayload(): %s",
            event.notification.rawPayload
        )

        // Pending intent
        val intent = Intent(application.applicationContext, MainActivity::class.java)

        intent.putExtra(
            Constants.INTENT_EXTRA_TARGET_KEY,
            Constants.INTENT_EXTRA_TARGET_VAL_NOTIFICATIONS
        )

        intent.flags =
            Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_NEW_TASK
        application.startActivity(intent)
    }
}
