package org.imaginativeworld.whynotcompose.ui.screens.ui.pager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiFoodBeverage
import androidx.compose.material.icons.rounded.FrontHand
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import timber.log.Timber

// - [x] Paging
// - [x] Swipe to refresh
// - [x] Last item message
// - [ ] bottom indicator
// - [ ] working category screen

@Composable
fun UiPagerScreen(
    viewModel: UiPagerViewModel,
    goBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    // val pagedItems = state.items.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.selectType(ElementCategory.Animal)
    }

    UiPagerScreenSkeleton(
        categories = state.categories,
        items = state.items,
        isRefreshing = false,
        goBack = goBack,
        onRefresh = {
            viewModel.load()
        }
    )
}

@PreviewLightDark
@Composable
private fun UiPagerScreenSkeletonPreview() {
    AppTheme {
        UiPagerScreenSkeleton(
            categories = ElementCategory.entries,
            items = flowOf(
                PagingData.from(UiPagerRepository.animals)
            ),
            isRefreshing = false
        )
    }
}

@PreviewLightDark
@Composable
private fun UiPagerScreenSkeletonLoadingPreview() {
    AppTheme {
        UiPagerScreenSkeleton(
            categories = ElementCategory.entries,
            items = flowOf(
                PagingData.empty()
            ),
            isRefreshing = false
        )
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun UiPagerScreenSkeleton(
    categories: List<ElementCategory>,
    items: Flow<PagingData<Element>>,
    isRefreshing: Boolean,
    goBack: () -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    val pagedItems = items.collectAsLazyPagingItems()
    val pullToRefreshState = rememberPullToRefreshState()
    val screenPagerState = rememberPagerState(initialPage = 1, pageCount = { 3 })
    val timelinePagerState = rememberPagerState(
        pageCount = {
            Timber.d("count: ${pagedItems.itemCount}")
            pagedItems.itemCount + 1
        }
    )

    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            AppComponent.Header(
                "Pager",
                goBack = goBack
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                state = screenPagerState,
                pageSpacing = 16.dp
            ) { outerPage ->
                when (outerPage) {
                    0 -> {
                        CategoryScreen(
                            categories = categories,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    1 -> {
                        if (pagedItems.itemCount == 0) {
                            Column(
                                Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator()

                                Spacer(Modifier.height(16.dp))

                                Text(
                                    "Loading...",
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                        } else {
                            PullToRefreshBox(
                                modifier = Modifier.fillMaxSize(),
                                state = pullToRefreshState,
                                isRefreshing = isRefreshing,
                                onRefresh = onRefresh
                            ) {
                                VerticalPager(
                                    modifier = Modifier.fillMaxSize(),
                                    state = timelinePagerState,
                                    pageSpacing = 16.dp
                                ) { page ->
                                    val item = if (pagedItems.itemCount > page) {
                                        pagedItems[page]
                                    } else {
                                        null
                                    }

                                    Box(
                                        Modifier
                                            .fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (item == null) {
                                            TheEndSection()
                                        } else {
                                            Column(
                                                Modifier
                                                    .fillMaxSize()
                                                    .background(Color(item.dominantColor.toColorInt()).copy(0.1f)),
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Text(
                                                    text = item.emoji,
                                                    textAlign = TextAlign.Center,
                                                    style = MaterialTheme.typography.displayLarge
                                                )

                                                Text(
                                                    text = "${page + 1}. ${item.name}",
                                                    textAlign = TextAlign.Center,
                                                    style = MaterialTheme.typography.displayLarge
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    else -> {
                        val currentElement = if (pagedItems.itemCount > 0 &&
                            pagedItems.itemCount > timelinePagerState.settledPage
                        ) {
                            pagedItems[timelinePagerState.settledPage]
                        } else {
                            null
                        }

                        if (currentElement != null) {
                            DetailsScreen(
                                item = currentElement
                            )
                        } else {
                            EmptyDetailsScreen()
                        }
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------------------

@Composable
private fun CategoryScreen(
    categories: List<ElementCategory>,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxSize()) {
        for (category in categories) {
            CategoryButton(
                title = category.title,
                onClick = {},
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun CategoryButton(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors()
    ) {
        Box(
            Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.displayLarge
            )
        }
    }
}

// ----------------------------------------------------------------

@Composable
private fun DetailsScreen(
    item: Element,
    modifier: Modifier = Modifier
) {
    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color(item.dominantColor.toColorInt()).copy(0.1f))
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = item.emoji,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge
            )

            Text(
                text = item.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge
            )

            Text(
                text = item.description,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}

// ----------------------------------------------------------------

@Composable
private fun TheEndSection(modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Rounded.EmojiFoodBeverage,
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            "The End",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@PreviewLightDark
@Composable
private fun TheEndSectionPreview() {
    AppTheme {
        Scaffold { innerPadding ->
            TheEndSection(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

// ----------------------------------------------------------------

@Composable
private fun EmptyDetailsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Rounded.FrontHand,
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            "Nothing to see here!",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@PreviewLightDark
@Composable
private fun EmptyDetailsScreenPreview() {
    AppTheme {
        Scaffold { innerPadding ->
            EmptyDetailsScreen(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
