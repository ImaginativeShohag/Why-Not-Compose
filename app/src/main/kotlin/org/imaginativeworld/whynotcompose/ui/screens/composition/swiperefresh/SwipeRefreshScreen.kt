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

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.common.compose.composeutils.rememberImagePainter
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.models.ListItemModel
import org.imaginativeworld.whynotcompose.repositories.MockData

// Source: https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/material3/material3/samples/src/main/java/androidx/compose/material3/samples/PullToRefreshSamples.kt

@Composable
fun SwipeRefreshScreen(
    goBack: () -> Unit
) {
    val scope = rememberCoroutineScope()

    val items = remember { mutableStateOf(MockData.dummyListItem) }

    val isRefreshing = remember { mutableStateOf(false) }

    SwipeRefreshScreenSkeleton(
        goBack = goBack,
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

@PreviewLightDark
@Composable
private fun SwipeRefreshScreenSkeletonPreview() {
    AppTheme {
        val items = remember { mutableStateOf(MockData.dummyListItem) }

        SwipeRefreshScreenSkeleton(
            items = items.value
        )
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun SwipeRefreshScreenSkeleton(
    items: List<ListItemModel>,
    isRefreshing: Boolean = false,
    goBack: () -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    val state = rememberPullToRefreshState()

    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            AppComponent.Header(
                "SwipeRefresh",
                goBack = goBack
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier.padding(innerPadding),
            state = state,
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            indicator = {
                PullToRefreshDefaults.Indicator(
                    state = state,
                    isRefreshing = isRefreshing,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        ) {
            LazyColumn(
                Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 8.dp)
            ) {
                items(items, key = { it.id }) { item ->
                    ListItem(
                        modifier = Modifier
                            .animateItem()
                            .padding(top = 8.dp)
                            .background(
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = .1f),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        headlineContent = { Text(item.name) },
                        leadingContent = {
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
        }
    }
}
