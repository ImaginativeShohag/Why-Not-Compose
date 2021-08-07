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