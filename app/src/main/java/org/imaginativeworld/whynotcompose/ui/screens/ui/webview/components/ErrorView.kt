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

package org.imaginativeworld.whynotcompose.ui.screens.ui.webview.components

import android.content.res.Configuration
import android.webkit.WebViewClient.ERROR_HOST_LOOKUP
import android.webkit.WebViewClient.ERROR_TIMEOUT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

@Preview
@Composable
fun ErrorViewPreview() {
    AppTheme {
        ErrorView(
            errorCode = ERROR_HOST_LOOKUP,
            description = "Webpage not available",
            failingUrl = "https://imaginativeworld.org",
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ErrorViewPreviewDark() {
    AppTheme {
        ErrorView(
            errorCode = ERROR_HOST_LOOKUP,
            description = "Webpage not available",
            failingUrl = "https://imaginativeworld.org",
        )
    }
}

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    errorCode: Int,
    description: String?,
    failingUrl: String?,
    onRetry: () -> Unit = {},
) {
    Scaffold(modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                modifier = Modifier
                    .padding(start = 32.dp, end = 32.dp)
                    .fillMaxWidth(),
                painter = painterResource(
                    id = R.drawable.ic_undraw_location_search_modified
                ),
                contentDescription = null,
            )

            Spacer(Modifier.height(32.dp))

            if (failingUrl != null) {
                Text(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                    text = buildAnnotatedString {
                        append("The webpage at ")

                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                            )
                        ) {
                            append("$failingUrl")
                        }

                        append(" could not be loaded.")
                    },
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                )
            }

            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                text = getMessage(errorCode, description),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
            )

            Button(
                modifier = Modifier.padding(top = 32.dp),
                onClick = { onRetry() }
            ) {
                Text("Try Again")
            }
        }
    }
}

private fun getMessage(
    errorCode: Int,
    description: String?,
): String {
    return when (errorCode) {
        ERROR_HOST_LOOKUP -> "The website not found! Maybe you are not connected to the Internet."
        ERROR_TIMEOUT -> "Time out! Please try again."
        else -> description ?: "Please try again after some time."
    }
}
