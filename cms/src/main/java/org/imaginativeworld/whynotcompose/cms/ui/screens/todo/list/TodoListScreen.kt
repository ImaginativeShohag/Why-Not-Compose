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

package org.imaginativeworld.whynotcompose.cms.ui.screens.todo.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.flowOf
import org.imaginativeworld.whynotcompose.base.models.Event
import org.imaginativeworld.whynotcompose.cms.models.todo.Todo
import org.imaginativeworld.whynotcompose.cms.repositories.MockData
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme
import org.imaginativeworld.whynotcompose.cms.ui.compositions.EmptyView
import org.imaginativeworld.whynotcompose.cms.ui.compositions.ErrorItem
import org.imaginativeworld.whynotcompose.cms.ui.compositions.GeneralAppBar
import org.imaginativeworld.whynotcompose.cms.ui.compositions.LoadingContainer
import org.imaginativeworld.whynotcompose.cms.ui.compositions.LoadingItem
import org.imaginativeworld.whynotcompose.cms.ui.screens.todo.add.TodoAddSheet
import org.imaginativeworld.whynotcompose.cms.ui.screens.todo.list.elements.TodoItem

@Composable
fun TodoListScreen(
    viewModel: TodoListViewModel,
    userId: Int,
    goBack: () -> Unit,
    toggleUIMode: () -> Unit,
    goToTodoDetails: (Int) -> Unit
) {
    val openAddTodoSheet = rememberSaveable { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()

    val pagedTodos = state.items.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.loadTodos(userId)
    }

    TodoListScreenSkeleton(
        goBack = goBack,
        toggleUIMode = toggleUIMode,
        showLoading = state.loading,
        showMessage = state.message,
        todos = pagedTodos,
        retryDataLoad = {
            viewModel.loadTodos(userId)
        },
        openAddTodoSheet = {
            openAddTodoSheet.value = !openAddTodoSheet.value
        },
        goToTodoDetails = goToTodoDetails
    )

    // ----------------------------------------------------------------
    // Bottom Sheet
    // ----------------------------------------------------------------

    if (openAddTodoSheet.value) {
        TodoAddSheet(
            userId = userId,
            showSheet = openAddTodoSheet,
            onSuccess = {
                viewModel.loadTodos(userId)
            }
        )
    }
}

@Preview
@Composable
fun TodoListScreenSkeletonPreview() {
    CMSAppTheme {
        TodoListScreenSkeleton(
            goBack = {},
            toggleUIMode = {},
            todos = flowOf(
                PagingData.from(
                    MockData.dummyTodoList
                )
            ).collectAsLazyPagingItems()
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TodoListScreenSkeletonPreviewDark() {
    CMSAppTheme {
        TodoListScreenSkeleton(
            goBack = {},
            toggleUIMode = {},
            todos = flowOf(
                PagingData.from(
                    MockData.dummyTodoList
                )
            ).collectAsLazyPagingItems()
        )
    }
}

@Composable
fun TodoListScreenSkeleton(
    goBack: () -> Unit,
    toggleUIMode: () -> Unit,
    showLoading: Boolean = false,
    showMessage: Event<String>? = null,
    todos: LazyPagingItems<Todo>,
    retryDataLoad: () -> Unit = {},
    openAddTodoSheet: () -> Unit = {},
    goToTodoDetails: (Int) -> Unit = { _ -> }
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val lazyListState = rememberLazyListState()

    val expandedFab by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0
        }
    }

    LaunchedEffect(showMessage) {
        showMessage?.getValueOnce()?.let { message ->
            val result = snackbarHostState.showSnackbar(message)

            if (result == SnackbarResult.ActionPerformed) {
                retryDataLoad()
            }
        }
    }

    Scaffold(
        Modifier
            .imePadding()
            .statusBarsPadding(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    openAddTodoSheet()
                },
                expanded = expandedFab,
                icon = { Icon(Icons.Filled.Add, "Add Todo") },
                text = { Text(text = "Add Todo") }
            )
        },
        topBar = {
            GeneralAppBar(
                subTitle = "Todos",
                goBack = goBack,
                toggleUIMode = toggleUIMode
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Box(
                Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                todos.apply {
                    EmptyView(
                        modifier = Modifier,
                        loadState = loadState,
                        itemCount = this.itemCount
                    )
                }

                LazyColumn(
                    Modifier
                        .fillMaxSize(),
                    state = lazyListState,
                    contentPadding = PaddingValues(top = 4.dp, bottom = 4.dp)
                ) {
                    items(
                        count = todos.itemCount,
                        key = todos.itemKey { it.id }
                    ) { index ->
                        val todo = todos[index]

                        if (todo == null) {
                            Text("Loading...")
                        } else {
                            TodoItem(
                                modifier = Modifier.padding(
                                    start = 12.dp,
                                    top = 4.dp,
                                    end = 12.dp,
                                    bottom = 4.dp
                                ),
                                title = todo.title,
                                dueDate = todo.getDueDate(),
                                status = todo.getStatusLabel(),
                                statusColor = todo.getStatusColor(),
                                onClick = {
                                    goToTodoDetails(todo.id)
                                }
                            )
                        }
                    }

                    todos.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                item {
                                    Column {
                                        for (i in 1..6) {
                                            LoadingItem(
                                                Modifier
                                                    .padding(
                                                        start = 16.dp,
                                                        top = 4.dp,
                                                        end = 16.dp,
                                                        bottom = 4.dp
                                                    )
                                            )
                                        }
                                    }
                                }
                            }

                            loadState.append is LoadState.Loading -> {
                                item {
                                    Column {
                                        LoadingItem(
                                            Modifier
                                                .alpha(1f)
                                                .padding(
                                                    start = 16.dp,
                                                    top = 4.dp,
                                                    end = 16.dp,
                                                    bottom = 4.dp
                                                )
                                        )
                                    }
                                }
                            }

                            loadState.refresh is LoadState.Error -> {
                                item {
                                    // Note: this should be full screen using fillParentMaxSize()
                                    ErrorItem(
                                        errorMessage = "Error! " + (todos.loadState.refresh as LoadState.Error).error.message,
                                        onRetryClicked = {
                                            retry()
                                        }
                                    )
                                }
                            }

                            loadState.append is LoadState.Error -> {
                                item {
                                    ErrorItem(
                                        errorMessage = "Error! " + (todos.loadState.append as LoadState.Error).error.message,
                                        onRetryClicked = {
                                            retry()
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        LoadingContainer(
            show = showLoading
        )
    }
}
