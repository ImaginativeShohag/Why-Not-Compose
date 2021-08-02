package org.imaginativeworld.whynotcompose.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.imaginativeworld.whynotcompose.ui.screens.home.index.HomeIndexScreen
import org.imaginativeworld.whynotcompose.ui.screens.home.splash.SplashScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Animations : Screen("animation")
    object Compositions : Screen("composition")
}

sealed class HomeScreen(val route: String) {
    object Splash : AnimationsScreen("splash")
    object HomeIndex : AnimationsScreen("home/index")
}

sealed class AnimationsScreen(val route: String) {
    object AnimationIndex : AnimationsScreen("animation/index")
    object AnimationOne : AnimationsScreen("animation/1")
}

sealed class CompositionsScreen(val route: String) {
    object CompositionsIndex : CompositionsScreen("composition/index")
    object CompositionsAppBar : CompositionsScreen("composition/appbar")
}

// ================================================================
// NavHost
// ================================================================

@Composable
fun NavHostMain(
    modifier: Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        addHomeScreens(
            navController = navController
        )
        addAnimationScreens(
            navController = navController
        )
        addCompositionScreens(
            navController = navController
        )
    }
}

// ================================================================
// Top Level Screens
// ================================================================

private fun NavGraphBuilder.addHomeScreens(
    navController: NavHostController,
) {
    navigation(
        route = Screen.Home.route,
        startDestination = HomeScreen.Splash.route
    ) {
        addHomeSplashScreen(
            navController = navController
        )
        addHomeIndexScreen(
            navController = navController
        )
    }
}

private fun NavGraphBuilder.addAnimationScreens(
    navController: NavHostController,
) {
    navigation(
        route = Screen.Animations.route,
        startDestination = AnimationsScreen.AnimationIndex.route
    ) {
        addAnimationIndexScreen(
            navController = navController
        )
        addAnimationOneScreen(
            navController = navController
        )
    }
}

private fun NavGraphBuilder.addCompositionScreens(
    navController: NavHostController,
) {
    navigation(
        route = Screen.Compositions.route,
        startDestination = CompositionsScreen.CompositionsIndex.route
    ) {
        addCompositionIndexScreen(
            navController = navController
        )
        addCompositionAppBarScreen(
            navController = navController
        )
    }
}

// ================================================================
// Leaf Screens
// ================================================================

private fun NavGraphBuilder.addHomeSplashScreen(
    navController: NavHostController,
) {
    composable(HomeScreen.Splash.route) {
        SplashScreen(
            gotoHomeIndex = {
                navController.navigate(HomeScreen.HomeIndex.route) {
                    popUpTo(HomeScreen.HomeIndex.route)
                }
            }
        )
    }
}

private fun NavGraphBuilder.addHomeIndexScreen(
    navController: NavHostController,
) {
    composable(HomeScreen.HomeIndex.route) {
        HomeIndexScreen()
    }
}

private fun NavGraphBuilder.addAnimationIndexScreen(
    navController: NavHostController,
) {
    composable(AnimationsScreen.AnimationIndex.route) {
        BlankScreen()
    }
}

private fun NavGraphBuilder.addAnimationOneScreen(
    navController: NavHostController,
) {
    composable(AnimationsScreen.AnimationOne.route) {
        BlankScreen()
    }
}

private fun NavGraphBuilder.addCompositionIndexScreen(
    navController: NavHostController,
) {
    composable(CompositionsScreen.CompositionsIndex.route) {
        BlankScreen()
    }
}

private fun NavGraphBuilder.addCompositionAppBarScreen(
    navController: NavHostController,
) {
    composable(CompositionsScreen.CompositionsAppBar.route) {
        BlankScreen()
    }
}


// ================================================================
// Blank Screen
// ================================================================

@Composable
private fun BlankScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Coming soon...")
    }
}