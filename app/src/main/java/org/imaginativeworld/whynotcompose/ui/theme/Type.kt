package org.imaginativeworld.whynotcompose.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.imaginativeworld.whynotcompose.R

object AppFont {
    val TitilliumWeb = FontFamily(
        Font(R.font.titillium_web_regular),
        Font(R.font.titillium_web_italic, style = FontStyle.Italic),
        // Font(R.font.titillium_web_medium, FontWeight.Medium),
        // Font(R.font.titillium_web_medium_italic, FontWeight.Medium, style = FontStyle.Italic),
        Font(R.font.titillium_web_bold, FontWeight.Bold),
        Font(R.font.titillium_web_bold_italic, FontWeight.Bold, style = FontStyle.Italic),
    )
}

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = AppFont.TitilliumWeb,
    h1 = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = AppFont.TitilliumWeb,
        color = Color(0xFF2F4858),
        lineHeight = 27.sp
    ),
    body1 = TextStyle(
        fontSize = 16.sp,
        fontFamily = AppFont.TitilliumWeb,
        color = Color(0xFF2F4858)
    ),
    button = TextStyle(
        fontFamily = AppFont.TitilliumWeb,
        color = Color.White
    )
    /* Other default text styles to override
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)