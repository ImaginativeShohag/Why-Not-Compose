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

package org.imaginativeworld.whynotcompose.ui.screens.composition.floatingactionbutton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

// Source:
// https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/material3/material3/samples/src/main/java/androidx/compose/material3/samples/FloatingActionButtonSamples.kt

@Composable
fun FloatingActionButtonScreen(
    goBack: () -> Unit
) {
    FloatingActionButtonScreenSkeleton(
        goBack = goBack
    )
}

@PreviewLightDark
@Composable
private fun FloatingActionButtonScreenSkeletonPreviewDark() {
    AppTheme {
        FloatingActionButtonScreenSkeleton()
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun FloatingActionButtonScreenSkeleton(
    goBack: () -> Unit = {}
) {
    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            AppComponent.Header(
                "FloatingActionButton",
                goBack = goBack
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppComponent.MediumSpacer()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FloatingActionButton(
                        onClick = { /* do something */ }
                    ) {
                        Icon(Icons.Filled.Add, "Localized description")
                    }

                    // ----------------------------------------------------------------

                    SmallFloatingActionButton(
                        onClick = { /* do something */ }
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Localized description")
                    }

                    // ----------------------------------------------------------------

                    LargeFloatingActionButton(
                        onClick = { /* do something */ }
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "Localized description",
                            modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize)
                        )
                    }
                }

                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ExtendedFloatingActionButton(onClick = { /* do something */ }) { Text(text = "Extended FAB") }

                    // ----------------------------------------------------------------

                    ExtendedFloatingActionButton(
                        onClick = { /* do something */ },
                        icon = { Icon(Icons.Filled.Add, "Localized description") },
                        text = { Text(text = "Extended FAB") }
                    )
                }

                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()

                HorizontalDivider()

                val listState = rememberLazyListState()
                // The FAB is initially expanded. Once the first visible item is past the first item we
                // collapse the FAB. We use a remembered derived state to minimize unnecessary compositions.
                val expandedFab by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }
                Scaffold(
                    modifier = Modifier.weight(1f),
                    floatingActionButton = {
                        ExtendedFloatingActionButton(
                            onClick = { /* do something */ },
                            expanded = expandedFab,
                            icon = { Icon(Icons.Filled.Add, "Localized Description") },
                            text = { Text(text = "Extended FAB") }
                        )
                    },
                    floatingActionButtonPosition = FabPosition.End
                ) { innerPadding ->
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        for (index in 0 until 100) {
                            item { Text(text = "List item - $index", modifier = Modifier.padding(24.dp)) }
                        }
                    }
                }
            }
        }
    }
}
