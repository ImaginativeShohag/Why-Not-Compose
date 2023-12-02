/*
 * Copyright 2023 Md. Mahmudul Hasan Shohag
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

package org.imaginativeworld.whynotcompose.ui.screens.tutorial.deeplinks

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
 * Try Deep-Links:
 *
 * ```bash
 * adb shell am start -W -a android.intent.action.VIEW -d "app://why-not-compose" org.imaginativeworld.whynotcompose.debug
 * adb shell am start -W -a android.intent.action.VIEW -d "http://imaginativeworld.org/why-not-compose" org.imaginativeworld.whynotcompose.debug
 * adb shell am start -W -a android.intent.action.VIEW -d "https://imaginativeworld.org/why-not-compose" org.imaginativeworld.whynotcompose.debug
 * ```
 *
 * Test Deep-Links:
 *
 * ```bash
 * adb shell am start -a android.intent.action.VIEW -c android.intent.category.BROWSABLE -d "http://imaginativeworld.org/why-not-compose"
 * adb shell am start -a android.intent.action.VIEW -c android.intent.category.BROWSABLE -d "https://imaginativeworld.org/why-not-compose"
 * ```
 *
 * Check the verification status:
 *
 * ```bash
 * adb shell pm get-app-links --user cur org.imaginativeworld.whynotcompose.debug
 * ```
 *
 * Reset verification status:
 *
 * ```bash
 * adb shell pm set-app-links --package org.imaginativeworld.whynotcompose.debug 0 all
 * ```
 *
 * Invoke the domain verification process
 *
 * ```bash
 * adb shell pm verify-app-links --re-verify org.imaginativeworld.whynotcompose.debug
 * ```
 *
 * Confirm the Digital Asset Links files
 *
 * https://digitalassetlinks.googleapis.com/v1/statements:list?source.web.site=https://imaginativeworld.org&relation=delegate_permission/common.handle_all_urls
 */

class DeepLinksActivity : ComponentActivity() {
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
                DeepLinksReceiverScreen(
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
fun DeepLinksReceiverScreenPreview() {
    AppTheme {
        DeepLinksReceiverScreen(
            action = "Lorem",
            data = "Ipsum"
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DeepLinksReceiverScreenPreviewDark() {
    AppTheme {
        DeepLinksReceiverScreen(
            action = "Lorem",
            data = "Ipsum"
        )
    }
}

@Composable
fun DeepLinksReceiverScreen(
    goBack: () -> Unit = {},
    action: String,
    data: String
) {
    Scaffold { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .navigationBarsPadding()
                .imePadding()
                .statusBarsPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppComponent.Header(
                "Deep Links Receiver",
                goBack = goBack
            )

            Divider()

            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "This activity will only open from the deep-links.",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

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
