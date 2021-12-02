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

package org.imaginativeworld.whynotcompose.ui.screens.animation.index

import org.imaginativeworld.whynotcompose.ui.screens.AnimationsScreen

data class Animation(
    val name: String,
    val route: AnimationsScreen,
) {
    companion object {
        val animationList = listOf(
            Animation(
                name = "Animated Text & Bubbles",
                route = AnimationsScreen.AnimationComposeOne,
            ),
            Animation(
                name = "Emudi",
                route = AnimationsScreen.AnimationEmudi,
            ),
            Animation(
                name = "Running Car",
                route = AnimationsScreen.AnimationRunningCar,
            ),
            Animation(
                name = "The Story",
                route = AnimationsScreen.AnimationTheStory,
            ),
        )
    }
}
