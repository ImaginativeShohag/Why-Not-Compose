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
import org.imaginativeworld.whynotcompose.ui.screens.composition.button.ButtonScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.card.CardScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.checkbox.CheckBoxScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.dropdown.DropDownMenuScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.index.CompositionIndexScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.loadingindicator.LoadingIndicatorScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.radiobutton.RadioButtonScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.switch.SwitchScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.textfield.TextFieldScreen
import org.imaginativeworld.whynotcompose.ui.screens.home.index.HomeIndexScreen
import org.imaginativeworld.whynotcompose.ui.screens.home.splash.SplashScreen
import org.imaginativeworld.whynotcompose.ui.screens.ui.index.UiIndexScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Animations : Screen("animation")
    object Compositions : Screen("composition")
    object UI : Screen("ui")
}

sealed class HomeScreen(val route: String) {
    object Splash : HomeScreen("splash")
    object HomeIndex : HomeScreen("home/index")
}

sealed class AnimationsScreen(val route: String) {
    object AnimationIndex : AnimationsScreen("animation/index")
    object AnimationOne : AnimationsScreen("animation/1")
}

sealed class CompositionsScreen(val route: String) {
    object CompositionsIndex : CompositionsScreen("composition/index")

    object CompositionsAppBar : CompositionsScreen("composition/appbar")
    object CompositionsButton : CompositionsScreen("composition/button")
    object CompositionsCard : CompositionsScreen("composition/card")
    object CompositionsCheckBox : CompositionsScreen("composition/checkbox")
    object CompositionsCodeField : CompositionsScreen("composition/codefield")
    object CompositionsDialog : CompositionsScreen("composition/dialog")
    object CompositionsDropDownMenu : CompositionsScreen("composition/dropdownmenu")
    object CompositionsList : CompositionsScreen("composition/list")
    object CompositionsLoadingIndicator : CompositionsScreen("composition/loadingindicator")
    object CompositionsRadioButton : CompositionsScreen("composition/radiobutton")
    object CompositionsSnackbar : CompositionsScreen("composition/snackbar")
    object CompositionsSwitch : CompositionsScreen("composition/switch")
    object CompositionsTextField : CompositionsScreen("composition/textfield")
}

sealed class UiScreen(val route: String) {
    object UiIndex : UiScreen("animation/index")

    object UiWebView : UiScreen("animation/webview")
    object UiMapView : UiScreen("animation/mapview")
}

// ================================================================
// NavHost
// ================================================================

@Composable
fun NavHostMain(
    modifier: Modifier,
    navController: NavHostController,
    turnOnDarkMode: (Boolean) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        addHomeScreens(
            navController = navController,
            turnOnDarkMode = turnOnDarkMode,
        )
        addAnimationScreens(
            navController = navController
        )
        addCompositionScreens(
            navController = navController
        )
        addUiScreens(
            navController = navController
        )
    }
}

// ================================================================
// Top Level Screens
// ================================================================

private fun NavGraphBuilder.addHomeScreens(
    navController: NavHostController,
    turnOnDarkMode: (Boolean) -> Unit,
) {
    navigation(
        route = Screen.Home.route,
        startDestination = HomeScreen.Splash.route
    ) {
        addHomeSplashScreen(
            navController = navController
        )
        addHomeIndexScreen(
            navController = navController,
            turnOnDarkMode = turnOnDarkMode,
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
            ButtonScreen()
        }

        composable(CompositionsScreen.CompositionsCard.route) {
            CardScreen()
        }

        composable(CompositionsScreen.CompositionsCheckBox.route) {
            CheckBoxScreen()
        }

        composable(CompositionsScreen.CompositionsCodeField.route) {
            BlankScreen()
        }

        composable(CompositionsScreen.CompositionsDialog.route) {
            BlankScreen()
        }

        composable(CompositionsScreen.CompositionsDropDownMenu.route) {
            DropDownMenuScreen()
        }

        composable(CompositionsScreen.CompositionsList.route) {
            BlankScreen()
        }

        composable(CompositionsScreen.CompositionsLoadingIndicator.route) {
            LoadingIndicatorScreen()
        }

        composable(CompositionsScreen.CompositionsRadioButton.route) {
            RadioButtonScreen()
        }

        composable(CompositionsScreen.CompositionsSnackbar.route) {
            BlankScreen()
        }

        composable(CompositionsScreen.CompositionsSwitch.route) {
            SwitchScreen()
        }

        composable(CompositionsScreen.CompositionsTextField.route) {
            TextFieldScreen()
        }
    }
}

private fun NavGraphBuilder.addUiScreens(
    navController: NavHostController,
) {
    navigation(
        route = Screen.UI.route,
        startDestination = AnimationsScreen.AnimationIndex.route
    ) {
        addUiIndexScreen(
            navController = navController
        )

        // Below compositions will be just few lines.
        // So, we will not use functions for those.
        composable(UiScreen.UiWebView.route) {
            BlankScreen()
        }

        composable(UiScreen.UiMapView.route) {
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
                         inclusive = true
                    }
                }
            }
        )
    }
}

private fun NavGraphBuilder.addHomeIndexScreen(
    navController: NavHostController,
    turnOnDarkMode: (Boolean) -> Unit,
) {
    composable(HomeScreen.HomeIndex.route) {
        HomeIndexScreen(
            navigate = { screen ->
                navController.navigate(screen.route)
            },
            turnOnDarkMode = turnOnDarkMode
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

// ----------------------------------------------------------------

private fun NavGraphBuilder.addUiIndexScreen(
    navController: NavHostController,
) {
    composable(UiScreen.UiIndex.route) {
        UiIndexScreen(
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