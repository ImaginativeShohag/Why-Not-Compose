package org.imaginativeworld.whynotcompose.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.imaginativeworld.whynotcompose.ui.compositions.CustomSnackbarHost
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme

@Composable
fun MainScreen(
    turnOnDarkMode: (Boolean) -> Unit,
) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )
    }

    MainScreenSkeleton(
        turnOnDarkMode = turnOnDarkMode,
    )
}

@Preview
@Composable
fun MainScreenSkeletonPreview() {
    AppTheme {
        ProvideWindowInsets {
            MainScreenSkeleton()
        }
    }
}

@Composable
fun MainScreenSkeleton(
    turnOnDarkMode: (Boolean) -> Unit = {},
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = { CustomSnackbarHost(it) },
    ) {
        NavHostMain(
            modifier = Modifier
                .navigationBarsWithImePadding()
                .systemBarsPadding(),
            navController = navController,
            turnOnDarkMode = turnOnDarkMode,
        )
    }
}