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

package org.imaginativeworld.whynotcompose.ui.screens.tutorial.onesignalandbroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.onesignal.OneSignal
import org.imaginativeworld.whynotcompose.base.extensions.toast
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.utils.Constants
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber

@Composable
fun OneSignalAndBroadcastScreen() {
    val context = LocalContext.current

    var counter by remember { mutableStateOf(0) }
    var currentValue by remember { mutableStateOf("$counter") }

    DisposableEffect(context) {
        /**
         * We will receive the broadcast data here. See [MyNotificationWillShowInForegroundHandler]
         * for how to receive data and broadcast data when a new OneSignal notification comes.
         */
        val broadcast = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                intent.getStringExtra("value")?.let { value ->
                    currentValue = value
                }
            }
        }

        val intentFilter = IntentFilter().apply {
            addAction(Constants.BROADCAST_ACTION_NOTIFICATIONS)
        }

        LocalBroadcastManager.getInstance(context).registerReceiver(broadcast, intentFilter)

        onDispose {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcast)
        }
    }

    OneSignalAndBroadcastScreenSkeleton(
        currentValue = currentValue,
        sendNotification = {
            try {
                counter++

                sendNewNotification(counter.toString())
            } catch (e: JSONException) {
                e.printStackTrace()

                context.toast(e.toString())
            }
        }
    )
}

/**
 * Send a new OneSignal notification to the current app user with given [value].
 */
private fun sendNewNotification(value: String) {
    if (OneSignal.getDeviceState() != null && OneSignal.getDeviceState()!!.isSubscribed) {
        // This is the current device OneSignal userId.
        val userId = OneSignal.getDeviceState()!!.userId

        OneSignal.postNotification(
            """
{
  "contents": {
    "en": "Current value is $value"
  },
  "data": {
    "value": "$value"
  },
  "include_player_ids": [
    "$userId"
  ]
}
            """.trimIndent(),
            object : OneSignal.PostNotificationResponseHandler {
                override fun onSuccess(response: JSONObject?) {
                    Timber.i("postNotification Success: " + response.toString())
                }

                override fun onFailure(response: JSONObject?) {
                    Timber.e("postNotification Failure: " + response.toString())
                }
            }
        )
    } else {
        throw Exception("OneSignal is not initialized yet or user is not subscribed!")
    }
}

@Preview
@Composable
fun OneSignalAndBroadcastScreenSkeletonPreview() {
    AppTheme {
        OneSignalAndBroadcastScreenSkeleton()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun OneSignalAndBroadcastScreenSkeletonPreviewDark() {
    AppTheme {
        OneSignalAndBroadcastScreenSkeleton()
    }
}

@Composable
fun OneSignalAndBroadcastScreenSkeleton(
    currentValue: String = "",
    sendNotification: () -> Unit = {}
) {
    Scaffold(
        Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AppComponent.Header("OneSignal and Broadcast")

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            Column(
                Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text("Current value: $currentValue")

                AppComponent.MediumSpacer()

                Button(onClick = {
                    sendNotification()
                }) {
                    Text("Post New Value")
                }
            }

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.BigSpacer()
        }
    }
}
