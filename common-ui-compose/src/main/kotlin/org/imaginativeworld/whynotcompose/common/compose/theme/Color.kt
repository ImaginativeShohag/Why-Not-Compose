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

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import org.imaginativeworld.whynotcompose.base.models.isLight
import org.imaginativeworld.whynotcompose.base.utils.UIThemeController
import org.imaginativeworld.whynotcompose.common.compose.R

val ColorScheme.composeThemeColor: Color
    @Composable get() = Color(0xFF4285f4)

val ColorScheme.inputBackground: Color
    @Composable get() {
        val uiThemeMode by UIThemeController.uiThemeMode.collectAsState()
        return if (uiThemeMode.isLight()) TailwindCSSColor.Gray100 else TailwindCSSColor.Gray800
    }

val ColorScheme.onInputBackground: Color
    @Composable get() {
        val uiThemeMode by UIThemeController.uiThemeMode.collectAsState()
        return if (uiThemeMode.isLight()) TailwindCSSColor.Gray900 else TailwindCSSColor.Gray50
    }

val ColorScheme.errorInputBackground: Color
    @Composable get() {
        val uiThemeMode by UIThemeController.uiThemeMode.collectAsState()
        return if (uiThemeMode.isLight()) TailwindCSSColor.Red500.copy(.1f) else TailwindCSSColor.Red900.copy(.95f)
    }

object AppColor {
//    val ExampleColor = Color(0xff123456)
}

object FlatColor {
    // Color from: https://flatuicolors.com/palette/defo
    val FlatAwesomeGreen1 = Color(0xFF1abc9c)
    val FlatAwesomeGreen2 = Color(0xFF16a085)
    val FlatGreen1 = Color(0xFF2ecc71)
    val FlatGreen2 = Color(0xFF27ae60)
    val FlatBlue1 = Color(0xFF3498db)
    val FlatBlue2 = Color(0xFF2980b9)
    val FlatPink1 = Color(0xFF9b59b6)
    val FlatGreyDark1 = Color(0xFF34495e)
    val FlatGreyDark2 = Color(0xFF2c3e50)
    val FlatGreyNormal1 = Color(0xFF95a5a6)
    val FlatGreyNormal2 = Color(0xFF7f8c8d)
    val FlatGreyLight1 = Color(0xFFecf0f1)
    val FlatGreyLight2 = Color(0xFFbdc3c7)
    val FlatRed1 = Color(0xFFe74c3c)
    val FlatRed2 = Color(0xFFc0392b)
    val FlatOrange1 = Color(0xFFe67e22)
    val FlatOrange2 = Color(0xFFd35400)
    val FlatYellow1 = Color(0xFFf1c40f)
    val FlatYellow2 = Color(0xFFf39c12)
}

object TailwindCSSColor {
    // Color from: https://tailwindcss.com/docs/customizing-colors

    // colors.trueGray
    val Gray50 = Color(0xFFFAFAFA)
    val Gray100 = Color(0xFFF5F5F5)
    val Gray200 = Color(0xFFE5E5E5)
    val Gray300 = Color(0xFFD4D4D4)
    val Gray400 = Color(0xFFA3A3A3)
    val Gray500 = Color(0xFF737373)
    val Gray600 = Color(0xFF525252)
    val Gray700 = Color(0xFF404040)
    val Gray800 = Color(0xFF262626)
    val Gray900 = Color(0xFF171717)

    // colors.red
    val Red500 = Color(0xFFEF4444)
    val Red700 = Color(0xFFB91C1C)
    val Red900 = Color(0xFF7F1D1D)

    // colors.amber
    val Yellow500 = Color(0xFFF59E0B)
    val Yellow700 = Color(0xFFB45309)
    val Yellow900 = Color(0xFF78350F)

    // colors.emerald
    val Green500 = Color(0xFF10B981)
    val Green700 = Color(0xFF047857)
    val Green900 = Color(0xFF064E3B)

    // colors.blue
    val Blue500 = Color(0xFF3B82F6)
    val Blue700 = Color(0xFF1D4ED8)
    val Blue900 = Color(0xFF1E3A8A)

    // colors.indigo
    val Indigo500 = Color(0xFF6366F1)
    val Indigo700 = Color(0xFF4338CA)
    val Indigo900 = Color(0xFF312E81)

    // colors.violet
    val Purple500 = Color(0xFF8B5CF6)
    val Purple700 = Color(0xFF6D28D9)
    val Purple900 = Color(0xFF4C1D95)

    // colors.pink
    val Pink500 = Color(0xFFEC4899)
    val Pink700 = Color(0xFFBE185D)
    val Pink900 = Color(0xFF831843)
}

/**
 * Apple system colors.
 *
 * Reference: https://www.figma.com/community/file/1248375255495415511
 */
object AppleSystemColor {
    val Red: Color
        @Composable get() = colorResource(id = R.color.ios_system_red)
    val Orange: Color
        @Composable get() = colorResource(id = R.color.ios_system_orange)
    val Yellow: Color
        @Composable get() = colorResource(id = R.color.ios_system_yellow)
    val Green: Color
        @Composable get() = colorResource(id = R.color.ios_system_green)
    val Mint: Color
        @Composable get() = colorResource(id = R.color.ios_system_mint)
    val Teal: Color
        @Composable get() = colorResource(id = R.color.ios_system_teal)
    val Cyan: Color
        @Composable get() = colorResource(id = R.color.ios_system_cyan)
    val Blue: Color
        @Composable get() = colorResource(id = R.color.ios_system_blue)
    val Indigo: Color
        @Composable get() = colorResource(id = R.color.ios_system_indigo)
    val Purple: Color
        @Composable get() = colorResource(id = R.color.ios_system_purple)
    val Pink: Color
        @Composable get() = colorResource(id = R.color.ios_system_pink)
    val Brown: Color
        @Composable get() = colorResource(id = R.color.ios_system_brown)
    val Gray: Color
        @Composable get() = colorResource(id = R.color.ios_system_gray)
    val Gray2: Color
        @Composable get() = colorResource(id = R.color.ios_system_gray2)
    val Gray3: Color
        @Composable get() = colorResource(id = R.color.ios_system_gray3)
    val Gray4: Color
        @Composable get() = colorResource(id = R.color.ios_system_gray4)
    val Gray5: Color
        @Composable get() = colorResource(id = R.color.ios_system_gray5)
    val Gray6: Color
        @Composable get() = colorResource(id = R.color.ios_system_gray6)
}
