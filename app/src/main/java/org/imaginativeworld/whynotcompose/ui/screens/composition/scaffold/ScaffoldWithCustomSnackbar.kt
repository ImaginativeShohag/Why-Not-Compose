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

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme

// Source: https://cs.android.com/androidx/platform/tools/dokka-devsite-plugin/+/master:testData/compose/samples/material/samples/ScaffoldSamples.kt

@Composable
fun ScaffoldWithCustomSnackbarScreen() {
    ScaffoldWithCustomSnackbarScreenSkeleton()
}

@Preview
@Composable
fun ScaffoldWithCustomSnackbarScreenSkeletonPreview() {
    AppTheme {
        ScaffoldWithCustomSnackbarScreenSkeleton()
    }
}

@Preview
@Composable
fun ScaffoldWithCustomSnackbarScreenSkeletonPreviewDark() {
    AppTheme(
        darkTheme = true
    ) {
        ScaffoldWithCustomSnackbarScreenSkeleton()
    }
}

@Composable
fun ScaffoldWithCustomSnackbarScreenSkeleton() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding(),
        scaffoldState = scaffoldState,
        snackbarHost = {
            // reuse default SnackbarHost to have default animation and timing handling
            SnackbarHost(it) { data ->
                // custom snackbar with the custom border
                Snackbar(
                    modifier = Modifier.border(2.dp, MaterialTheme.colors.secondary),
                    snackbarData = data
                )
            }
        },
        floatingActionButton = {
            var clickCount by remember { mutableStateOf(0) }
            ExtendedFloatingActionButton(
                text = { Text("Show snackbar") },
                onClick = {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Snackbar # ${++clickCount}")
                    }
                }
            )
        },
        content = { innerPadding ->
            Text(
                text = "Custom Snackbar Demo",
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .wrapContentSize()
            )
        }
    )
}

// @Composable
// fun TemplateChildScreen() {
//    TemplateChildScreenSkeleton()
// }
//
// @Preview
// @Composable
// fun TemplateChildScreenSkeletonPreview() {
//    AppTheme {
//        TemplateChildScreenSkeleton()
//    }
// }
//
// @Preview
// @Composable
// fun TemplateChildScreenSkeletonPreviewDark() {
//    AppTheme(
//        darkTheme = true
//    ) {
//        TemplateChildScreenSkeleton()
//    }
// }
//
// @Composable
// fun TemplateChildScreenSkeleton() {
//    Scaffold(
//        Modifier
//            .navigationBarsWithImePadding()
//            .statusBarsPadding()
//    ) {
//        Column(
//            Modifier
//                .fillMaxSize()
//                .verticalScroll(rememberScrollState())
//                .padding(start = 16.dp, end = 16.dp)
//        ) {
//            AppComponent.Header("Title")
//
//            // ----------------------------------------------------------------
//            // ----------------------------------------------------------------
//
//            Divider()
//
//            AppComponent.SubHeader("Sub Title")
//
//            // ----------------------------------------------------------------
//
//            // Content here...
//
//            // ----------------------------------------------------------------
//
//            AppComponent.MediumSpacer()
//
//            // Content here...
//
//            // ----------------------------------------------------------------
//            // ----------------------------------------------------------------
//
//            AppComponent.BigSpacer()
//
//        }
//    }
// }
