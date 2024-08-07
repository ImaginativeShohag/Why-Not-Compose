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

package org.imaginativeworld.whynotcompose.cms.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import org.imaginativeworld.whynotcompose.base.models.UIThemeMode
import org.imaginativeworld.whynotcompose.base.utils.UIThemeController
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme

@Composable
fun CMSMainScreen(
    updateUiThemeMode: (UIThemeMode) -> Unit,
    goBack: () -> Unit
) {
    val uiThemeMode by UIThemeController.uiThemeMode.collectAsState()
    val isSystemInDarkTheme = isSystemInDarkTheme()

    val isDarkMode by remember(isSystemInDarkTheme) {
        derivedStateOf {
            when (uiThemeMode) {
                UIThemeMode.AUTO -> isSystemInDarkTheme
                UIThemeMode.LIGHT -> false
                UIThemeMode.DARK -> true
            }
        }
    }

    CMSAppTheme(
        darkTheme = isDarkMode
    ) {
        CMSMainScreenSkeleton(
            updateUiThemeMode = updateUiThemeMode,
            goBack = goBack
        )
    }
}

@Preview
@Composable
private fun CMSMainScreenSkeletonPreview() {
    CMSAppTheme {
        CMSMainScreenSkeleton()
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun CMSMainScreenSkeleton(
    updateUiThemeMode: (UIThemeMode) -> Unit = {},
    goBack: () -> Unit = {}
) {
    val navController = rememberNavController()

    CMSNavHost(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        navController = navController,
        updateUiThemeMode = updateUiThemeMode,
        goBack = goBack
    )
}
