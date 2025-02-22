package com.example.store.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.store.theme.StoreAppTheme
import org.imaginativeworld.whynotcompose.base.models.UIThemeMode
import org.imaginativeworld.whynotcompose.base.utils.UIThemeController

@Composable
fun StoreMainScreen(
    updateUiThemeMode: (UIThemeMode) -> Unit,
    goBack: () -> Unit
) {
    val uiThemeMode by UIThemeController.uiThemeMode.collectAsState()
    val isSystemInDarkTheme = isSystemInDarkTheme()

    val isDarkMode by remember(isSystemInDarkTheme) {
        derivedStateOf {
            when (uiThemeMode) {
                UIThemeMode.AUTO -> isSystemInDarkTheme
                UIThemeMode.LIGHT -> false
                UIThemeMode.DARK -> true
            }
        }
    }

    StoreAppTheme(
        darkTheme = isDarkMode
    ) {
        StoreMainScreenSkeleton(
            updateUiThemeMode = updateUiThemeMode,
            goBack = goBack
        )
    }
}

@Preview
@Composable
private fun StoreMainScreenSkeletonPreview() {
    StoreAppTheme {
        StoreMainScreenSkeleton()
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun StoreMainScreenSkeleton(
    updateUiThemeMode: (UIThemeMode) -> Unit = {},
    goBack: () -> Unit = {}
) {
    val navController = rememberNavController()

    StoreNavHost(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        navController = navController,
        updateUiThemeMode = updateUiThemeMode,
        goBack = goBack
    )
}
