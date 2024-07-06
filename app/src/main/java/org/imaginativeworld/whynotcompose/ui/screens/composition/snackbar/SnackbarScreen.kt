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

package org.imaginativeworld.whynotcompose.ui.screens.composition.snackbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.base.models.Event
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.ui.screens.CompositionsScreen

@Composable
fun SnackbarScreen(
    goBack: () -> Unit,
    navigate: (String) -> Unit
) {
    SnackbarScreenSkeleton(
        goBack = goBack,
        navigate = navigate
    )
}

@PreviewLightDark
@Composable
private fun SnackbarScreenSkeletonPreview() {
    AppTheme {
        SnackbarScreenSkeleton()
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun SnackbarScreenSkeleton(
    goBack: () -> Unit = {},
    navigate: (String) -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            AppComponent.Header(
                "Snackbar",
                goBack = goBack
            )
        },
        snackbarHost = { CustomSnackbarHost(state = snackbarHostState) }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                Modifier.padding(start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppComponent.SubHeader("Official Examples")

                // ----------------------------------------------------------------

                OutlinedButton(onClick = {
                    navigate(CompositionsScreen.CompositionScaffoldThree.route)
                }) {
                    Text("Scaffold With Simple Snackbar")
                }

                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()

                OutlinedButton(onClick = {
                    navigate(CompositionsScreen.CompositionScaffoldFour.route)
                }) {
                    Text("Scaffold With Custom Snackbar")
                }

                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()

                OutlinedButton(onClick = {
                    navigate(CompositionsScreen.CompositionScaffoldFive.route)
                }) {
                    Text("Scaffold With Coroutines Snackbar")
                }

                // ----------------------------------------------------------------
                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()
            }

            HorizontalDivider()

            Column(
                Modifier.padding(start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppComponent.SubHeader("Custom Snackbar")

                // ----------------------------------------------------------------

                var showMessage by remember { mutableStateOf<Event<String>?>(null) }

                LaunchedEffect(showMessage) {
                    showMessage?.let { message ->
                        snackbarHostState.showSnackbar(message.value)
                    }
                }

                OutlinedButton(onClick = {
                    showMessage = Event("Hi! I am a Snackbar!")
                }) {
                    Text("Show Snackbar")
                }
            }

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.BigSpacer()
        }
    }
}

@Composable
fun CustomSnackbarHost(state: SnackbarHostState) {
    SnackbarHost(state) { data ->
        CustomSnackbar(
            modifier = Modifier,
            snackbarData = data
        )
    }
}

@Composable
fun CustomSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    actionOnNewLine: Boolean = false,
    shape: Shape = MaterialTheme.shapes.small,
    backgroundColor: Color = SnackbarDefaults.color,
    contentColor: Color = MaterialTheme.colorScheme.surface,
    actionColor: Color = SnackbarDefaults.actionColor
) {
    val actionLabel = snackbarData.visuals.actionLabel
    val actionComposable: (@Composable () -> Unit)? = if (actionLabel != null) {
        @Composable {
            TextButton(
                colors = ButtonDefaults.textButtonColors(contentColor = actionColor),
                onClick = { snackbarData.performAction() },
                content = { Text(actionLabel) }
            )
        }
    } else {
        null
    }
    Snackbar(
        modifier = modifier.padding(12.dp),
        content = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = snackbarData.visuals.message,
                textAlign = TextAlign.Center,
                color = contentColor
            )
        },
        action = actionComposable,
        actionOnNewLine = actionOnNewLine,
        shape = shape,
        containerColor = backgroundColor,
        contentColor = contentColor
    )
}
