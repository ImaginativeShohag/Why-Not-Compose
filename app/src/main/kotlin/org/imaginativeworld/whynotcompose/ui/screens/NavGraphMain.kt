/*
 * Copyright 2021 Md. Mahmudul Hasan Shohag
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ------------------------------------------------------------------------
 *
 * Project: Why Not Compose!
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * Source: https://github.com/ImaginativeShohag/Why-Not-Compose
 */

package org.imaginativeworld.whynotcompose.ui.screens

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import org.imaginativeworld.whynotcompose.base.extensions.getJsonFromObj
import org.imaginativeworld.whynotcompose.base.extensions.getObjFromJson
import org.imaginativeworld.whynotcompose.base.extensions.navArg
import org.imaginativeworld.whynotcompose.base.extensions.navResult
import org.imaginativeworld.whynotcompose.base.extensions.navigate
import org.imaginativeworld.whynotcompose.base.extensions.popBackStackOrIgnore
import org.imaginativeworld.whynotcompose.base.extensions.popBackStackWithResult
import org.imaginativeworld.whynotcompose.base.models.UIThemeMode
import org.imaginativeworld.whynotcompose.cms.ui.screens.CMSMainScreen
import org.imaginativeworld.whynotcompose.exoplayer.ExoPlayerScreen
import org.imaginativeworld.whynotcompose.models.DemoData
import org.imaginativeworld.whynotcompose.models.MapPlace
import org.imaginativeworld.whynotcompose.popbackstack.PopBackStackActivity
import org.imaginativeworld.whynotcompose.tictactoe.TicTacToeScreen
import org.imaginativeworld.whynotcompose.tictactoe.TicTacToeViewModel
import org.imaginativeworld.whynotcompose.ui.screens.animation.composeone.ComposeOneScreen
import org.imaginativeworld.whynotcompose.ui.screens.animation.emudi.EmudiScreen
import org.imaginativeworld.whynotcompose.ui.screens.animation.index.AnimationIndexScreen
import org.imaginativeworld.whynotcompose.ui.screens.animation.runningcar.RunningCarScreen
import org.imaginativeworld.whynotcompose.ui.screens.animation.thestory.TheStoryScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.appbar.AppBarScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.badge.BadgeScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.bottomnavigation.BottomNavigationScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.button.ButtonScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.card.CardScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.checkbox.CheckBoxScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.dialog.DialogScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.dropdown.DropDownMenuScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.floatingactionbutton.FloatingActionButtonScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.index.CompositionIndexScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.list.LazyColumnIndexScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.list.LazyRowScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.list.LazyVerticalGridScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.list.ListColumnScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.list.ListIndexScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.list.ListRowScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.list.lazycolumn.LazyColumnSampleOneScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.list.lazycolumn.LazyColumnSampleTwoScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.listitem.ListItemScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.loadingindicator.LoadingIndicatorScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.radiobutton.RadioButtonScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.scaffold.ScaffoldIndexScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.scaffold.ScaffoldWithCoroutinesSnackbarScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.scaffold.ScaffoldWithCustomSnackbarScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.scaffold.ScaffoldWithIndefiniteSnackbarScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.scaffold.ScaffoldWithMultilineSnackbarScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.scaffold.ScaffoldWithSimpleSnackbarScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.scaffold.SimpleScaffoldWithTopBarScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.slider.SliderScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.snackbar.SnackbarScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.swiperefresh.SwipeRefreshScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.swipetodismiss.SwipeToDismissScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.switch.SwitchScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.text.TextScreen
import org.imaginativeworld.whynotcompose.ui.screens.composition.textfield.TextFieldScreen
import org.imaginativeworld.whynotcompose.ui.screens.home.index.HomeIndexScreen
import org.imaginativeworld.whynotcompose.ui.screens.home.splash.SplashScreen
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.animatedvisibility.AnimatedVisibilityScreen
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.barcodescanner.index.BarcodeScannerScreen
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.barcodescanner.index.BarcodeScannerViewModel
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.baselineprofiles.BaselineProfilesScreen
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.baselineprofiles.BaselineProfilesViewModel
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.captureimageandcrop.CaptureImageAndCropScreen
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.captureimageandcrop.CaptureImageAndCropViewModel
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.counter.CounterScreen
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.counterwithviewmodel.CounterWithVMScreen
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.counterwithviewmodel.CounterWithVMViewModel
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.datafetchandpaging.DataFetchAndPagingScreen
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.datafetchandpaging.DataFetchAndPagingViewModel
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.deeplinks.DeepLinksScreen
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.index.TutorialIndexScreen
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.lottie.LottieScreen
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.navdatapass.NavDataPassFourScreen
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.navdatapass.NavDataPassHomeScreen
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.navdatapass.NavDataPassOneScreen
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.navdatapass.NavDataPassThreeScreen
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.navdatapass.NavDataPassTwoScreen
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.permission.PermissionScreen
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.reactivemodel.ReactiveModelScreen
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.reactivemodel.ReactiveModelViewModel
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.selectimageandcrop.SelectImageAndCropScreen
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.selectimageandcrop.SelectImageAndCropViewModel
import org.imaginativeworld.whynotcompose.ui.screens.ui.index.UiIndexScreen
import org.imaginativeworld.whynotcompose.ui.screens.ui.mapview.MapScreen
import org.imaginativeworld.whynotcompose.ui.screens.ui.mapview.MapViewDetailsScreen
import org.imaginativeworld.whynotcompose.ui.screens.ui.mapview.MapViewViewModel
import org.imaginativeworld.whynotcompose.ui.screens.ui.otpcodeverify.OtpCodeVerifyScreen
import org.imaginativeworld.whynotcompose.ui.screens.ui.otpcodeverify.OtpCodeVerifyViewModel
import org.imaginativeworld.whynotcompose.ui.screens.ui.pager.UiPagerScreen
import org.imaginativeworld.whynotcompose.ui.screens.ui.pager.UiPagerViewModel
import org.imaginativeworld.whynotcompose.ui.screens.ui.webview.WebViewScreen
import org.imaginativeworld.whynotcompose.ui.screens.ui.webview.WebViewTarget
import org.imaginativeworld.whynotcompose.ui.screens.ui.webview.WebViewViewModel

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Animations : Screen("animation")
    data object Compositions : Screen("composition")
    data object UIs : Screen("ui")
    data object Tutorials : Screen("tutorial")
}

sealed class HomeScreen(val route: String) {
    data object Splash : HomeScreen("splash")
    data object HomeIndex : HomeScreen("home/index")
}

sealed class AnimationsScreen(val route: String) {
    data object AnimationIndex : AnimationsScreen("animation/index")
    data object AnimationComposeOne : AnimationsScreen("animation/composeone")
    data object AnimationEmudi : AnimationsScreen("animation/emudi")
    data object AnimationRunningCar : AnimationsScreen("animation/runningcar")
    data object AnimationTheStory : AnimationsScreen("animation/thestory")
}

sealed class CompositionsScreen(val route: String) {
    data object CompositionIndex : CompositionsScreen("composition/index")

    data object CompositionAppBar : CompositionsScreen("composition/appbar")
    data object CompositionButton : CompositionsScreen("composition/button")
    data object CompositionCard : CompositionsScreen("composition/card")
    data object CompositionCheckBox : CompositionsScreen("composition/checkbox")
    data object CompositionDialog : CompositionsScreen("composition/dialog")
    data object CompositionDropDownMenu : CompositionsScreen("composition/dropdownmenu")

    data object CompositionListIndex : CompositionsScreen("composition/list")
    data object CompositionListColumn : CompositionsScreen("composition/list/column")
    data object CompositionListRow : CompositionsScreen("composition/list/row")

    data object CompositionListLazyColumnIndex : CompositionsScreen("composition/list/lazycolumn")
    data object CompositionListLazyColumnOne : CompositionsScreen("composition/list/lazycolumn/1")
    data object CompositionListLazyColumnTwo : CompositionsScreen("composition/list/lazycolumn/2")

    data object CompositionListLazyRow : CompositionsScreen("composition/list/lazyrow")
    data object CompositionListGridVertical : CompositionsScreen("composition/list/grid/vertical")

    data object CompositionListItem : CompositionsScreen("composition/listitem")

    data object CompositionLoadingIndicator : CompositionsScreen("composition/loadingindicator")
    data object CompositionRadioButton : CompositionsScreen("composition/radiobutton")

    data object CompositionScaffoldIndex : CompositionsScreen("composition/scaffold")
    data object CompositionScaffoldOne : CompositionsScreen("composition/scaffold/1")
    data object CompositionScaffoldTwo : CompositionsScreen("composition/scaffold/2")
    data object CompositionScaffoldThree : CompositionsScreen("composition/scaffold/3")
    data object CompositionScaffoldFour : CompositionsScreen("composition/scaffold/4")
    data object CompositionScaffoldFive : CompositionsScreen("composition/scaffold/5")
    data object CompositionScaffoldSix : CompositionsScreen("composition/scaffold/6")

    data object CompositionSnackbar : CompositionsScreen("composition/snackbar")
    data object CompositionSwitch : CompositionsScreen("composition/switch")
    data object CompositionTextField : CompositionsScreen("composition/textfield")
    data object CompositionSwipeToDismiss : CompositionsScreen("composition/swipetodismiss")
    data object CompositionSwipeRefresh : CompositionsScreen("composition/swiperefresh")
    data object CompositionBadge : CompositionsScreen("composition/badge")
    data object CompositionFloatingActionButton : CompositionsScreen("composition/fab")
    data object CompositionSlider : CompositionsScreen("composition/slider")
    data object CompositionText : CompositionsScreen("composition/text")
    data object CompositionBottomNavigation : CompositionsScreen("composition/bottomnavigation")
}

sealed class UIsScreen(val route: String) {
    data object UiIndex : UIsScreen("ui/index")

    data object UiWebView : UIsScreen("ui/webview")
    data object UiMapView : UIsScreen("ui/mapview")
    data object UiMapViewDetails : UIsScreen("ui/mapview/details?item={item}") {
        const val PARAM_ITEM = "item"
        fun createRoute(item: MapPlace) =
            route.replace("{$PARAM_ITEM}", item.getJsonFromObj() ?: "")
    }

    data object UiOtpCodeVerify : UIsScreen("ui/otpcodeverify")
    data object UiPager : UIsScreen("ui/slider")
}

sealed class TutorialsScreen(val route: String) {
    data object TutorialIndex : TutorialsScreen("tutorial/index")

    data object TutorialCounter : TutorialsScreen("tutorial/counter")
    data object TutorialCounterWithViewModel : TutorialsScreen("tutorial/counter-with-view-model")
    data object TutorialAnimatedVisibility : TutorialsScreen("tutorial/animated-visibility")
    data object TutorialLottie : TutorialsScreen("tutorial/lottie")
    data object TutorialSelectImageAndCrop : TutorialsScreen("tutorial/select-image-and-crop")
    data object TutorialCaptureImageAndCrop : TutorialsScreen("tutorial/capture-image-and-crop")
    data object TutorialPermission : TutorialsScreen("tutorial/permission")
    data object TutorialDataFetchAndPaging : TutorialsScreen("tutorial/data-fetch-and-paging")
    data object TutorialTicTacToe : TutorialsScreen("tutorial/tic-tac-toe")
    data object TutorialExoPlayer : TutorialsScreen("tutorial/exoplayer")
    data object TutorialCMS : TutorialsScreen("tutorial/cms")
    data object TutorialDeepLink : TutorialsScreen("tutorial/deep-link")

    // ================================================================
    // Navigation pass-receive example
    // ================================================================

    data object TutorialNavDataPassHome : TutorialsScreen("tutorial/nav-data-pass/home") {
        const val RESULT_KEY_DATA = "received_data"
    }

    data object TutorialNavDataPassScreen1 :
        TutorialsScreen("tutorial/nav-data-pass/one/{data}") {
        const val PARAM_DATA = "data"
        fun createRoute(item: DemoData) =
            route.replace("{$PARAM_DATA}", item.getJsonFromObj() ?: "")
    }

    data object TutorialNavDataPassScreen2 : TutorialsScreen("tutorial/nav-data-pass/two") {
        const val PARAM_DATA = "data"
    }

    data object TutorialNavDataPassScreen3 :
        TutorialsScreen("tutorial/nav-data-pass/three/{id}/{name}/details") {
        const val PARAM_ID = "id"
        const val PARAM_NAME = "name"

        fun createRoute(id: Int, name: String) =
            route
                .replace("{$PARAM_ID}", "$id")
                .replace("{$PARAM_NAME}", name)
    }

    data object TutorialNavDataPassScreen4 :
        TutorialsScreen("tutorial/nav-data-pass/four/details/{id}") {
        const val PARAM_ID = "id"

        const val RESULT_DATA = "data"
        const val ARG_NAME = "name"
        const val ARG_RANKS = "ranks"

        fun createRoute(id: Int) =
            route
                .replace("{$PARAM_ID}", "$id")
    }

    data object TutorialNavDataPassScreen5 :
        TutorialsScreen("tutorial/nav-data-pass/five/details")

    // ================================================================
    // Reactive model
    // ================================================================

    data object TutorialReactiveModel : TutorialsScreen("tutorial/reactive-model")

    // ================================================================

    data object TutorialPopBackStack : TutorialsScreen("tutorial/pop-back-stack")

    data object TutorialBaselineProfiles : TutorialsScreen("tutorial/baseline-profiles")

    data object TutorialBarcodeScanner : TutorialsScreen("tutorial/barcode-scanner")
}

// ================================================================
// NavHost
// ================================================================

@Composable
fun NavHostMain(
    navController: NavHostController,
    updateUiThemeMode: (UIThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        addHomeScreens(
            navController = navController,
            updateUiThemeMode = updateUiThemeMode
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
        addTutorialScreens(
            navController = navController,
            updateUiThemeMode = updateUiThemeMode
        )
    }
}

// ================================================================
// Top Level Screens
// ================================================================

private fun NavGraphBuilder.addHomeScreens(
    navController: NavHostController,
    updateUiThemeMode: (UIThemeMode) -> Unit
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
            updateUiThemeMode = updateUiThemeMode
        )
    }
}

private fun NavGraphBuilder.addAnimationScreens(
    navController: NavHostController
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
        composable(AnimationsScreen.AnimationComposeOne.route) {
            ComposeOneScreen()
        }

        composable(AnimationsScreen.AnimationEmudi.route) {
            EmudiScreen()
        }

        composable(AnimationsScreen.AnimationRunningCar.route) {
            RunningCarScreen()
        }

        composable(AnimationsScreen.AnimationTheStory.route) {
            TheStoryScreen()
        }
    }
}

private fun NavGraphBuilder.addCompositionScreens(
    navController: NavHostController
) {
    navigation(
        route = Screen.Compositions.route,
        startDestination = CompositionsScreen.CompositionIndex.route
    ) {
        addCompositionIndexScreen(
            navController = navController
        )

        // Below compositions will be just few lines.
        // So, we will not use functions for those.
        composable(CompositionsScreen.CompositionAppBar.route) {
            AppBarScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }

        composable(CompositionsScreen.CompositionButton.route) {
            ButtonScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }

        composable(CompositionsScreen.CompositionCard.route) {
            CardScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }

        composable(CompositionsScreen.CompositionCheckBox.route) {
            CheckBoxScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }

        composable(CompositionsScreen.CompositionDialog.route) {
            DialogScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }

        composable(CompositionsScreen.CompositionDropDownMenu.route) {
            DropDownMenuScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }

        composable(CompositionsScreen.CompositionListIndex.route) {
            ListIndexScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                },
                navigate = { screen ->
                    navController.navigate(screen.route)
                }
            )
        }

        composable(CompositionsScreen.CompositionListColumn.route) {
            ListColumnScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }

        composable(CompositionsScreen.CompositionListRow.route) {
            ListRowScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }

        composable(CompositionsScreen.CompositionListLazyColumnIndex.route) {
            LazyColumnIndexScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                },
                navigate = { screen ->
                    navController.navigate(screen.route)
                }
            )
        }

        composable(CompositionsScreen.CompositionListLazyColumnOne.route) {
            LazyColumnSampleOneScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }

        composable(CompositionsScreen.CompositionListLazyColumnTwo.route) {
            LazyColumnSampleTwoScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }

        composable(CompositionsScreen.CompositionListLazyRow.route) {
            LazyRowScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }

        composable(CompositionsScreen.CompositionListGridVertical.route) {
            LazyVerticalGridScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }

        composable(CompositionsScreen.CompositionListItem.route) {
            ListItemScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }

        composable(CompositionsScreen.CompositionLoadingIndicator.route) {
            LoadingIndicatorScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }

        composable(CompositionsScreen.CompositionRadioButton.route) {
            RadioButtonScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }

        composable(CompositionsScreen.CompositionScaffoldIndex.route) {
            ScaffoldIndexScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                },
                navigate = { screen ->
                    navController.navigate(screen.route)
                }
            )
        }

        composable(CompositionsScreen.CompositionScaffoldOne.route) {
            SimpleScaffoldWithTopBarScreen()
        }

        composable(CompositionsScreen.CompositionScaffoldTwo.route) {
            ScaffoldWithMultilineSnackbarScreen()
        }

        composable(CompositionsScreen.CompositionScaffoldThree.route) {
            ScaffoldWithSimpleSnackbarScreen()
        }

        composable(CompositionsScreen.CompositionScaffoldFour.route) {
            ScaffoldWithCustomSnackbarScreen()
        }

        composable(CompositionsScreen.CompositionScaffoldFive.route) {
            ScaffoldWithCoroutinesSnackbarScreen()
        }

        composable(CompositionsScreen.CompositionScaffoldSix.route) {
            ScaffoldWithIndefiniteSnackbarScreen()
        }

        composable(CompositionsScreen.CompositionSnackbar.route) {
            SnackbarScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                },
                navigate = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable(CompositionsScreen.CompositionSwitch.route) {
            SwitchScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }

        composable(CompositionsScreen.CompositionTextField.route) {
            TextFieldScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }

        composable(CompositionsScreen.CompositionSwipeToDismiss.route) {
            SwipeToDismissScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }

        composable(CompositionsScreen.CompositionSwipeRefresh.route) {
            SwipeRefreshScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }

        composable(CompositionsScreen.CompositionBadge.route) {
            BadgeScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }

        composable(CompositionsScreen.CompositionFloatingActionButton.route) {
            FloatingActionButtonScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }

        composable(CompositionsScreen.CompositionSlider.route) {
            SliderScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }

        composable(CompositionsScreen.CompositionText.route) {
            TextScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }

        composable(CompositionsScreen.CompositionBottomNavigation.route) {
            BottomNavigationScreen(
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }
    }
}

private fun NavGraphBuilder.addUiScreens(
    navController: NavHostController
) {
    navigation(
        route = Screen.UIs.route,
        startDestination = UIsScreen.UiIndex.route
    ) {
        addUiIndexScreen(
            navController = navController
        )

        // Below compositions will be just few lines.
        // So, we will not use functions for those.
        composable(UIsScreen.UiWebView.route) {
            val viewModel: WebViewViewModel = hiltViewModel()

            WebViewScreen(
                viewModel = viewModel,
                target = WebViewTarget.AboutMe,
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }

        composable(UIsScreen.UiMapView.route) {
            val viewModel: MapViewViewModel = hiltViewModel()

            MapScreen(
                viewModel = viewModel,
                goBack = {
                    navController.popBackStackOrIgnore()
                },
                gotoDetailsScreen = { item ->
                    navController.navigate(UIsScreen.UiMapViewDetails.createRoute(item))
                }
            )
        }

        composable(
            UIsScreen.UiMapViewDetails.route,
            arguments = listOf(
                navArgument(UIsScreen.UiMapViewDetails.PARAM_ITEM) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.let { args ->
                val item = args.getString(UIsScreen.UiMapViewDetails.PARAM_ITEM)
                    .getObjFromJson<MapPlace>() ?: throw Exception("Item cannot be null!")

                MapViewDetailsScreen(
                    item = item,
                    goBack = {
                        navController.popBackStackOrIgnore()
                    }
                )
            }
        }

        composable(UIsScreen.UiOtpCodeVerify.route) {
            val viewModel: OtpCodeVerifyViewModel = hiltViewModel()

            OtpCodeVerifyScreen(
                viewModel = viewModel,
                phoneNumber = "+8801234567891",
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }

        composable(UIsScreen.UiPager.route) {
            val viewModel: UiPagerViewModel = hiltViewModel()

            UiPagerScreen(
                viewModel = viewModel,
                goBack = {
                    navController.popBackStackOrIgnore()
                }
            )
        }
    }
}

private fun NavGraphBuilder.addTutorialScreens(
    navController: NavHostController,
    updateUiThemeMode: (UIThemeMode) -> Unit
) {
    navigation(
        route = Screen.Tutorials.route,
        startDestination = TutorialsScreen.TutorialIndex.route
    ) {
        addTutorialIndexScreen(
            navController = navController,
            updateUiThemeMode = updateUiThemeMode
        )

        // Below compositions will be just few lines.
        // So, we will not use functions for those.
//        composable(TutorialsScreen.xyz.route) {
//            BlankScreen()
//        }
    }
}

// ================================================================
// Leaf Screens
// ================================================================

private fun NavGraphBuilder.addHomeSplashScreen(
    navController: NavHostController
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
    updateUiThemeMode: (UIThemeMode) -> Unit
) {
    composable(HomeScreen.HomeIndex.route) {
        HomeIndexScreen(
            navigate = { screen ->
                navController.navigate(screen.route)
            },
            updateUiThemeMode = updateUiThemeMode
        )
    }
}

// ----------------------------------------------------------------

private fun NavGraphBuilder.addAnimationIndexScreen(
    navController: NavHostController
) {
    composable(AnimationsScreen.AnimationIndex.route) {
        AnimationIndexScreen(
            goBack = {
                navController.popBackStackOrIgnore()
            },
            navigate = { screen ->
                navController.navigate(screen.route)
            }
        )
    }
}

// ----------------------------------------------------------------

private fun NavGraphBuilder.addCompositionIndexScreen(
    navController: NavHostController
) {
    composable(CompositionsScreen.CompositionIndex.route) {
        CompositionIndexScreen(
            goBack = {
                navController.popBackStackOrIgnore()
            },
            navigate = { screen ->
                navController.navigate(screen.route)
            }
        )
    }
}

// ----------------------------------------------------------------

private fun NavGraphBuilder.addUiIndexScreen(
    navController: NavHostController
) {
    composable(UIsScreen.UiIndex.route) {
        UiIndexScreen(
            goBack = {
                navController.popBackStackOrIgnore()
            },
            navigate = { screen ->
                navController.navigate(screen.route)
            }
        )
    }
}

// ----------------------------------------------------------------

private fun NavGraphBuilder.addTutorialIndexScreen(
    navController: NavHostController,
    updateUiThemeMode: (UIThemeMode) -> Unit
) {
    composable(TutorialsScreen.TutorialIndex.route) {
        val context = LocalContext.current

        TutorialIndexScreen(
            goBack = {
                navController.popBackStackOrIgnore()
            },
            navigate = { screen ->
                if (screen.route == TutorialsScreen.TutorialPopBackStack.route) {
                    navController.navigate(screen.route)

                    (context as Activity).finish()
                } else {
                    navController.navigate(screen.route)
                }
            }
        )
    }

    composable(TutorialsScreen.TutorialCounter.route) {
        CounterScreen(
            goBack = {
                navController.popBackStackOrIgnore()
            }
        )
    }

    composable(TutorialsScreen.TutorialCounterWithViewModel.route) {
        val viewModel: CounterWithVMViewModel = hiltViewModel()

        CounterWithVMScreen(
            viewModel = viewModel,
            goBack = {
                navController.popBackStackOrIgnore()
            }
        )
    }

    composable(TutorialsScreen.TutorialAnimatedVisibility.route) {
        AnimatedVisibilityScreen(
            goBack = {
                navController.popBackStackOrIgnore()
            }
        )
    }

    composable(TutorialsScreen.TutorialLottie.route) {
        LottieScreen(
            goBack = {
                navController.popBackStackOrIgnore()
            }
        )
    }

    composable(TutorialsScreen.TutorialSelectImageAndCrop.route) {
        val viewModel: SelectImageAndCropViewModel = hiltViewModel()

        SelectImageAndCropScreen(
            viewModel = viewModel,
            goBack = {
                navController.popBackStackOrIgnore()
            }
        )
    }

    composable(TutorialsScreen.TutorialCaptureImageAndCrop.route) {
        val viewModel: CaptureImageAndCropViewModel = hiltViewModel()

        CaptureImageAndCropScreen(
            viewModel = viewModel,
            goBack = {
                navController.popBackStackOrIgnore()
            }
        )
    }

    composable(TutorialsScreen.TutorialPermission.route) {
        PermissionScreen(
            goBack = {
                navController.popBackStackOrIgnore()
            }
        )
    }

    composable(TutorialsScreen.TutorialDataFetchAndPaging.route) {
        val viewModel: DataFetchAndPagingViewModel = hiltViewModel()

        DataFetchAndPagingScreen(
            viewModel = viewModel,
            goBack = {
                navController.popBackStackOrIgnore()
            }
        )
    }

    composable(TutorialsScreen.TutorialTicTacToe.route) {
        val viewModel: TicTacToeViewModel = hiltViewModel()

        TicTacToeScreen(
            viewModel = viewModel,
            goBack = {
                navController.popBackStackOrIgnore()
            }
        )
    }

    composable(TutorialsScreen.TutorialExoPlayer.route) {
        ExoPlayerScreen(
            goBack = {
                navController.popBackStackOrIgnore()
            }
        )
    }

    composable(TutorialsScreen.TutorialCMS.route) {
        CMSMainScreen(
            updateUiThemeMode = updateUiThemeMode,
            goBack = {
                navController.popBackStackOrIgnore()
            }
        )
    }

    composable(TutorialsScreen.TutorialDeepLink.route) {
        DeepLinksScreen(
            goBack = {
                navController.popBackStackOrIgnore()
            }
        )
    }

    // ================================================================
    // Navigation pass-receive example
    // ================================================================

    composable(TutorialsScreen.TutorialNavDataPassHome.route) { backStackEntry ->
        // Observe for receive data using `SavedStateHandle`.
        // Pros: Using official `SavedStateHandle`.
        // Cons: The data need to be remove manually after use.
        val resultDataBySavedState = backStackEntry.savedStateHandle
            .getLiveData<DemoData>(TutorialsScreen.TutorialNavDataPassHome.RESULT_KEY_DATA)
            .observeAsState()

        // Receive result using Memory Cache.
        // Pros: It will be reset automatically.
        // Cons: It need Memory Cache.
        val resultDataByMemoryCache: DemoData? = navResult(
            TutorialsScreen.TutorialNavDataPassScreen4.RESULT_DATA
        )

        NavDataPassHomeScreen(
            receivedDataBySavedState = resultDataBySavedState.value,
            receivedDataByMemoryCache = resultDataByMemoryCache,
            goBack = {
                navController.popBackStackOrIgnore()
            },
            gotoScreenOne = { item ->
                navController.navigate(TutorialsScreen.TutorialNavDataPassScreen1.createRoute(item))
            },
            gotoScreenTwo = { item ->
                // Navigate with passing data using `savedStateHandle`.
                // Equivalent: navController.currentBackStackEntry?.savedStateHandle
                backStackEntry.savedStateHandle.apply {
                    set(
                        TutorialsScreen.TutorialNavDataPassScreen2.PARAM_DATA,
                        item
                    )
                }
                navController.navigate(TutorialsScreen.TutorialNavDataPassScreen2.route)
            },
            gotoScreenThree = { id, name ->
                // Navigate with passing data using default `arguments` system in `NavController`. (Recommended)
                navController.navigate(
                    TutorialsScreen.TutorialNavDataPassScreen3.createRoute(
                        id = id,
                        name = name
                    )
                )
            },
            gotoScreenFour = { id, name ->
                // Navigate with passing data using custom Memory Cache.
                navController.navigate(
                    TutorialsScreen.TutorialNavDataPassScreen4.createRoute(id = id),
                    args = mapOf(
                        TutorialsScreen.TutorialNavDataPassScreen4.ARG_NAME to name,
                        TutorialsScreen.TutorialNavDataPassScreen4.ARG_RANKS to listOf(
                            "A",
                            "B",
                            "C"
                        )
                    )
                )
            }
        )
    }

    composable(
        TutorialsScreen.TutorialNavDataPassScreen1.route,
        arguments = listOf(
            navArgument(TutorialsScreen.TutorialNavDataPassScreen1.PARAM_DATA) {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        // Receive parameters using default `NavController` route system.
        val receivedData = backStackEntry.arguments
            ?.getString(TutorialsScreen.TutorialNavDataPassScreen1.PARAM_DATA)
            .getObjFromJson<DemoData>() ?: throw Exception("Data cannot be null!")

        NavDataPassOneScreen(
            data = receivedData,
            goBack = {
                navController.popBackStackOrIgnore()
            },
            backWithData = { data ->
                // Send-back data using `SavedStateHandle`.
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(
                        TutorialsScreen.TutorialNavDataPassHome.RESULT_KEY_DATA,
                        data
                    )

                navController.popBackStackOrIgnore()
            }
        )
    }

    composable(TutorialsScreen.TutorialNavDataPassScreen2.route) {
        // Receive passed data using `savedStateHandle`.
        val data: DemoData? = navController.previousBackStackEntry?.savedStateHandle?.get<DemoData>(
            TutorialsScreen.TutorialNavDataPassScreen2.PARAM_DATA
        )

        NavDataPassTwoScreen(
            data = data,
            goBack = {
                navController.popBackStackOrIgnore()
            }
        )
    }

    composable(
        TutorialsScreen.TutorialNavDataPassScreen3.route,
        arguments = listOf(
            navArgument(TutorialsScreen.TutorialNavDataPassScreen3.PARAM_ID) {
                type = NavType.IntType
            },
            navArgument(TutorialsScreen.TutorialNavDataPassScreen3.PARAM_NAME) {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        var id = 0
        var name = ""

        // Receive data using default `arguments` system in `NavController`. (Recommended)
        backStackEntry.arguments?.apply {
            id = getInt(TutorialsScreen.TutorialNavDataPassScreen3.PARAM_ID)
            name = getString(TutorialsScreen.TutorialNavDataPassScreen3.PARAM_NAME, "")
        }

        NavDataPassThreeScreen(
            id = id,
            name = name,
            goBack = {
                navController.popBackStackOrIgnore()
            }
        )
    }

    composable(
        TutorialsScreen.TutorialNavDataPassScreen4.route,
        arguments = listOf(
            navArgument(TutorialsScreen.TutorialNavDataPassScreen3.PARAM_ID) {
                type = NavType.IntType
            }
        )
    ) { backStackEntry ->
        val id = backStackEntry.arguments
            ?.getInt(TutorialsScreen.TutorialNavDataPassScreen4.PARAM_ID)
            ?: throw Exception("Id cannot be null!")

        // Receive data using custom Memory Cache.
        val name = navArg<String>(
            TutorialsScreen.TutorialNavDataPassScreen4.ARG_NAME
        ).value ?: throw Exception("Name cannot be null!")
        val ranks = navArg<List<String>>(
            TutorialsScreen.TutorialNavDataPassScreen4.ARG_RANKS
        ).value ?: throw Exception("Ranks cannot be null!")

        NavDataPassFourScreen(
            id = id,
            name = name,
            ranks = ranks,
            goBack = {
                navController.popBackStackOrIgnore()
            },
            backWithData = { data ->
                // Send-back data using Memory Cache.
                navController.popBackStackWithResult(
                    TutorialsScreen.TutorialNavDataPassScreen4.RESULT_DATA to data
                )
            },
            goAnotherScreen = {
                navController.navigate(
                    TutorialsScreen.TutorialNavDataPassScreen5.route
                )
            }
        )
    }

    composable(TutorialsScreen.TutorialNavDataPassScreen5.route) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Hello World!")

            Spacer(Modifier.height(16.dp))

            Button(onClick = {
                navController.popBackStackOrIgnore()
            }) {
                Text("Go Back")
            }
        }
    }

    // ================================================================
    // Reactive model
    // ================================================================

    composable(TutorialsScreen.TutorialReactiveModel.route) {
        val viewModel: ReactiveModelViewModel = hiltViewModel()

        ReactiveModelScreen(
            viewModel = viewModel,
            goBack = {
                navController.popBackStackOrIgnore()
            }
        )
    }

    // ================================================================

    activity(TutorialsScreen.TutorialPopBackStack.route) {
        activityClass = PopBackStackActivity::class
    }

    composable(TutorialsScreen.TutorialBaselineProfiles.route) {
        val viewModel: BaselineProfilesViewModel = hiltViewModel()

        BaselineProfilesScreen(
            viewModel = viewModel,
            goBack = {
                navController.popBackStackOrIgnore()
            }
        )
    }

    composable(TutorialsScreen.TutorialBarcodeScanner.route) {
        val viewModel: BarcodeScannerViewModel = hiltViewModel()

        BarcodeScannerScreen(
            viewModel = viewModel,
            goBack = {
                navController.popBackStackOrIgnore()
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
