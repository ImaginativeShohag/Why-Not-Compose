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

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.isActive
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.models.Event
import org.imaginativeworld.whynotcompose.models.github.GithubRepo
import org.imaginativeworld.whynotcompose.repositories.MockData
import org.imaginativeworld.whynotcompose.ui.screens.AppComponent
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.datafetchandpaging.elements.EmptyView
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.datafetchandpaging.elements.GithubRepoItem
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.datafetchandpaging.elements.LoadingGithubRepoItem
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.datafetchandpaging.elements.SearchTextInputField
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme
import org.imaginativeworld.whynotcompose.ui.theme.TailwindCSSColor
import org.imaginativeworld.whynotcompose.utils.compositions.LoadingContainer

val Colors.searchErrorInputBackground: Color
    @Composable get() = if (isLight) TailwindCSSColor.Red500 else TailwindCSSColor.Red900

@Composable
fun DataFetchAndPagingScreen(
    viewModel: DataFetchAndPagingViewModel,
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
        setOpenSearch = viewModel::setOpenSearch,
        retryDataLoad = {
            viewModel.loadPosts(
                query = searchTFV.value.text,
                delayRequest = false
            )
        }
    )
}

@Preview
@Composable
fun DataFetchAndPagingScreenSkeletonPreview() {
    AppTheme {
        DataFetchAndPagingScreenSkeleton(
            repos = flowOf(
                PagingData.from(
                    listOf(
                        MockData.dummyGithubRepo,
                        MockData.dummyGithubRepo,
                        MockData.dummyGithubRepo,
                        MockData.dummyGithubRepo,
                        MockData.dummyGithubRepo,
                        MockData.dummyGithubRepo,
                        MockData.dummyGithubRepo
                    )
                )
            ).collectAsLazyPagingItems(),
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DataFetchAndPagingScreenSkeletonPreviewDark() {
    AppTheme {
        DataFetchAndPagingScreenSkeleton(
            repos = flowOf(
                PagingData.from(
                    listOf(
                        MockData.dummyGithubRepo,
                        MockData.dummyGithubRepo,
                        MockData.dummyGithubRepo,
                        MockData.dummyGithubRepo,
                        MockData.dummyGithubRepo,
                        MockData.dummyGithubRepo,
                        MockData.dummyGithubRepo
                    )
                )
            ).collectAsLazyPagingItems(),
        )
    }
}

@Composable
fun DataFetchAndPagingScreenSkeleton(
    showLoadingView: Boolean = false,
    showMessage: Event<String>? = null,
    repos: LazyPagingItems<GithubRepo>,
    searchTFV: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue()) },
    openSearch: Boolean = false,
    setOpenSearch: (Boolean) -> Unit = {},
    retryDataLoad: () -> Unit = {},
) {
    val scaffoldState = rememberScaffoldState()

    val focusRequester = remember { FocusRequester() }

    val lazyListState = rememberLazyListState()

    LaunchedEffect(showMessage) {
        showMessage?.let { message ->
            message.getValueOnce()?.let { value ->
                val result = scaffoldState.snackbarHostState.showSnackbar(value)

                if (result == SnackbarResult.ActionPerformed) {
                    retryDataLoad()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        delay(300)

        if (openSearch && isActive) {
            focusRequester.requestFocus()

            setOpenSearch(false)
        }
    }

    Scaffold(
        modifier = Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding(),
        scaffoldState = scaffoldState,
    ) {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            AppComponent.Header("Data Fetch and Paging")

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Box(
                    Modifier
                        .padding(top = 32.dp)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    repos.apply {
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
                        contentPadding = PaddingValues(top = 28.dp, bottom = 4.dp),
                    ) {
                        items(repos) { repo ->
                            if (repo == null) {
                                Text("Loading...")
                            } else {
                                GithubRepoItem(
                                    modifier = Modifier.padding(
                                        start = 12.dp,
                                        top = 4.dp,
                                        end = 12.dp,
                                        bottom = 4.dp
                                    ),
                                    item = repo,
                                    onClick = {
                                        // do something...
                                    }
                                )
                            }
                        }

                        repos.apply {
                            when {
                                loadState.refresh is LoadState.Loading -> {
                                    item {
                                        Column {
                                            for (i in 1..6) {
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
                                            errorMessage = "Error! " + (repos.loadState.refresh as LoadState.Error).error.message,
                                            onRetryClicked = {
                                                retry()
                                            }
                                        )
                                    }
                                }

                                loadState.append is LoadState.Error -> {
                                    item {
                                        ErrorItem(
                                            errorMessage = "Error! " + (repos.loadState.append as LoadState.Error).error.message,
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

                // ----------------------------------------------------------------

                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(
                            MaterialTheme.colors.primary,
                            RoundedCornerShape(24.dp, 24.dp, 24.dp, 24.dp)
                        )
                ) {
                    val searchBackgroundColor by animateColorAsState(
                        targetValue = if (searchTFV.value.text.isBlank())
                            MaterialTheme.colors.searchErrorInputBackground
                        else MaterialTheme.colors.surface
                    )

                    Box(
                        Modifier
                            .padding(start = 8.dp, top = 8.dp, end = 8.dp)
                            .background(
                                color = searchBackgroundColor,
                                shape = RoundedCornerShape(20.dp)
                            )
                    ) {
                        SearchTextInputField(
                            modifier = Modifier
                                .focusRequester(focusRequester)
                                .padding(end = 34.dp),
                            background = Color.Transparent,
                            errorBackground = Color.Transparent,
                            shape = RoundedCornerShape(20.dp),
                            height = 40.dp,
                            horizontalPadding = 16.dp,
                            fontSize = 16.sp,
                            textFieldValue = searchTFV,
                            placeholder = "Search Github repositories...",
                            isError = searchTFV.value.text.isBlank(),
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
                                exit = fadeOut(),
                            ) {
                                Image(
                                    modifier = Modifier
                                        .size(18.dp)
                                        .clickable {
                                            searchTFV.value = TextFieldValue()
                                        },
                                    painter = painterResource(id = R.drawable.ic_close),
                                    contentDescription = "Clear",
                                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
                                )
                            }

                            AnimatedVisibility(
                                modifier = Modifier,
                                visible = searchTFV.value.text.isEmpty(),
                                enter = fadeIn(),
                                exit = fadeOut(),
                            ) {
                                Image(
                                    modifier = Modifier
                                        .size(18.dp),
                                    painter = painterResource(id = R.drawable.ic_search),
                                    contentDescription = "Search",
                                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
                                )
                            }
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

@Preview
@Composable
fun ErrorItemPreview() {
    AppTheme {
        ErrorItem(
            "Something went wrong!"
        ) {

        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ErrorItemPreviewDark() {
    AppTheme {
        ErrorItem(
            "Something went wrong!"
        ) {

        }
    }
}

@Composable
fun ErrorItem(
    errorMessage: String,
    onRetryClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.error
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp),
                text = errorMessage,
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp),
                onClick = {
                    onRetryClicked()
                }
            ) {
                Text("Retry")
            }
        }
    }
}