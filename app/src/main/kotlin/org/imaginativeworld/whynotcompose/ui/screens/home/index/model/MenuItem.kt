/*
 * Copyright 2025 Md. Mahmudul Hasan Shohag
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

package org.imaginativeworld.whynotcompose.ui.screens.home.index.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.common.compose.theme.AppleSystemColor
import org.imaginativeworld.whynotcompose.ui.screens.Screen

data class MenuItem(
    val name: String,
    @DrawableRes val icon: Int,
    val color: @Composable () -> Color,
    val route: Screen
) {
    companion object {
        val menuItems = listOf(
            MenuItem(
                name = "Animations",
                icon = R.drawable.ic_round_animation_24,
                color = { AppleSystemColor.Orange },
                route = Screen.Animations
            ),
            MenuItem(
                name = "Compositions",
                icon = R.drawable.ic_round_widgets_24,
                color = { AppleSystemColor.Red },
                route = Screen.Compositions
            ),
            MenuItem(
                name = "UIs",
                icon = R.drawable.ic_round_grid_view_24,
                color = { AppleSystemColor.Blue },
                route = Screen.UIs
            ),
            MenuItem(
                name = "Tutorials",
                icon = R.drawable.ic_round_sticky_note_2_24,
                color = { AppleSystemColor.Purple },
                route = Screen.Tutorials
            )
        )
    }
}
