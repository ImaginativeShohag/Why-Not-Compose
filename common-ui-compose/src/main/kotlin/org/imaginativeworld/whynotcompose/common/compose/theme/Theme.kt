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

package org.imaginativeworld.whynotcompose.common.compose.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme
    @Composable get() = darkColorScheme(
        primary = TailwindCSSColor.Green700,
        secondary = TailwindCSSColor.Pink700,
        background = TailwindCSSColor.Gray900,
        surface = Color.Black,
        error = TailwindCSSColor.Red700,

        onPrimary = TailwindCSSColor.Gray50,
        onSecondary = TailwindCSSColor.Gray50,
        onBackground = TailwindCSSColor.Gray50,
        onSurface = TailwindCSSColor.Gray50
    )

private val LightColorScheme
    @Composable get() = lightColorScheme(
        primary = TailwindCSSColor.Green500,
        secondary = TailwindCSSColor.Pink500,
        background = TailwindCSSColor.Gray50,
        surface = Color.White,
        error = TailwindCSSColor.Red500,

        onPrimary = TailwindCSSColor.Gray50,
        onSecondary = TailwindCSSColor.Gray50,
        onBackground = TailwindCSSColor.Gray900,
        onSurface = TailwindCSSColor.Gray900
    )

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
