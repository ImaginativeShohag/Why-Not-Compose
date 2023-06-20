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

package org.imaginativeworld.whynotcompose.cms.ui.screens

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
import org.imaginativeworld.whynotcompose.cms.ui.screens.post.list.PostListScreen
import org.imaginativeworld.whynotcompose.cms.ui.screens.post.list.PostListViewModel
import org.imaginativeworld.whynotcompose.cms.ui.screens.splash.SplashScreen
import org.imaginativeworld.whynotcompose.cms.ui.screens.todo.details.TodoDetailsScreen
import org.imaginativeworld.whynotcompose.cms.ui.screens.todo.details.TodoDetailsViewModel
import org.imaginativeworld.whynotcompose.cms.ui.screens.todo.list.TodoListScreen
import org.imaginativeworld.whynotcompose.cms.ui.screens.todo.list.TodoListViewModel
import org.imaginativeworld.whynotcompose.cms.ui.screens.user.details.UserDetailsScreen
import org.imaginativeworld.whynotcompose.cms.ui.screens.user.details.UserDetailsViewModel
import org.imaginativeworld.whynotcompose.cms.ui.screens.user.list.UserListScreen
import org.imaginativeworld.whynotcompose.cms.ui.screens.user.list.UserListViewModel

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object User : Screen("user")
    object Todo : Screen("todo")
    object Post : Screen("post")
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

sealed class TodoScreen(val route: String) {
    object TodoList : TodoScreen("users/{userId}/todos") {
        const val USER_ID = "userId"
    }

    object TodoDetails : TodoScreen("users/{userId}/todos/{todoId}") {
        const val USER_ID = "userId"
        const val TODO_ID = "todoId"
    }
}

sealed class PostScreen(val route: String) {
    object PostList : PostScreen("users/{userId}/posts") {
        const val USER_ID = "userId"
    }

    object PostDetails : PostScreen("users/{userId}/posts/{postId}") {
        const val USER_ID = "userId"
        const val POST_ID = "postId"
    }
}

sealed class CommentScreen(val route: String) {
    object CommentList : CommentScreen("posts/{postId}/comments") {
        const val POST_ID = "postId"
    }

    object CommentDetails : CommentScreen("posts/{postId}/comments/{commentId}") {
        const val POST_ID = "postId"
        const val COMMNET_ID = "commentId"
    }
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
        addPostScreens(
            navController = navController,
            turnOnDarkMode = turnOnDarkMode
        )
        addTodoScreens(
            navController = navController,
            turnOnDarkMode = turnOnDarkMode
        )

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
            val userId = backStackEntry.arguments?.getInt(UserScreen.UserDetails.USER_ID) ?: 0

            UserDetailsScreen(
                viewModel = viewModel,
                userId = userId,
                goBack = {
                    navController.popBackStack()
                },
                toggleUIMode = {
                    turnOnDarkMode(!isDarkMode)
                },
                onTodosClicked = {
                    navController.navigate(
                        TodoScreen.TodoList.route
                            .replaceFirst(
                                "{${TodoScreen.TodoList.USER_ID}}",
                                "$userId"
                            )
                    )
                },
                onPostsClicked = {
                    navController.navigate(
                        PostScreen.PostList.route
                            .replaceFirst(
                                "{${PostScreen.PostList.USER_ID}}",
                                "$userId"
                            )
                    )
                }
            )
        }
    }
}

private fun NavGraphBuilder.addTodoScreens(
    navController: NavHostController,
    turnOnDarkMode: (Boolean) -> Unit
) {
    navigation(
        route = Screen.Todo.route,
        startDestination = TodoScreen.TodoList.route
    ) {
        composable(
            TodoScreen.TodoList.route,
            arguments = listOf(
                navArgument(TodoScreen.TodoList.USER_ID) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val viewModel: TodoListViewModel = hiltViewModel()
            val isDarkMode by UIThemeController.isDarkMode.collectAsState()
            val userId = backStackEntry.arguments?.getInt(TodoScreen.TodoList.USER_ID) ?: 0

            TodoListScreen(
                viewModel = viewModel,
                userId = userId,
                goBack = {
                    navController.popBackStack()
                },
                toggleUIMode = {
                    turnOnDarkMode(!isDarkMode)
                },
                goToTodoDetails = { todoId ->
                    navController.navigate(
                        TodoScreen.TodoDetails.route
                            .replaceFirst(
                                "{${TodoScreen.TodoDetails.USER_ID}}",
                                "$userId"
                            )
                            .replaceFirst(
                                "{${TodoScreen.TodoDetails.TODO_ID}}",
                                "$todoId"
                            )
                    )
                }
            )
        }

        composable(
            TodoScreen.TodoDetails.route,
            arguments = listOf(
                navArgument(TodoScreen.TodoDetails.USER_ID) { type = NavType.IntType },
                navArgument(TodoScreen.TodoDetails.TODO_ID) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val viewModel: TodoDetailsViewModel = hiltViewModel()
            val isDarkMode by UIThemeController.isDarkMode.collectAsState()
            val userId = backStackEntry.arguments?.getInt(TodoScreen.TodoDetails.USER_ID) ?: 0
            val todoId = backStackEntry.arguments?.getInt(TodoScreen.TodoDetails.TODO_ID) ?: 0

            TodoDetailsScreen(
                viewModel = viewModel,
                userId = userId,
                todoId = todoId,
                goBack = {
                    navController.popBackStack()
                },
                toggleUIMode = {
                    turnOnDarkMode(!isDarkMode)
                }
            )
        }
    }
}

private fun NavGraphBuilder.addPostScreens(
    navController: NavHostController,
    turnOnDarkMode: (Boolean) -> Unit
) {
    navigation(
        route = Screen.Post.route,
        startDestination = PostScreen.PostList.route
    ) {
        addCommentScreens(navController = navController)

        composable(
            PostScreen.PostList.route,
            arguments = listOf(
                navArgument(PostScreen.PostList.USER_ID) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val viewModel: PostListViewModel = hiltViewModel()
            val isDarkMode by UIThemeController.isDarkMode.collectAsState()
            val userId = backStackEntry.arguments?.getInt(PostScreen.PostList.USER_ID) ?: 0

            PostListScreen(
                viewModel = viewModel,
                userId = userId,
                goBack = {
                    navController.popBackStack()
                },
                toggleUIMode = {
                    turnOnDarkMode(!isDarkMode)
                },
                goToPostDetails = { postId ->
                    navController.navigate(
                        PostScreen.PostDetails.route
                            .replaceFirst(
                                "{${PostScreen.PostDetails.USER_ID}}",
                                "$userId"
                            )
                            .replaceFirst(
                                "{${PostScreen.PostDetails.POST_ID}}",
                                "$postId"
                            )
                    )
                }
            )
        }

        composable(
            PostScreen.PostDetails.route,
            arguments = listOf(
                navArgument(PostScreen.PostDetails.USER_ID) { type = NavType.IntType },
                navArgument(PostScreen.PostDetails.POST_ID) { type = NavType.IntType }
            )
        ) {
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
