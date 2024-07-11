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

package org.imaginativeworld.whynotcompose.base.models

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

enum class UIThemeMode(val value: Int) {
    AUTO(0),
    LIGHT(1),
    DARK(2);

    companion object {
        private val map = entries.associateBy(UIThemeMode::value)

        fun fromValue(value: Int): UIThemeMode? = map[value]
    }
}

fun UIThemeMode.humanReadable() = when (this) {
    UIThemeMode.AUTO -> "Auto"
    UIThemeMode.LIGHT -> "Light"
    UIThemeMode.DARK -> "Dark"
}

fun UIThemeMode.nextMode() = when (this) {
    UIThemeMode.AUTO -> UIThemeMode.LIGHT
    UIThemeMode.LIGHT -> UIThemeMode.DARK
    UIThemeMode.DARK -> UIThemeMode.AUTO
}

@Composable
fun UIThemeMode.isLight(): Boolean {
    val isSystemInDarkTheme = isSystemInDarkTheme()

    return when (this) {
        UIThemeMode.AUTO -> !isSystemInDarkTheme
        UIThemeMode.LIGHT -> true
        UIThemeMode.DARK -> false
    }
}
