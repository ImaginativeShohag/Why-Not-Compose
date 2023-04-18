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

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.imaginativeworld.whynotcompose.common.compose.R

object AppFont {
    val TitilliumWeb = FontFamily(
        Font(R.font.titillium_web_regular),
        Font(R.font.titillium_web_italic, style = FontStyle.Italic),
        Font(R.font.titillium_web_medium, FontWeight.Medium),
        Font(R.font.titillium_web_medium_italic, FontWeight.Medium, style = FontStyle.Italic),
        Font(R.font.titillium_web_bold, FontWeight.Bold),
        Font(R.font.titillium_web_bold_italic, FontWeight.Bold, style = FontStyle.Italic)
    )
}

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = AppFont.TitilliumWeb,
    h1 = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = AppFont.TitilliumWeb,
        lineHeight = 27.sp
    ),
    h2 = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = AppFont.TitilliumWeb,
        lineHeight = 24.sp
    ),
    body1 = TextStyle(
        fontSize = 16.sp,
        fontFamily = AppFont.TitilliumWeb
    ),
    body2 = TextStyle(
        fontSize = 16.sp,
        fontFamily = AppFont.TitilliumWeb
    ),
    button = TextStyle(
        fontFamily = AppFont.TitilliumWeb,
        fontWeight = FontWeight.Medium
    )
    /* Other default text styles to override
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)
