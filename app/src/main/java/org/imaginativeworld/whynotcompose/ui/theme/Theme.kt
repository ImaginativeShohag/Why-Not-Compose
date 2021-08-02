package org.imaginativeworld.whynotcompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color(0xff064E3B),
    primaryVariant = Color(0xff064E3B),
    secondary = Color(0xff064E3B),
    background = Color(0xff111827),
    surface = Color(0xff111827),

    onPrimary = Color(0xffF9FAFB),
    onSecondary = Color(0xffF9FAFB),
    onBackground = Color(0xffF9FAFB),
    onSurface = Color(0xffF9FAFB),
)

private val LightColorPalette = lightColors(
    primary = Color(0xff10B981),
    primaryVariant = Color(0xff10B981),
    secondary = Color(0xff10B981),
    background = Color(0xffF9FAFB),
    surface = Color(0xffF9FAFB),

    onPrimary = Color(0xff111827),
    onSecondary = Color(0xff111827),
    onBackground = Color(0xff111827),
    onSurface = Color(0xff111827),
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