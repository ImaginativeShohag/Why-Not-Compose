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

package org.imaginativeworld.whynotcompose.ui.screens.composition.scaffold

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

// Source: https://cs.android.com/androidx/platform/tools/dokka-devsite-plugin/+/master:testData/compose/samples/material/samples/ScaffoldSamples.kt

@Composable
fun ScaffoldWithCoroutinesSnackbarScreen() {
    ScaffoldWithCoroutinesSnackbarScreenSkeleton()
}

@Preview
@Composable
fun ScaffoldWithCoroutinesSnackbarScreenSkeletonPreview() {
    AppTheme {
        ScaffoldWithCoroutinesSnackbarScreenSkeleton()
    }
}

@Preview
@Composable
fun ScaffoldWithCoroutinesSnackbarScreenSkeletonPreviewDark() {
    AppTheme(
        darkTheme = true
    ) {
        ScaffoldWithCoroutinesSnackbarScreenSkeleton()
    }
}

@Composable
fun ScaffoldWithCoroutinesSnackbarScreenSkeleton() {
    // decouple snackbar host state from scaffold state for demo purposes
    // this state, channel and flow is for demo purposes to demonstrate business logic layer
    val snackbarHostState = remember { SnackbarHostState() }
    // we allow only one snackbar to be in the queue here, hence conflated
    val channel = remember { Channel<Int>(Channel.Factory.CONFLATED) }
    LaunchedEffect(channel) {
        channel.receiveAsFlow().collect { index ->
            val result = snackbarHostState.showSnackbar(
                message = "Snackbar # $index",
                actionLabel = "Action on $index"
            )
            when (result) {
                SnackbarResult.ActionPerformed -> {
                    /* action has been performed */
                }
                SnackbarResult.Dismissed -> {
                    /* dismissed, no action needed */
                }
            }
        }
    }
    Scaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        // attach snackbar host state to the scaffold
        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
        floatingActionButton = {
            var clickCount by remember { mutableStateOf(0) }
            ExtendedFloatingActionButton(
                text = { Text("Show snackbar") },
                onClick = {
                    // offset snackbar data to the business logic
                    channel.trySend(++clickCount)
                }
            )
        },
        content = { innerPadding ->
            Text(
                "Snackbar demo",
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .wrapContentSize()
            )
        }
    )
}
