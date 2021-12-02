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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarData
import androidx.compose.material.SnackbarDefaults
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberScaffoldState
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import org.imaginativeworld.whynotcompose.models.Event
import org.imaginativeworld.whynotcompose.ui.screens.AppComponent
import org.imaginativeworld.whynotcompose.ui.screens.CompositionsScreen
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme

@Composable
fun SnackbarScreen(
    navigate: (String) -> Unit
) {
    SnackbarScreenSkeleton(
        navigate = navigate
    )
}

@Preview
@Composable
fun SnackbarScreenSkeletonPreview() {
    AppTheme {
        SnackbarScreenSkeleton()
    }
}

@Composable
fun SnackbarScreenSkeleton(
    navigate: (String) -> Unit = {}
) {

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding(),
        scaffoldState = scaffoldState,
        snackbarHost = { CustomSnackbarHost(state = it) }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 32.dp, end = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            AppComponent.Header("Snackbar")

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            Divider()

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

            Divider()

            AppComponent.SubHeader("Custom Snackbar")

            // ----------------------------------------------------------------

            var showMessage by remember { mutableStateOf<Event<String>?>(null) }

            LaunchedEffect(showMessage) {
                showMessage?.let { message ->
                    scaffoldState.snackbarHostState.showSnackbar(message.value)
                }
            }

            OutlinedButton(onClick = {
                showMessage = Event("Hi! I am a Snackbar!")
            }) {
                Text("Show Snackbar")
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
            snackbarData = data,
        )
    }
}

@Composable
fun CustomSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    actionOnNewLine: Boolean = false,
    shape: Shape = MaterialTheme.shapes.small,
    backgroundColor: Color = SnackbarDefaults.backgroundColor,
    contentColor: Color = MaterialTheme.colors.surface,
    actionColor: Color = SnackbarDefaults.primaryActionColor,
    elevation: Dp = 6.dp
) {
    val actionLabel = snackbarData.actionLabel
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
                text = snackbarData.message,
                textAlign = TextAlign.Center,
                color = contentColor
            )
        },
        action = actionComposable,
        actionOnNewLine = actionOnNewLine,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation
    )
}
