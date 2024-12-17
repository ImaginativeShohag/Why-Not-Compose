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

package org.imaginativeworld.whynotcompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import org.imaginativeworld.whynotcompose.base.models.UIThemeMode
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

@Composable
fun MainScreen(
    updateUiThemeMode: (UIThemeMode) -> Unit
) {
    MainScreenSkeleton(
        updateUiThemeMode = updateUiThemeMode
    )
}

@Preview
@Composable
private fun MainScreenSkeletonPreview() {
    AppTheme {
        MainScreenSkeleton(
            updateUiThemeMode = {}
        )
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun MainScreenSkeleton(
    updateUiThemeMode: (UIThemeMode) -> Unit
) {
    val navController = rememberNavController()

    NavHostMain(
        navController = navController,
        updateUiThemeMode = updateUiThemeMode,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .semantics {
                testTagsAsResourceId = true
            }
    )
}
