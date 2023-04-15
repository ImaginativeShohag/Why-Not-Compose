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

package org.imaginativeworld.whynotcompose.ui.screens.composition.swiperefresh

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.common.compose.composeutils.rememberImagePainter
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.models.ListItem
import org.imaginativeworld.whynotcompose.repositories.MockData

@Composable
fun SwipeRefreshScreen() {
    val scope = rememberCoroutineScope()

    val items = remember { mutableStateOf(MockData.dummyListItem) }

    val isRefreshing = remember { mutableStateOf(false) }

    SwipeRefreshScreenSkeleton(
        items = items.value,
        isRefreshing = isRefreshing.value,
        onRefresh = {
            scope.launch {
                isRefreshing.value = true

                delay(2000)

                items.value = items.value.shuffled()

                isRefreshing.value = false
            }
        }
    )
}

@Preview
@Composable
fun SwipeRefreshScreenSkeletonPreview() {
    AppTheme {
        val items = remember { mutableStateOf(MockData.dummyListItem) }

        SwipeRefreshScreenSkeleton(
            items = items.value
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SwipeRefreshScreenSkeletonPreviewDark() {
    AppTheme {
        val items = remember { mutableStateOf(MockData.dummyListItem) }

        SwipeRefreshScreenSkeleton(
            items = items.value
        )
    }
}

@Composable
fun SwipeRefreshScreenSkeleton(
    items: List<ListItem>,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {}
) {
    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh = { onRefresh() })

    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding()
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            AppComponent.Header("SwipeRefresh")

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

            // ----------------------------------------------------------------

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .pullRefresh(pullRefreshState)
            ) {
                LazyColumn(
                    Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 8.dp)
                ) {
                    items(items, key = { it.id }) { item ->
                        ListItem(
                            modifier = Modifier
                                .animateItemPlacement()
                                .padding(top = 8.dp)
                                .background(
                                    color = MaterialTheme.colors.onBackground.copy(alpha = .1f),
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            text = { Text(item.name) },
                            icon = {
                                Image(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(RoundedCornerShape(4.dp)),
                                    painter = rememberImagePainter(
                                        data = item.image,
                                        crossFade = true
                                    ),
                                    contentDescription = item.name
                                )
                            }
                        )
                    }
                }

                PullRefreshIndicator(
                    isRefreshing,
                    pullRefreshState,
                    Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }
}
