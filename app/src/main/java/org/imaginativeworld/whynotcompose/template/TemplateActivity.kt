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

package org.imaginativeworld.whynotcompose.template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.base.extensions.toast
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.utils.SharedPref
import javax.inject.Inject

@AndroidEntryPoint
class TemplateActivity : ComponentActivity() {

    @Inject
    lateinit var sharedPref: SharedPref

    private var pressBackExitJob: Job? = null
    private var backPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        var isDarkMode by mutableStateOf(sharedPref.getDarkMode())

        setContent {
            AppTheme(
                darkTheme = isDarkMode
            ) {
                ProvideWindowInsets {
                    TemplateMainScreen(
                        isDarkMode = isDarkMode,
                        turnOnDarkMode = { turnOn ->
                            isDarkMode = turnOn

                            sharedPref.setDarkMode(turnOn)
                        }
                    )
                }
            }
        }

        // Press double back to exit
        onBackPressedDispatcher.addCallback(this) {
            pressBackExitJob?.cancel()

            if (backPressedOnce) {
                finish()
                return@addCallback
            }

            toast("Please press back again to exit.")

            backPressedOnce = true

            pressBackExitJob = lifecycleScope.launch {
                delay(500)

                backPressedOnce = false
            }
        }
    }
}
