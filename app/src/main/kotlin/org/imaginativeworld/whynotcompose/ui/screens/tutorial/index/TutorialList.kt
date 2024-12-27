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

package org.imaginativeworld.whynotcompose.ui.screens.tutorial.index

import androidx.compose.ui.graphics.Color
import org.imaginativeworld.whynotcompose.common.compose.theme.TailwindCSSColor
import org.imaginativeworld.whynotcompose.ui.screens.TutorialsScreen

sealed class TutorialLevel(
    val name: String,
    val value: Int,
    val color: Color
) {
    data object Beginner : TutorialLevel(
        name = "Beginner",
        value = 1,
        color = TailwindCSSColor.Green500
    )

    data object Intermediate : TutorialLevel(
        name = "Intermediate",
        value = 2,
        color = TailwindCSSColor.Yellow500
    )

    data object Advanced : TutorialLevel(
        name = "Advanced",
        value = 3,
        color = TailwindCSSColor.Red500
    )
}

data class Tutorial(
    val name: String,
    val description: String? = null,
    val route: TutorialsScreen,
    val level: TutorialLevel,
    val testTag: String = "tutorial-${route.route}"
) {
    companion object {
        val tutorialList = listOf(
            Tutorial(
                name = "Counter",
                description = "Simple counter.",
                route = TutorialsScreen.TutorialCounter,
                level = TutorialLevel.Beginner
            ),
            Tutorial(
                name = "Counter with ViewModel",
                description = "Control counter using `ViewModel`.",
                route = TutorialsScreen.TutorialCounterWithViewModel,
                level = TutorialLevel.Beginner
            ),
            Tutorial(
                name = "AnimatedVisibility",
                description = "Animate UI using `AnimatedVisibility`.",
                route = TutorialsScreen.TutorialAnimatedVisibility,
                level = TutorialLevel.Beginner
            ),
            Tutorial(
                name = "Lottie",
                description = "Exploring `Lottie`.",
                route = TutorialsScreen.TutorialLottie,
                level = TutorialLevel.Beginner
            ),
            Tutorial(
                name = "Select Image and Crop for Upload",
                description = "Select image from gallery and crop image using `uCrop`.",
                route = TutorialsScreen.TutorialSelectImageAndCrop,
                level = TutorialLevel.Intermediate
            ),
            Tutorial(
                name = "Capture Image and Crop for Upload",
                description = "Capture image from default camera app and crop image using `uCrop`.",
                route = TutorialsScreen.TutorialCaptureImageAndCrop,
                level = TutorialLevel.Intermediate
            ),
            Tutorial(
                name = "Permission",
                description = "Check and handle permission from Compose UI.",
                route = TutorialsScreen.TutorialPermission,
                level = TutorialLevel.Beginner
            ),
            Tutorial(
                name = "Data Fetch and Paging",
                description = "Fetch data from server using `Android Jetpack Paging` library.",
                route = TutorialsScreen.TutorialDataFetchAndPaging,
                level = TutorialLevel.Advanced
            ),
            Tutorial(
                name = "Tic-Tac-Toe",
                description = "Simple game with (kind of) supervised learning AI.",
                route = TutorialsScreen.TutorialTicTacToe,
                level = TutorialLevel.Advanced
            ),
            Tutorial(
                name = "ExoPlayer",
                description = "Example usage of `ExoPlayer` with Compose.",
                route = TutorialsScreen.TutorialExoPlayer,
                level = TutorialLevel.Advanced
            ),
            Tutorial(
                name = "CMS",
                description = "Example of a Content Management System.",
                route = TutorialsScreen.TutorialCMS,
                level = TutorialLevel.Advanced
            ),
            Tutorial(
                name = "Deep Link",
                description = "Example of Deep Link.",
                route = TutorialsScreen.TutorialDeepLink,
                level = TutorialLevel.Intermediate
            ),
            Tutorial(
                name = "Navigation Data Pass",
                description = "Example of data passing in `Navigation Component`.",
                route = TutorialsScreen.TutorialNavDataPassHome,
                level = TutorialLevel.Intermediate
            ),
            Tutorial(
                name = "Reactive Model",
                description = "Example of reactive model in `MVVM`.",
                route = TutorialsScreen.TutorialReactiveModel,
                level = TutorialLevel.Beginner
            ),
            Tutorial(
                name = "Navigation blank screen issue",
                description = "Solutions for `popBackStack()` blank screen issue.",
                route = TutorialsScreen.TutorialPopBackStack,
                level = TutorialLevel.Intermediate,
                testTag = "tutorial-popbackstack"
            ),
            Tutorial(
                name = "Baseline profiles",
                description = "Check the baseline profile install status using `ProfileVerifier`.",
                route = TutorialsScreen.TutorialBaselineProfiles,
                level = TutorialLevel.Intermediate
            ),
            Tutorial(
                name = "Barcode Scanner",
                description = "Multiple ways to scan barcode.",
                route = TutorialsScreen.TutorialBarcodeScanner,
                level = TutorialLevel.Intermediate
            )
        )
            .sortedBy { it.name }
            .sortedBy { it.level.value }
    }
}
