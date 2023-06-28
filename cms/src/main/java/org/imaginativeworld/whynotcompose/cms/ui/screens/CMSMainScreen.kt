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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import org.imaginativeworld.whynotcompose.base.utils.UIThemeController
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme

@Composable
fun CMSMainScreen(
    turnOnDarkMode: (Boolean) -> Unit,
    goBack: () -> Unit
) {
    val isDarkMode by UIThemeController.isDarkMode.collectAsState()

    CMSAppTheme(
        darkTheme = isDarkMode
    ) {
        CMSMainScreenSkeleton(
            turnOnDarkMode = turnOnDarkMode,
            goBack = goBack
        )
    }
}

@Preview
@Composable
fun CMSMainScreenSkeletonPreview() {
    CMSAppTheme {
        CMSMainScreenSkeleton()
    }
}

@Composable
fun CMSMainScreenSkeleton(
    turnOnDarkMode: (Boolean) -> Unit = {},
    goBack: () -> Unit = {}
) {
    val navController = rememberNavController()

    CMSNavHost(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        navController = navController,
        turnOnDarkMode = turnOnDarkMode,
        goBack = goBack
    )
}
