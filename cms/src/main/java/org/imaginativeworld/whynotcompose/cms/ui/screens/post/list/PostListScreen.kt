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

package org.imaginativeworld.whynotcompose.cms.ui.screens.post.list

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
import org.imaginativeworld.whynotcompose.cms.models.Post
import org.imaginativeworld.whynotcompose.cms.repositories.MockData
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme
import org.imaginativeworld.whynotcompose.cms.ui.compositions.EmptyView
import org.imaginativeworld.whynotcompose.cms.ui.compositions.ErrorItem
import org.imaginativeworld.whynotcompose.cms.ui.compositions.GeneralAppBar
import org.imaginativeworld.whynotcompose.cms.ui.compositions.LoadingContainer
import org.imaginativeworld.whynotcompose.cms.ui.compositions.LoadingItem
import org.imaginativeworld.whynotcompose.cms.ui.screens.post.add.PostAddSheet
import org.imaginativeworld.whynotcompose.cms.ui.screens.post.list.elements.PostItem

@Composable
fun PostListScreen(
    viewModel: PostListViewModel,
    userId: Int,
    goBack: () -> Unit,
    toggleUIMode: () -> Unit,
    goToPostDetails: (Int) -> Unit
) {
    val openAddPostSheet = rememberSaveable { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()

    val pagedPosts = state.items.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.loadPosts(userId)
    }

    PostListScreenSkeleton(
        goBack = goBack,
        toggleUIMode = toggleUIMode,
        showLoading = state.loading,
        showMessage = state.message,
        posts = pagedPosts,
        retryDataLoad = {
            viewModel.loadPosts(userId)
        },
        openAddPostSheet = {
            openAddPostSheet.value = !openAddPostSheet.value
        },
        goToPostDetails = goToPostDetails
    )

    // ----------------------------------------------------------------
    // Bottom Sheet
    // ----------------------------------------------------------------

    if (openAddPostSheet.value) {
        PostAddSheet(
            showSheet = openAddPostSheet,
            userId = userId,
            onSuccess = {
                viewModel.loadPosts(userId)
            }
        )
    }
}

@Preview
@Composable
fun PostListScreenSkeletonPreview() {
    CMSAppTheme {
        PostListScreenSkeleton(
            goBack = {},
            toggleUIMode = {},
            posts = flowOf(
                PagingData.from(
                    MockData.dummyPostList
                )
            ).collectAsLazyPagingItems()
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PostListScreenSkeletonPreviewDark() {
    CMSAppTheme {
        PostListScreenSkeleton(
            goBack = {},
            toggleUIMode = {},
            posts = flowOf(
                PagingData.from(
                    MockData.dummyPostList
                )
            ).collectAsLazyPagingItems()
        )
    }
}

@Composable
fun PostListScreenSkeleton(
    goBack: () -> Unit,
    toggleUIMode: () -> Unit,
    showLoading: Boolean = false,
    showMessage: Event<String>? = null,
    posts: LazyPagingItems<Post>,
    retryDataLoad: () -> Unit = {},
    openAddPostSheet: () -> Unit = {},
    goToPostDetails: (Int) -> Unit = { _ -> }
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
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    openAddPostSheet()
                },
                expanded = expandedFab,
                icon = { Icon(Icons.Filled.Add, "Add Post") },
                text = { Text(text = "Add Post") }
            )
        },
        topBar = {
            GeneralAppBar(
                subTitle = "Posts",
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
                posts.apply {
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
                        count = posts.itemCount,
                        key = posts.itemKey { it.id }
                    ) { index ->
                        val post = posts[index]

                        if (post == null) {
                            Text("Loading...")
                        } else {
                            PostItem(
                                modifier = Modifier.padding(
                                    start = 12.dp,
                                    top = 4.dp,
                                    end = 12.dp,
                                    bottom = 4.dp
                                ),
                                title = post.title,
                                body = post.body,
                                featuredImageUrl = post.getFeaturedImageUrl(),
                                isPreview = true,
                                onClick = {
                                    goToPostDetails(post.id)
                                }
                            )
                        }
                    }

                    posts.apply {
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
                                        errorMessage = "Error! " + (posts.loadState.refresh as LoadState.Error).error.message,
                                        onRetryClicked = {
                                            retry()
                                        }
                                    )
                                }
                            }

                            loadState.append is LoadState.Error -> {
                                item {
                                    ErrorItem(
                                        errorMessage = "Error! " + (posts.loadState.append as LoadState.Error).error.message,
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
