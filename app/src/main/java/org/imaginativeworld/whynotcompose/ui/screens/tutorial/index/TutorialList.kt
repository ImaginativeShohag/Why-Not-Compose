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
    val color: Color,
) {
    object Beginner : TutorialLevel(
        name = "Beginner",
        color = TailwindCSSColor.Green500,
    )

    object Intermediate : TutorialLevel(
        name = "Intermediate",
        color = TailwindCSSColor.Yellow500,
    )

    object Advanced : TutorialLevel(
        name = "Advanced",
        color = TailwindCSSColor.Red500,
    )
}

data class Tutorial(
    val name: String,
    val route: TutorialsScreen,
    val level: TutorialLevel,
) {
    companion object {
        val tutorialList = listOf(
            Tutorial(
                name = "Counter",
                route = TutorialsScreen.TutorialCounter,
                level = TutorialLevel.Beginner,
            ),
            Tutorial(
                name = "Counter with ViewModel",
                route = TutorialsScreen.TutorialCounterWithViewModel,
                level = TutorialLevel.Beginner,
            ),
            Tutorial(
                name = "AnimatedVisibility",
                route = TutorialsScreen.TutorialAnimatedVisibility,
                level = TutorialLevel.Beginner,
            ),
            Tutorial(
                name = "Lottie",
                route = TutorialsScreen.TutorialLottie,
                level = TutorialLevel.Beginner,
            ),
            Tutorial(
                name = "Select Image and Crop for Upload",
                route = TutorialsScreen.TutorialSelectImageAndCrop,
                level = TutorialLevel.Intermediate,
            ),
            Tutorial(
                name = "Capture Image and Crop for Upload",
                route = TutorialsScreen.TutorialCaptureImageAndCrop,
                level = TutorialLevel.Intermediate,
            ),
            Tutorial(
                name = "Permission",
                route = TutorialsScreen.TutorialPermission,
                level = TutorialLevel.Beginner,
            ),
            Tutorial(
                name = "Data Fetch and Paging",
                route = TutorialsScreen.TutorialDataFetchAndPaging,
                level = TutorialLevel.Advanced,
            ),
            Tutorial(
                name = "Tic-Tac-Toe",
                route = TutorialsScreen.TutorialTicTacToe,
                level = TutorialLevel.Intermediate,
            ),
        )
    }
}
