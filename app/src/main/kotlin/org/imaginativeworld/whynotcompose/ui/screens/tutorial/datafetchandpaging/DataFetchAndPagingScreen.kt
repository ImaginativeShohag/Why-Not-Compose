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

package org.imaginativeworld.whynotcompose.ui.screens.tutorial.datafetchandpaging

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.isActive
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.base.models.Event
import org.imaginativeworld.whynotcompose.base.models.github.GithubRepo
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.compositions.LoadingContainer
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.repositories.MockData
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.datafetchandpaging.elements.EmptyView
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.datafetchandpaging.elements.GithubRepoItem
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.datafetchandpaging.elements.LoadingGithubRepoItem
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.datafetchandpaging.elements.SearchTextInputField

@Composable
fun DataFetchAndPagingScreen(
    viewModel: DataFetchAndPagingViewModel,
    goBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    val searchTFV = viewModel.searchText

    LaunchedEffect(searchTFV.value.text) {
        viewModel.loadPosts(
            query = searchTFV.value.text
        )
    }

    val pagedPosts = state.items.collectAsLazyPagingItems()

    DataFetchAndPagingScreenSkeleton(
        showLoadingView = state.loading,
        showMessage = state.message,
        repos = pagedPosts,
        searchTFV = searchTFV,
        openSearch = viewModel.openSearch.value,
        goBack = goBack,
        setOpenSearch = viewModel::setOpenSearch,
        retryDataLoad = {
            viewModel.loadPosts(
                query = searchTFV.value.text,
                delayRequest = false
            )
        }
    )
}

@PreviewLightDark
@Composable
private fun DataFetchAndPagingScreenSkeletonPreview() {
    AppTheme {
        DataFetchAndPagingScreenSkeleton(
            showLoadingView = false,
            showMessage = null,
            repos = flowOf(
                PagingData.from(
                    (1..10).map {
                        MockData.dummyGithubRepo.copy(id = it)
                    }
                )
            ).collectAsLazyPagingItems(),
            searchTFV = remember { mutableStateOf(TextFieldValue("Lorem")) }
        )
    }
}

@PreviewLightDark
@Composable
private fun DataFetchAndPagingScreenSkeletonBlankSearchPreview() {
    AppTheme {
        DataFetchAndPagingScreenSkeleton(
            showLoadingView = false,
            showMessage = null,
            repos = flowOf(
                PagingData.empty<GithubRepo>()
            ).collectAsLazyPagingItems(),
            searchTFV = remember { mutableStateOf(TextFieldValue()) }
        )
    }
}

@Suppress("ktlint:compose:modifier-missing-check", "ktlint:compose:param-order-check", "ktlint:compose:mutable-state-param-check")
@Composable
fun DataFetchAndPagingScreenSkeleton(
    showLoadingView: Boolean,
    showMessage: Event<String>?,
    repos: LazyPagingItems<GithubRepo>,
    searchTFV: MutableState<TextFieldValue>,
    openSearch: Boolean = false,
    goBack: () -> Unit = {},
    setOpenSearch: (Boolean) -> Unit = {},
    retryDataLoad: () -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val focusRequester = remember { FocusRequester() }

    val lazyListState = rememberLazyListState()

    LaunchedEffect(showMessage, retryDataLoad) {
        showMessage?.let { message ->
            message.getValueOnce()?.let { value ->
                val result = snackbarHostState.showSnackbar(value)

                if (result == SnackbarResult.ActionPerformed) {
                    retryDataLoad()
                }
            }
        }
    }

    LaunchedEffect(setOpenSearch) {
        delay(300)

        if (openSearch && isActive) {
            focusRequester.requestFocus()

            setOpenSearch(false)
        }
    }

    Scaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            AppComponent.Header(
                "Data Fetch and Paging",
                goBack = goBack
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Box(
                Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                repos.apply {
                    Column(
                        Modifier.verticalScroll(rememberScrollState())
                    ) {
                        EmptyView(
                            modifier = Modifier,
                            loadState = loadState,
                            itemCount = repos.itemCount,
                            title = if (searchTFV.value.text.isBlank()) {
                                "No search keyword!"
                            } else {
                                "Nothing found!"
                            },
                            message = if (searchTFV.value.text.isBlank()) {
                                "Enter anything to the search field."
                            } else {
                                "No repository found for \"${searchTFV.value.text}\"."
                            }
                        )
                    }
                }

                LazyColumn(
                    Modifier
                        .fillMaxSize(),
                    state = lazyListState,
                    contentPadding = PaddingValues(top = 52.dp, bottom = 4.dp)
                ) {
                    items(
                        count = repos.itemCount,
                        key = repos.itemKey { it.id }
                    ) { index ->
                        val repo = repos[index]

                        if (repo == null) {
                            Text("Loading...")
                        } else {
                            GithubRepoItem(
                                item = repo,
                                onClick = {
                                    // do something...
                                },
                                modifier = Modifier.padding(
                                    start = 12.dp,
                                    top = 4.dp,
                                    end = 12.dp,
                                    bottom = 4.dp
                                )
                            )
                        }
                    }

                    repos.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                item {
                                    Column {
                                        repeat(6) {
                                            LoadingGithubRepoItem(
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
                                        LoadingGithubRepoItem(
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
                                        message = "Error! " + (repos.loadState.refresh as LoadState.Error).error.message,
                                        onRetryClick = {
                                            retry()
                                        }
                                    )
                                }
                            }

                            loadState.append is LoadState.Error -> {
                                item {
                                    ErrorItem(
                                        message = "Error! " + (repos.loadState.append as LoadState.Error).error.message,
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

            // ----------------------------------------------------------------

            Box(
                Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                val searchBackgroundColor by animateColorAsState(
                    targetValue = if (searchTFV.value.text.isBlank()) {
                        MaterialTheme.colorScheme.errorContainer
                    } else {
                        MaterialTheme.colorScheme.background
                    }
                )

                Box(
                    Modifier
                        .padding(start = 8.dp, top = 8.dp, end = 8.dp)
                        .background(
                            color = searchBackgroundColor,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {
                    SearchTextInputField(
                        modifier = Modifier
                            .focusRequester(focusRequester)
                            .padding(end = 34.dp),
                        value = searchTFV.value,
                        onValueChange = {
                            searchTFV.value = it
                        },
                        background = Color.Transparent,
                        errorBackground = Color.Transparent,
                        shape = RoundedCornerShape(20.dp),
                        height = 40.dp,
                        horizontalPadding = 16.dp,
                        fontSize = 16.sp,
                        placeholder = "Search Github repositories...",
                        isError = searchTFV.value.text.isBlank()
                    )
                    Column(
                        Modifier
                            .padding(end = 16.dp)
                            .align(Alignment.CenterEnd)
                            .size(18.dp)
                    ) {
                        AnimatedVisibility(
                            modifier = Modifier,
                            visible = searchTFV.value.text.isNotEmpty(),
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(18.dp)
                                    .clickable {
                                        searchTFV.value = TextFieldValue()
                                    },
                                painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = "Clear",
                                colorFilter = ColorFilter.tint(
                                    MaterialTheme.colorScheme.onBackground
                                )
                            )
                        }

                        AnimatedVisibility(
                            modifier = Modifier,
                            visible = searchTFV.value.text.isEmpty(),
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(18.dp),
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = "Search",
                                colorFilter = ColorFilter.tint(
                                    MaterialTheme.colorScheme.onBackground
                                )
                            )
                        }
                    }
                }
            }
        }

        LoadingContainer(
            show = showLoadingView
        )
    }
}

@PreviewLightDark
@Composable
private fun ErrorItemPreview() {
    AppTheme {
        ErrorItem(
            message = "Something went wrong!",
            onRetryClick = {}
        )
    }
}

@Composable
fun ErrorItem(
    message: String,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp),
                text = message,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onErrorContainer,
                textAlign = TextAlign.Center
            )

            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onErrorContainer
                ),
                onClick = {
                    onRetryClick()
                }
            ) {
                Text("Retry")
            }
        }
    }
}
