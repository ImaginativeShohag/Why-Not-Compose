package org.imaginativeworld.whynotcompose.cms.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import org.imaginativeworld.whynotcompose.base.utils.UIThemeController
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme

@Composable
fun CMSMainScreen(
    turnOnDarkMode: (Boolean) -> Unit,
    goBack: () -> Unit
) {
    val isDarkMode by UIThemeController.isDarkMode.collectAsState()

    CMSAppTheme(
        darkTheme = isDarkMode
    ) {
        CMSMainScreenSkeleton(
            turnOnDarkMode = turnOnDarkMode,
            goBack = goBack
        )
    }
}

@Preview
@Composable
fun CMSMainScreenSkeletonPreview() {
    CMSAppTheme {
        CMSMainScreenSkeleton()
    }
}

@Composable
fun CMSMainScreenSkeleton(
    turnOnDarkMode: (Boolean) -> Unit = {},
    goBack: () -> Unit = {}
) {
    val navController = rememberNavController()

    CMSNavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        turnOnDarkMode = turnOnDarkMode,
        goBack = goBack
    )
}
