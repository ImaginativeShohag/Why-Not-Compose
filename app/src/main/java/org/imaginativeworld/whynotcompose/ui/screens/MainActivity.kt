package org.imaginativeworld.whynotcompose.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.insets.ProvideWindowInsets
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme
import org.imaginativeworld.whynotcompose.utils.extensions.toast

class MainActivity : ComponentActivity() {

    private var pressBackExitJob: Job? = null
    private var backPressedOnce = false

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        var isDarkMode by mutableStateOf(false)

        setContent {
            AppTheme(
                darkTheme = isDarkMode
            ) {
                ProvideWindowInsets {
                    MainScreen(
                        turnOnDarkMode = { turnOn ->
                            isDarkMode = turnOn
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
