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

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
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
private val defaultTypography = Typography()
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = AppFont.TitilliumWeb),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = AppFont.TitilliumWeb),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = AppFont.TitilliumWeb),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = AppFont.TitilliumWeb),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = AppFont.TitilliumWeb),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = AppFont.TitilliumWeb),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = AppFont.TitilliumWeb),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = AppFont.TitilliumWeb),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = AppFont.TitilliumWeb),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = AppFont.TitilliumWeb),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = AppFont.TitilliumWeb),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = AppFont.TitilliumWeb),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = AppFont.TitilliumWeb),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = AppFont.TitilliumWeb),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = AppFont.TitilliumWeb)
)
