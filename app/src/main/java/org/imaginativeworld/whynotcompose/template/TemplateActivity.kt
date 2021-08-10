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
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme
import org.imaginativeworld.whynotcompose.utils.SharedPref
import org.imaginativeworld.whynotcompose.utils.extensions.toast
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
