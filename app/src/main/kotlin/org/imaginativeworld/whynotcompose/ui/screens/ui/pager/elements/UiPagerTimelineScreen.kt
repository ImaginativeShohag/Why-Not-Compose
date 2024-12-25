package org.imaginativeworld.whynotcompose.ui.screens.ui.pager.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.flowOf
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.ui.screens.ui.pager.data.Element
import org.imaginativeworld.whynotcompose.ui.screens.ui.pager.data.UiPagerRepository

@Composable
fun UiPagerTimelineScreen(
    pagerState: PagerState,
    pagedItems: LazyPagingItems<Element>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        state = pullToRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh
    ) {
        VerticalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
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
                    UiPagerTheEndSection()
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

@PreviewLightDark
@Composable
private fun UiPagerTimelineScreenPreview() {
    AppTheme {
        Scaffold { innerPadding ->
            UiPagerTimelineScreen(
                modifier = Modifier.padding(innerPadding),
                pagerState = rememberPagerState(
                    pageCount = { UiPagerRepository.animals.size }
                ),
                pagedItems = flowOf(
                    PagingData.from(UiPagerRepository.animals)
                ).collectAsLazyPagingItems(),
                isRefreshing = false,
                onRefresh = {}

            )
        }
    }
}
