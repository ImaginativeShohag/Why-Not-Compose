package org.imaginativeworld.whynotcompose.cms.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

@Composable
fun CMSMainScreen(
    isDarkMode: Boolean,
    turnOnDarkMode: (Boolean) -> Unit
) {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = !isDarkMode
        )
    }

    CMSMainScreenSkeleton(
        turnOnDarkMode = turnOnDarkMode
    )
}

@Preview
@Composable
fun CMSMainScreenSkeletonPreview() {
    AppTheme {
        CMSMainScreenSkeleton()
    }
}

@Composable
fun CMSMainScreenSkeleton(
    turnOnDarkMode: (Boolean) -> Unit = {}
) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        CMSNavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            turnOnDarkMode = turnOnDarkMode
        )
    }
}
