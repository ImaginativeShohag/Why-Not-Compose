/*
 * Copyright 2023 Md. Mahmudul Hasan Shohag
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

package org.imaginativeworld.whynotcompose.cms.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import org.imaginativeworld.whynotcompose.base.utils.UIThemeController
import org.imaginativeworld.whynotcompose.cms.ui.splash.SplashScreen
import org.imaginativeworld.whynotcompose.cms.ui.user.details.UserDetailsScreen
import org.imaginativeworld.whynotcompose.cms.ui.user.details.UserDetailsViewModel
import org.imaginativeworld.whynotcompose.cms.ui.user.list.UserListScreen
import org.imaginativeworld.whynotcompose.cms.ui.user.list.UserListViewModel

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object User : Screen("user")
    object Post : Screen("post")
    object Todo : Screen("todo")
    object Comment : Screen("comment")
}

sealed class SplashScreen(val route: String) {
    object Splash : SplashScreen("splash/index")
}

sealed class UserScreen(val route: String) {
    object UserList : UserScreen("users")
    object UserDetails : UserScreen("users/{userId}") {
        const val USER_ID = "userId"
    }
}

sealed class PostScreen(val route: String) {
    object PostList : PostScreen("users/{userId}/posts")
    object PostDetails : PostScreen("posts/{postId}")
}

sealed class TodoScreen(val route: String) {
    object TodoList : TodoScreen("users/{userId}/todos")
    object TodoDetails : TodoScreen("todos/{todoId}")
}

sealed class CommentScreen(val route: String) {
    object CommentList : CommentScreen("posts/{postId}/comments")
    object CommentDetails : CommentScreen("comments/{commentId}")
}

// ================================================================
// NavHost
// ================================================================

@Composable
fun CMSNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    turnOnDarkMode: (Boolean) -> Unit,
    goBack: () -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        addSplashScreens(navController = navController)
        addUserScreens(
            navController = navController,
            turnOnDarkMode = turnOnDarkMode,
            goBack = goBack
        )
    }
}

// ================================================================
// Top Level Screens
// ================================================================

private fun NavGraphBuilder.addSplashScreens(
    navController: NavHostController
) {
    navigation(
        route = Screen.Splash.route,
        startDestination = SplashScreen.Splash.route
    ) {
        composable(SplashScreen.Splash.route) {
            SplashScreen(
                gotoHomeIndex = {
                    navController.navigate(Screen.User.route) {
                        popUpTo(SplashScreen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

private fun NavGraphBuilder.addUserScreens(
    navController: NavHostController,
    turnOnDarkMode: (Boolean) -> Unit,
    goBack: () -> Unit
) {
    navigation(
        route = Screen.User.route,
        startDestination = UserScreen.UserList.route
    ) {
        addPostScreens(navController = navController)
        addTodoScreens(navController = navController)

        composable(UserScreen.UserList.route) {
            val viewModel: UserListViewModel = hiltViewModel()

            val isDarkMode by UIThemeController.isDarkMode.collectAsState()

            UserListScreen(
                viewModel = viewModel,
                goBack = {
                    // We are using the parent `NavController` to go back from this `NavHost`.
                    goBack()
                },
                toggleUIMode = {
                    turnOnDarkMode(!isDarkMode)
                },
                goToUserDetails = { userId ->
                    navController.navigate(
                        UserScreen.UserDetails.route.replaceFirst(
                            "{${UserScreen.UserDetails.USER_ID}}",
                            "$userId"
                        )
                    )
                }
            )
        }

        composable(
            UserScreen.UserDetails.route,
            arguments = listOf(
                navArgument(UserScreen.UserDetails.USER_ID) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val viewModel: UserDetailsViewModel = hiltViewModel()

            val isDarkMode by UIThemeController.isDarkMode.collectAsState()

            UserDetailsScreen(
                viewModel = viewModel,
                userId = backStackEntry.arguments?.getInt(UserScreen.UserDetails.USER_ID) ?: 0,
                goBack = {
                    navController.popBackStack()
                },
                toggleUIMode = {
                    turnOnDarkMode(!isDarkMode)
                },
                onTodosClicked = {
                    navController.navigate(Screen.Todo.route)
                },
                onPostsClicked = {
                    navController.navigate(Screen.Post.route)
                }
            )
        }
    }
}

private fun NavGraphBuilder.addPostScreens(
    navController: NavHostController
) {
    navigation(
        route = Screen.Post.route,
        startDestination = PostScreen.PostList.route
    ) {
        addCommentScreens(navController = navController)

        composable(PostScreen.PostList.route) {
            BlankScreen()
        }

        composable(PostScreen.PostDetails.route) {
            BlankScreen()
        }
    }
}

private fun NavGraphBuilder.addTodoScreens(
    navController: NavHostController
) {
    navigation(
        route = Screen.Todo.route,
        startDestination = TodoScreen.TodoList.route
    ) {
        composable(TodoScreen.TodoList.route) {
            BlankScreen()
        }

        composable(TodoScreen.TodoDetails.route) {
            BlankScreen()
        }
    }
}

private fun NavGraphBuilder.addCommentScreens(
    navController: NavHostController
) {
    navigation(
        route = Screen.Comment.route,
        startDestination = CommentScreen.CommentList.route
    ) {
        composable(CommentScreen.CommentList.route) {
            BlankScreen()
        }

        composable(CommentScreen.CommentDetails.route) {
            BlankScreen()
        }
    }
}

// ================================================================
// Leaf Screens
// ================================================================

// ================================================================
// Blank Screen
// ================================================================

@Composable
private fun BlankScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Coming soon...")
    }
}
