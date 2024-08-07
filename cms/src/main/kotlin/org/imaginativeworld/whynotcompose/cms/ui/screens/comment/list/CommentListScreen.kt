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

package org.imaginativeworld.whynotcompose.cms.ui.screens.comment.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.flowOf
import org.imaginativeworld.whynotcompose.base.models.Event
import org.imaginativeworld.whynotcompose.cms.models.Comment
import org.imaginativeworld.whynotcompose.cms.repositories.MockData
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme
import org.imaginativeworld.whynotcompose.cms.ui.compositions.EmptyView
import org.imaginativeworld.whynotcompose.cms.ui.compositions.ErrorItem
import org.imaginativeworld.whynotcompose.cms.ui.compositions.GeneralAppBar
import org.imaginativeworld.whynotcompose.cms.ui.compositions.LoadingContainer
import org.imaginativeworld.whynotcompose.cms.ui.compositions.LoadingItem
import org.imaginativeworld.whynotcompose.cms.ui.screens.comment.add.CommentAddSheet
import org.imaginativeworld.whynotcompose.cms.ui.screens.comment.list.elements.CommentItem

@Composable
fun CommentListScreen(
    viewModel: CommentListViewModel,
    postId: Int,
    goBack: () -> Unit,
    toggleUIMode: () -> Unit,
    goToCommentDetails: (Int) -> Unit
) {
    val openAddCommentSheet = rememberSaveable { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()

    val pagedComments = state.items.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.loadComments(postId)
    }

    CommentListScreenSkeleton(
        goBack = goBack,
        toggleUIMode = toggleUIMode,
        showLoading = state.loading,
        showMessage = state.message,
        comments = pagedComments,
        retryDataLoad = {
            viewModel.loadComments(postId)
        },
        openAddCommentSheet = {
            openAddCommentSheet.value = !openAddCommentSheet.value
        },
        goToCommentDetails = goToCommentDetails
    )

    // ----------------------------------------------------------------
    // Bottom Sheet
    // ----------------------------------------------------------------

    if (openAddCommentSheet.value) {
        CommentAddSheet(
            showSheet = openAddCommentSheet,
            postId = postId,
            onSuccess = {
                viewModel.loadComments(postId)
            }
        )
    }
}

@PreviewLightDark
@Composable
private fun CommentListScreenSkeletonPreview() {
    CMSAppTheme {
        CommentListScreenSkeleton(
            showLoading = false,
            showMessage = null,
            goBack = {},
            toggleUIMode = {},
            comments = flowOf(
                PagingData.from(
                    MockData.dummyCommentList
                )
            ).collectAsLazyPagingItems()
        )
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun CommentListScreenSkeleton(
    showLoading: Boolean,
    showMessage: Event<String>?,
    goBack: () -> Unit,
    toggleUIMode: () -> Unit,
    comments: LazyPagingItems<Comment>,
    retryDataLoad: () -> Unit = {},
    openAddCommentSheet: () -> Unit = {},
    goToCommentDetails: (Int) -> Unit = { _ -> }
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val lazyListState = rememberLazyListState()

    val expandedFab by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0
        }
    }

    LaunchedEffect(showMessage, retryDataLoad) {
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
                    openAddCommentSheet()
                },
                expanded = expandedFab,
                icon = { Icon(Icons.Filled.Add, "Add Comment") },
                text = { Text(text = "Add Comment") }
            )
        },
        topBar = {
            GeneralAppBar(
                subTitle = "Comments",
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
                comments.apply {
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
                        count = comments.itemCount,
                        key = comments.itemKey { it.id }
                    ) { index ->
                        val comment = comments[index]

                        if (comment == null) {
                            Text("Loading...")
                        } else {
                            CommentItem(
                                modifier = Modifier.padding(
                                    start = 12.dp,
                                    top = 4.dp,
                                    end = 12.dp,
                                    bottom = 4.dp
                                ),
                                name = comment.name,
                                email = comment.email,
                                body = comment.body,
                                isPreview = true,
                                userImageUrl = comment.getAvatarImageUrl(),
                                onClick = {
                                    goToCommentDetails(comment.id)
                                }
                            )
                        }
                    }

                    comments.apply {
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
                                        message = "Error! " + (comments.loadState.refresh as LoadState.Error).error.message,
                                        onRetryClick = {
                                            retry()
                                        }
                                    )
                                }
                            }

                            loadState.append is LoadState.Error -> {
                                item {
                                    ErrorItem(
                                        message = "Error! " + (comments.loadState.append as LoadState.Error).error.message,
                                        onRetryClick = {
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
