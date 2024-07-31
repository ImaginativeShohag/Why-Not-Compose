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

package org.imaginativeworld.whynotcompose

import android.app.Application
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapsSdkInitializedCallback
import com.onesignal.OneSignal
import com.onesignal.debug.LogLevel
import dagger.hilt.android.HiltAndroidApp
import org.imaginativeworld.whynotcompose.utils.onesignal.MyNotificationClickListener
import org.imaginativeworld.whynotcompose.utils.onesignal.MyNotificationLifecycleListener
import timber.log.Timber

@HiltAndroidApp
class App :
    Application(),
    OnMapsSdkInitializedCallback {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())

            OneSignal.Debug.logLevel = LogLevel.VERBOSE
        } else {
            OneSignal.Debug.logLevel = LogLevel.NONE
        }

        // New Map Renderer
        MapsInitializer.initialize(applicationContext, MapsInitializer.Renderer.LATEST, this)

        // OneSignal
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID)
        OneSignal.Notifications.addForegroundLifecycleListener(
            MyNotificationLifecycleListener(this)
        )
        OneSignal.Notifications.addClickListener(
            MyNotificationClickListener(this)
        )
    }

    override fun onMapsSdkInitialized(renderer: MapsInitializer.Renderer) {
        when (renderer) {
            MapsInitializer.Renderer.LATEST -> Timber.d(
                "The latest version of the renderer is used."
            )

            MapsInitializer.Renderer.LEGACY -> Timber.d(
                "The legacy version of the renderer is used."
            )
        }
    }

    companion object {
        private const val ONESIGNAL_APP_ID = "54cc32c5-ab13-4b7e-a929-7a623a572ad6"
    }
}
