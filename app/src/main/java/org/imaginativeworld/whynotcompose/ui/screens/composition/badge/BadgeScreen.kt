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

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import org.imaginativeworld.whynotcompose.base.extensions.toWords
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

// Source;
// https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/material/material/samples/src/main/java/androidx/compose/material/samples/BadgeSamples.kt

@Composable
fun BadgeScreen() {
    BadgeScreenSkeleton()
}

@Preview
@Composable
fun BadgeScreenSkeletonPreview() {
    AppTheme {
        BadgeScreenSkeleton()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BadgeScreenSkeletonPreviewDark() {
    AppTheme {
        BadgeScreenSkeleton()
    }
}

@Composable
fun BadgeScreenSkeleton() {
    Scaffold(
        Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 16.dp, end = 16.dp)
        ) {
            AppComponent.Header("Badge")

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

            AppComponent.MediumSpacer()

            // ----------------------------------------------------------------

            var homeCounter by remember { mutableStateOf(0) }
            var favoriteCounter by remember { mutableStateOf(0) }
            var profileCounter by remember { mutableStateOf(0) }

            BottomNavigation {
                BottomNavigationItem(
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
                BottomNavigationItem(
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
                BottomNavigationItem(
                    icon = {
                        BadgedBox(badge = {
                            Badge {
                                Text(
                                    if (profileCounter > 9) "infinite"
                                    else profileCounter.toWords()
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

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.BigSpacer()
        }
    }
}
