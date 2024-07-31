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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.imaginativeworld.whynotcompose.base.extensions.popBackStackOrIgnore
import org.imaginativeworld.whynotcompose.base.models.UIThemeMode
import org.imaginativeworld.whynotcompose.base.models.nextMode
import org.imaginativeworld.whynotcompose.base.utils.UIThemeController
import org.imaginativeworld.whynotcompose.cms.ui.screens.comment.details.CommentDetailsScreen
import org.imaginativeworld.whynotcompose.cms.ui.screens.comment.details.CommentDetailsViewModel
import org.imaginativeworld.whynotcompose.cms.ui.screens.comment.list.CommentListScreen
import org.imaginativeworld.whynotcompose.cms.ui.screens.comment.list.CommentListViewModel
import org.imaginativeworld.whynotcompose.cms.ui.screens.post.details.PostDetailsScreen
import org.imaginativeworld.whynotcompose.cms.ui.screens.post.details.PostDetailsViewModel
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

sealed class SplashScreen {
    @Serializable
    object Splash
}

sealed class UserScreen {
    @Serializable
    object UserList

    @Serializable
    data class UserDetails(
        val userId: Int
    )
}

sealed class TodoScreen {
    @Serializable
    data class TodoList(
        val userId: Int
    )

    @Serializable
    data class TodoDetails(
        val userId: Int,
        val todoId: Int
    )
}

sealed class PostScreen {
    @Serializable
    data class PostList(
        val userId: Int
    )

    @Serializable
    data class PostDetails(
        val userId: Int,
        val postId: Int
    )
}

sealed class CommentScreen {
    @Serializable
    data class CommentList(
        val postId: Int
    )

    @Serializable
    data class CommentDetails(
        val postId: Int,
        val commentId: Int
    )
}

// ================================================================
// NavHost
// ================================================================

@Composable
fun CMSNavHost(
    navController: NavHostController,
    updateUiThemeMode: (UIThemeMode) -> Unit,
    goBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = SplashScreen.Splash
    ) {
        composable<SplashScreen.Splash> {
            SplashScreen(
                gotoHomeIndex = {
                    navController.navigate(UserScreen.UserList) {
                        popUpTo(SplashScreen.Splash) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        addUserScreens(
            navController = navController,
            updateUiThemeMode = updateUiThemeMode,
            goBack = goBack
        )
    }
}

// ================================================================
// Top Level Screens
// ================================================================

private fun NavGraphBuilder.addUserScreens(
    navController: NavHostController,
    updateUiThemeMode: (UIThemeMode) -> Unit,
    goBack: () -> Unit
) {
    composable<UserScreen.UserList> {
        val viewModel: UserListViewModel = hiltViewModel()
        val isDarkMode by UIThemeController.uiThemeMode.collectAsState()

        UserListScreen(
            viewModel = viewModel,
            goBack = {
                // We are using the parent `NavController` to go back from this `NavHost`.
                goBack()
            },
            toggleUIMode = {
                updateUiThemeMode(isDarkMode.nextMode())
            },
            goToUserDetails = { userId ->
                navController.navigate(
                    UserScreen.UserDetails(
                        userId = userId
                    )
                )
            }
        )
    }

    composable<UserScreen.UserDetails> { backStackEntry ->
        val viewModel: UserDetailsViewModel = hiltViewModel()
        val isDarkMode by UIThemeController.uiThemeMode.collectAsState()

        val userDetails: UserScreen.UserDetails = backStackEntry.toRoute()
        val userId = userDetails.userId

        UserDetailsScreen(
            viewModel = viewModel,
            userId = userId,
            goBack = {
                navController.popBackStackOrIgnore()
            },
            toggleUIMode = {
                updateUiThemeMode(isDarkMode.nextMode())
            },
            onTodosClick = {
                navController.navigate(
                    TodoScreen.TodoList(
                        userId = userId
                    )
                )
            },
            onPostsClick = {
                navController.navigate(
                    PostScreen.PostList(
                        userId = userId
                    )
                )
            }
        )
    }

    addPostScreens(
        navController = navController,
        updateUiThemeMode = updateUiThemeMode
    )

    addTodoScreens(
        navController = navController,
        updateUiThemeMode = updateUiThemeMode
    )
}

private fun NavGraphBuilder.addTodoScreens(
    navController: NavHostController,
    updateUiThemeMode: (UIThemeMode) -> Unit
) {
    composable<TodoScreen.TodoList> { backStackEntry ->
        val viewModel: TodoListViewModel = hiltViewModel()
        val isDarkMode by UIThemeController.uiThemeMode.collectAsState()

        val userDetails: TodoScreen.TodoList = backStackEntry.toRoute()
        val userId = userDetails.userId

        TodoListScreen(
            viewModel = viewModel,
            userId = userId,
            goBack = {
                navController.popBackStackOrIgnore()
            },
            toggleUIMode = {
                updateUiThemeMode(isDarkMode.nextMode())
            },
            goToTodoDetails = { todoId ->
                navController.navigate(
                    TodoScreen.TodoDetails(
                        userId = userId,
                        todoId = todoId
                    )
                )
            }
        )
    }

    composable<TodoScreen.TodoDetails> { backStackEntry ->
        val viewModel: TodoDetailsViewModel = hiltViewModel()
        val isDarkMode by UIThemeController.uiThemeMode.collectAsState()

        val todoDetails: TodoScreen.TodoDetails = backStackEntry.toRoute()
        val userId = todoDetails.userId
        val todoId = todoDetails.todoId

        TodoDetailsScreen(
            viewModel = viewModel,
            userId = userId,
            todoId = todoId,
            goBack = {
                navController.popBackStackOrIgnore()
            },
            toggleUIMode = {
                updateUiThemeMode(isDarkMode.nextMode())
            }
        )
    }
}

private fun NavGraphBuilder.addPostScreens(
    navController: NavHostController,
    updateUiThemeMode: (UIThemeMode) -> Unit
) {
    composable<PostScreen.PostList> { backStackEntry ->
        val viewModel: PostListViewModel = hiltViewModel()
        val isDarkMode by UIThemeController.uiThemeMode.collectAsState()

        val postList: PostScreen.PostList = backStackEntry.toRoute()
        val userId = postList.userId

        PostListScreen(
            viewModel = viewModel,
            userId = userId,
            goBack = {
                navController.popBackStackOrIgnore()
            },
            toggleUIMode = {
                updateUiThemeMode(isDarkMode.nextMode())
            },
            goToPostDetails = { postId ->
                navController.navigate(
                    PostScreen.PostDetails(
                        userId = userId,
                        postId = postId
                    )
                )
            }
        )
    }

    composable<PostScreen.PostDetails> { backStackEntry ->
        val viewModel: PostDetailsViewModel = hiltViewModel()
        val isDarkMode by UIThemeController.uiThemeMode.collectAsState()

        val postDetails: PostScreen.PostDetails = backStackEntry.toRoute()
        val userId = postDetails.userId
        val postId = postDetails.postId

        PostDetailsScreen(
            viewModel = viewModel,
            userId = userId,
            postId = postId,
            goBack = {
                navController.popBackStackOrIgnore()
            },
            toggleUIMode = {
                updateUiThemeMode(isDarkMode.nextMode())
            },
            onCommentsClick = {
                navController.navigate(
                    CommentScreen.CommentList(
                        postId = postId
                    )
                )
            }
        )
    }

    addCommentScreens(
        navController = navController,
        updateUiThemeMode = updateUiThemeMode
    )
}

private fun NavGraphBuilder.addCommentScreens(
    navController: NavHostController,
    updateUiThemeMode: (UIThemeMode) -> Unit
) {
    composable<CommentScreen.CommentList> { backStackEntry ->
        val viewModel: CommentListViewModel = hiltViewModel()
        val isDarkMode by UIThemeController.uiThemeMode.collectAsState()

        val commentList: CommentScreen.CommentList = backStackEntry.toRoute()
        val postId = commentList.postId

        CommentListScreen(
            viewModel = viewModel,
            postId = postId,
            goBack = {
                navController.popBackStackOrIgnore()
            },
            toggleUIMode = {
                updateUiThemeMode(isDarkMode.nextMode())
            },
            goToCommentDetails = { commentId ->
                navController.navigate(
                    CommentScreen.CommentDetails(
                        postId = postId,
                        commentId = commentId
                    )
                )
            }
        )
    }

    composable<CommentScreen.CommentDetails> { backStackEntry ->
        val viewModel: CommentDetailsViewModel = hiltViewModel()
        val uiThemeMode by UIThemeController.uiThemeMode.collectAsState()

        val commentDetails: CommentScreen.CommentDetails = backStackEntry.toRoute()
        val postId = commentDetails.postId
        val commentId = commentDetails.commentId

        CommentDetailsScreen(
            viewModel = viewModel,
            postId = postId,
            commentId = commentId,
            goBack = {
                navController.popBackStackOrIgnore()
            },
            toggleUIMode = {
                updateUiThemeMode(uiThemeMode.nextMode())
            }
        )
    }
}
