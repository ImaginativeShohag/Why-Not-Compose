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
import org.imaginativeworld.whynotcompose.ui.screens.composition.appbar.AppBarScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.index.CompositionIndexScreen
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
    object CompositionsButton : CompositionsScreen("composition/button")
    object CompositionsCheckBox : CompositionsScreen("composition/checkbox")
    object CompositionsCodeField : CompositionsScreen("composition/codefield")
    object CompositionsDialog : CompositionsScreen("composition/dialog")
    object CompositionsDropDown : CompositionsScreen("composition/dropdown")
    object CompositionsList : CompositionsScreen("composition/list")
    object CompositionsLoading : CompositionsScreen("composition/loading")
    object CompositionsRadioButton : CompositionsScreen("composition/radiobutton")
    object CompositionsSnackbar : CompositionsScreen("composition/snackbar")
    object CompositionsTextField : CompositionsScreen("composition/textfield")
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

        // Below compositions will be just few lines.
        // So, we will not use functions for those.
        composable(AnimationsScreen.AnimationOne.route) {
            BlankScreen()
        }
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

        // Below compositions will be just few lines.
        // So, we will not use functions for those.
        composable(CompositionsScreen.CompositionsAppBar.route) {
            AppBarScreen()
        }

        composable(CompositionsScreen.CompositionsButton.route) {
            BlankScreen()
        }

        composable(CompositionsScreen.CompositionsCheckBox.route) {
            BlankScreen()
        }

        composable(CompositionsScreen.CompositionsCodeField.route) {
            BlankScreen()
        }

        composable(CompositionsScreen.CompositionsDialog.route) {
            BlankScreen()
        }

        composable(CompositionsScreen.CompositionsDropDown.route) {
            BlankScreen()
        }

        composable(CompositionsScreen.CompositionsList.route) {
            BlankScreen()
        }

        composable(CompositionsScreen.CompositionsLoading.route) {
            BlankScreen()
        }

        composable(CompositionsScreen.CompositionsRadioButton.route) {
            BlankScreen()
        }

        composable(CompositionsScreen.CompositionsSnackbar.route) {
            BlankScreen()
        }

        composable(CompositionsScreen.CompositionsTextField.route) {
            BlankScreen()
        }
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
                    popUpTo(HomeScreen.Splash.route) {
                        // TODO: Check later if this is fixed.
                        // It throws a crash which is an internal bug.
                        // Will be fixed in Navigation 2.4.0-alpha06 release.
                        // Track: https://issuetracker.google.com/issues/194301889
                        // inclusive = true
                    }
                }
            }
        )
    }
}

private fun NavGraphBuilder.addHomeIndexScreen(
    navController: NavHostController,
) {
    composable(HomeScreen.HomeIndex.route) {
        HomeIndexScreen(
            gotoAnimationIndex = {
                navController.navigate(Screen.Animations.route)
            },
            gotoCompositionIndex = {
                navController.navigate(Screen.Compositions.route)
            }
        )
    }
}

// ----------------------------------------------------------------

private fun NavGraphBuilder.addAnimationIndexScreen(
    navController: NavHostController,
) {
    composable(AnimationsScreen.AnimationIndex.route) {
        BlankScreen()
    }
}

// ----------------------------------------------------------------

private fun NavGraphBuilder.addCompositionIndexScreen(
    navController: NavHostController,
) {
    composable(CompositionsScreen.CompositionsIndex.route) {
        CompositionIndexScreen(
            navigate = { screen ->
                navController.navigate(screen.route)
            }
        )
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