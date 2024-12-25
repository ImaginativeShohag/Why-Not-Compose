package org.imaginativeworld.whynotcompose.ui.screens.ui.pager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.ui.screens.ui.pager.data.Element
import org.imaginativeworld.whynotcompose.ui.screens.ui.pager.data.ElementCategory
import org.imaginativeworld.whynotcompose.ui.screens.ui.pager.data.UiPagerRepository
import org.imaginativeworld.whynotcompose.ui.screens.ui.pager.elements.UiPagerCategoryScreen
import org.imaginativeworld.whynotcompose.ui.screens.ui.pager.elements.UiPagerDetailsScreen
import org.imaginativeworld.whynotcompose.ui.screens.ui.pager.elements.UiPagerEmptyDetailsScreen
import org.imaginativeworld.whynotcompose.ui.screens.ui.pager.elements.UiPagerLoadingSection
import org.imaginativeworld.whynotcompose.ui.screens.ui.pager.elements.UiPagerProfileScreen
import org.imaginativeworld.whynotcompose.ui.screens.ui.pager.elements.UiPagerTimelineScreen
import timber.log.Timber

@Composable
fun UiPagerScreen(
    viewModel: UiPagerViewModel,
    goBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.selectCategory(ElementCategory.Animal)
    }

    UiPagerScreenSkeleton(
        categories = state.categories,
        items = state.items,
        selectedCategory = state.selectedCategory,
        isRefreshing = false,
        goBack = goBack,
        onRefresh = {
            viewModel.load()
        },
        onCategoryClick = { category ->
            viewModel.selectCategory(category)
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
            selectedCategory = ElementCategory.Animal,
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
            selectedCategory = ElementCategory.Animal,
            isRefreshing = false
        )
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun UiPagerScreenSkeleton(
    categories: List<ElementCategory>,
    items: Flow<PagingData<Element>>,
    selectedCategory: ElementCategory,
    isRefreshing: Boolean,
    goBack: () -> Unit = {},
    onRefresh: () -> Unit = {},
    onCategoryClick: (ElementCategory) -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val pagedItems = items.collectAsLazyPagingItems()
    val screenPagerState = rememberPagerState(initialPage = 1, pageCount = { 4 })
    val timelinePagerState = rememberPagerState(
        pageCount = {
            Timber.d("count: ${pagedItems.itemCount}")
            pagedItems.itemCount + 1
        }
    )

    val menuItems = listOf(
        "Category" to Icons.Rounded.Category,
        "Home" to Icons.Rounded.Home,
        "Profile" to Icons.Rounded.AccountCircle
    )

    val selectedMenuIndex by remember {
        derivedStateOf {
            if (screenPagerState.currentPage == 2) {
                // Details screen will be act as a part of home menu.
                1
            } else if (screenPagerState.currentPage == 3) {
                // The 4th page (index 3) will be the 3rd menu (index 2)
                2
            } else {
                screenPagerState.currentPage
            }
        }
    }

    LaunchedEffect(selectedCategory) {
        timelinePagerState.scrollToPage(0)
    }

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
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = screenPagerState,
                pageSpacing = 16.dp
            ) { outerPage ->
                when (outerPage) {
                    0 -> {
                        UiPagerCategoryScreen(
                            categories = categories,
                            selectedCategory = selectedCategory,
                            onClick = { category ->
                                onCategoryClick(category)

                                scope.launch {
                                    screenPagerState.animateScrollToPage(1)
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    1 -> {
                        if (pagedItems.itemCount == 0) {
                            UiPagerLoadingSection()
                        } else {
                            UiPagerTimelineScreen(
                                pagerState = timelinePagerState,
                                pagedItems = pagedItems,
                                isRefreshing = isRefreshing,
                                onRefresh = onRefresh
                            )
                        }
                    }

                    2 -> {
                        val currentElement = if (pagedItems.itemCount > 0 &&
                            pagedItems.itemCount > timelinePagerState.settledPage
                        ) {
                            pagedItems[timelinePagerState.settledPage]
                        } else {
                            null
                        }

                        if (currentElement != null) {
                            UiPagerDetailsScreen(
                                item = currentElement
                            )
                        } else {
                            UiPagerEmptyDetailsScreen()
                        }
                    }

                    else -> {
                        UiPagerProfileScreen()
                    }
                }
            }

            NavigationBar {
                menuItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.second, contentDescription = null) },
                        label = { Text(item.first) },
                        selected = index == selectedMenuIndex,
                        onClick = {
                            scope.launch {
                                // 3rd Menu (index 2) will be mapped with profile screen (index 3)
                                if (index == 2) {
                                    screenPagerState.scrollToPage(3)
                                } else {
                                    screenPagerState.scrollToPage(index)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
