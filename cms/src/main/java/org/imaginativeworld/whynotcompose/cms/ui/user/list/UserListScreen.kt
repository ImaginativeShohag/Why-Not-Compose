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

package org.imaginativeworld.whynotcompose.cms.ui.user.list

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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.base.models.Event
import org.imaginativeworld.whynotcompose.cms.models.user.User
import org.imaginativeworld.whynotcompose.cms.repositories.MockData
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme
import org.imaginativeworld.whynotcompose.cms.ui.common.EmptyView
import org.imaginativeworld.whynotcompose.cms.ui.common.ErrorItem
import org.imaginativeworld.whynotcompose.cms.ui.common.GeneralAppBar
import org.imaginativeworld.whynotcompose.cms.ui.common.LoadingContainer
import org.imaginativeworld.whynotcompose.cms.ui.common.LoadingItem
import org.imaginativeworld.whynotcompose.cms.ui.user.add.UserAddSheet
import org.imaginativeworld.whynotcompose.cms.ui.user.list.elements.UserItem

@Composable
fun UserListScreen(
    viewModel: UserListViewModel,
    goBack: () -> Unit,
    toggleUIMode: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    val pagedUsers = state.items.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.loadUsers()
    }

    UserListScreenSkeleton(
        goBack = goBack,
        toggleUIMode = toggleUIMode,
        showLoading = state.loading,
        showMessage = state.message,
        users = pagedUsers,
        retryDataLoad = {
            viewModel.loadUsers()
        }
    )
}

@Preview
@Composable
fun UserListScreenSkeletonPreview() {
    CMSAppTheme {
        UserListScreenSkeleton(
            goBack = { },
            toggleUIMode = {},
            users = flowOf(
                PagingData.from(
                    MockData.dummayUserList
                )
            ).collectAsLazyPagingItems()
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UserListScreenSkeletonPreviewDark() {
    CMSAppTheme {
        UserListScreenSkeleton(
            goBack = { },
            toggleUIMode = {},
            users = flowOf(
                PagingData.from(
                    MockData.dummayUserList
                )
            ).collectAsLazyPagingItems()
        )
    }
}

@Composable
fun UserListScreenSkeleton(
    goBack: () -> Unit,
    toggleUIMode: () -> Unit,
    showLoading: Boolean = false,
    showMessage: Event<String>? = null,
    users: LazyPagingItems<User>,
    retryDataLoad: () -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var openAddUserSheet by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
        confirmValueChange = { sheetState ->
            return@rememberModalBottomSheetState sheetState != SheetValue.Hidden
        }
    )

    val expandedFab by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0
        }
    }

    LaunchedEffect(showMessage) {
        showMessage?.let { message ->
            message.getValueOnce()?.let { value ->
                val result = snackbarHostState.showSnackbar(value)

                if (result == SnackbarResult.ActionPerformed) {
                    retryDataLoad()
                }
            }
        }
    }

    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    openAddUserSheet = !openAddUserSheet
                },
                expanded = expandedFab,
                icon = { Icon(Icons.Filled.Add, "Add User") },
                text = { Text(text = "Add User") }
            )
        },
        topBar = {
            GeneralAppBar(
                subTitle = "Users",
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
                users.apply {
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
                    items(users) { user ->
                        if (user == null) {
                            Text("Loading...")
                        } else {
                            UserItem(
                                modifier = Modifier.padding(
                                    start = 12.dp,
                                    top = 4.dp,
                                    end = 12.dp,
                                    bottom = 4.dp
                                ),
                                name = user.name,
                                email = user.email,
                                gender = user.getGenderLabel(),
                                status = user.getStatusLabel(),
                                userImageUrl = "https://picsum.photos/seed/u${user.id}/200/200",
                                statusColor = user.getStatusColor(),
                                onClick = {
                                    // open details sheet
                                    // do something...
                                }
                            )
                        }
                    }

                    users.apply {
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
                                        errorMessage = "Error! " + (users.loadState.refresh as LoadState.Error).error.message,
                                        onRetryClicked = {
                                            retry()
                                        }
                                    )
                                }
                            }

                            loadState.append is LoadState.Error -> {
                                item {
                                    ErrorItem(
                                        errorMessage = "Error! " + (users.loadState.append as LoadState.Error).error.message,
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

    // ----------------------------------------------------------------
    // Bottom Sheet
    // ----------------------------------------------------------------

    if (openAddUserSheet) {
        ModalBottomSheet(
            onDismissRequest = { openAddUserSheet = false },
            sheetState = bottomSheetState
        ) {
            UserAddSheet(
                goBack = {
                    scope.launch {
                        bottomSheetState.hide()
                    }.invokeOnCompletion {
                        if (!bottomSheetState.isVisible) {
                            openAddUserSheet = false
                        }
                    }
                },
                addUser = { name, email, gender, status ->
                    //
                }
            )
        }
    }
}
