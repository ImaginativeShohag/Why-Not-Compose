package org.imaginativeworld.whynotcompose.template

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme

@Composable
fun TemplateMainScreen(
    isDarkMode: Boolean,
    turnOnDarkMode: (Boolean) -> Unit,
) {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = !isDarkMode
        )
    }

    TemplateMainScreenSkeleton(
        turnOnDarkMode = turnOnDarkMode
    )
}

@Preview
@Composable
fun TemplateMainScreenSkeletonPreview() {
    AppTheme {
        TemplateMainScreenSkeleton()
    }
}

@Composable
fun TemplateMainScreenSkeleton(
    turnOnDarkMode: (Boolean) -> Unit = {},
) {
    Scaffold {
        Box(
            modifier = Modifier
                .navigationBarsWithImePadding()
                .statusBarsPadding(),
        ) {
            // Your Content Here.
        }
    }
}