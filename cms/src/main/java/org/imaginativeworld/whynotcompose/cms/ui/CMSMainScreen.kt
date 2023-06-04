package org.imaginativeworld.whynotcompose.cms.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme

@Composable
fun CMSMainScreen(
    isDarkMode: Boolean,
    turnOnDarkMode: (Boolean) -> Unit,
    goBack: () -> Unit
) {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = !isDarkMode
        )
    }

    CMSAppTheme(
        darkTheme = isDarkMode
    ) {
        CMSMainScreenSkeleton(
            isDarkMode = isDarkMode,
            turnOnDarkMode = turnOnDarkMode,
            goBack = goBack
        )
    }
}

@Preview
@Composable
fun CMSMainScreenSkeletonPreview() {
    CMSAppTheme {
        CMSMainScreenSkeleton(isDarkMode = false)
    }
}

@Composable
fun CMSMainScreenSkeleton(
    isDarkMode: Boolean,
    turnOnDarkMode: (Boolean) -> Unit = {},
    goBack: () -> Unit = {}
) {
    val navController = rememberNavController()

    CMSNavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        isDarkMode = isDarkMode,
        turnOnDarkMode = turnOnDarkMode,
        goBack = goBack
    )
}
