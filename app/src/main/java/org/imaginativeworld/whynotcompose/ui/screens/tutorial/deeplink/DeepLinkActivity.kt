package org.imaginativeworld.whynotcompose.ui.screens.tutorial.deeplink

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import org.imaginativeworld.whynotcompose.base.utils.UIThemeController
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

/***
 * Try Deep-Link (for Debug):
 *
 * ```bash
 * adb shell am start -W -a android.intent.action.VIEW -d "app://why-not-compose" org.imaginativeworld.whynotcompose.debug
 * adb shell am start -W -a android.intent.action.VIEW -d "http://imaginativeworld.org/why-not-compose" org.imaginativeworld.whynotcompose.debug
 * adb shell am start -W -a android.intent.action.VIEW -d "https://imaginativeworld.org/why-not-compose" org.imaginativeworld.whynotcompose.debug
 * ```
 */

class DeepLinkActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ATTENTION: This was auto-generated to handle app links.
        val appLinkIntent: Intent = intent
        val appLinkAction: String? = appLinkIntent.action
        val appLinkData: Uri? = appLinkIntent.data

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val isDarkMode by UIThemeController.isDarkMode.collectAsState()

            AppTheme(
                darkTheme = isDarkMode
            ) {
                DeepLinkReceiverScreen(
                    goBack = {
                        finish()
                    },
                    action = appLinkAction.toString(),
                    data = appLinkData.toString()
                )
            }
        }
    }
}

@Preview
@Composable
fun DeepLinkReceiverScreenPreview() {
    AppTheme {
        DeepLinkReceiverScreen(
            action = "Lorem",
            data = "Ipsum"
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DeepLinkReceiverScreenPreviewDark() {
    AppTheme {
        DeepLinkReceiverScreen(
            action = "Lorem",
            data = "Ipsum"
        )
    }
}

@Composable
fun DeepLinkReceiverScreen(
    goBack: () -> Unit = {},
    action: String,
    data: String
) {
    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding()
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            AppComponent.Header(
                "Deep Link Receiver",
                goBack = goBack
            )

            Divider()

            Column(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier,
                    text = "Action",
                    fontSize = 21.sp
                )

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = action,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center
                )

                Text(
                    modifier = Modifier.padding(top = 32.dp),
                    text = "Data",
                    fontSize = 21.sp
                )

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = data,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
