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

package org.imaginativeworld.whynotcompose.ui.screens.composition.badge

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import org.imaginativeworld.whynotcompose.base.extensions.toWords
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

// Source;
// https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/material/material/samples/src/main/java/androidx/compose/material/samples/BadgeSamples.kt

@Composable
fun BadgeScreen(
    goBack: () -> Unit
) {
    BadgeScreenSkeleton(
        goBack = goBack
    )
}

@PreviewLightDark
@Composable
private fun BadgeScreenSkeletonPreviewDark() {
    AppTheme {
        BadgeScreenSkeleton()
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun BadgeScreenSkeleton(
    goBack: () -> Unit = {}
) {
    var homeCounter by remember { mutableIntStateOf(0) }
    var favoriteCounter by remember { mutableIntStateOf(0) }
    var profileCounter by remember { mutableIntStateOf(0) }

    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            AppComponent.Header(
                "Badge",
                goBack = goBack
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = {
                        BadgedBox(badge = { Badge { Text("$homeCounter") } }) {
                            Icon(
                                Icons.Rounded.Home,
                                contentDescription = "Home"
                            )
                        }
                    },
                    selected = true,
                    onClick = {
                        homeCounter++
                    }
                )
                NavigationBarItem(
                    icon = {
                        BadgedBox(badge = {
                            if (favoriteCounter > 0) {
                                Badge {
                                    Text(if (favoriteCounter > 9) "9+" else "$favoriteCounter")
                                }
                            }
                        }) {
                            Icon(
                                Icons.Rounded.Favorite,
                                contentDescription = "Favorite"
                            )
                        }
                    },
                    selected = false,
                    onClick = {
                        favoriteCounter++
                    }
                )
                NavigationBarItem(
                    icon = {
                        BadgedBox(badge = {
                            Badge {
                                Text(
                                    if (profileCounter > 9) {
                                        "infinite"
                                    } else {
                                        profileCounter.toWords()
                                    }
                                )
                            }
                        }) {
                            Icon(
                                Icons.Rounded.Person,
                                contentDescription = "Profile"
                            )
                        }
                    },
                    selected = false,
                    onClick = {
                        profileCounter++
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Badge Example")
        }
    }
}
