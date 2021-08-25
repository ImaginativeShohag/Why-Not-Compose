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

package org.imaginativeworld.whynotcompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = TailwindCSSColor.Green700,
    primaryVariant = TailwindCSSColor.Green900,
    secondary = TailwindCSSColor.Pink700,
    secondaryVariant = TailwindCSSColor.Pink900,
    background = TailwindCSSColor.Gray900,
    surface = Color.Black,
    error = TailwindCSSColor.Red500,

    onPrimary = TailwindCSSColor.Gray50,
    onSecondary = TailwindCSSColor.Gray50,
    onBackground = TailwindCSSColor.Gray50,
    onSurface = TailwindCSSColor.Gray50,
)

private val LightColorPalette = lightColors(
    primary = TailwindCSSColor.Green500,
    primaryVariant = TailwindCSSColor.Green700,
    secondary = TailwindCSSColor.Pink500,
    secondaryVariant = TailwindCSSColor.Pink700,
    background = TailwindCSSColor.Gray50,
    surface = Color.White,
    error = TailwindCSSColor.Red500,

    onPrimary = TailwindCSSColor.Gray50,
    onSecondary = TailwindCSSColor.Gray50,
    onBackground = TailwindCSSColor.Gray900,
    onSurface = TailwindCSSColor.Gray900,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}