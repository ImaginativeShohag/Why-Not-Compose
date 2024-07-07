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

package org.imaginativeworld.whynotcompose.ui.screens.composition.swipetodismiss

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.models.ListItemModel
import org.imaginativeworld.whynotcompose.repositories.MockData

// Source:
// https://cs.android.com/androidx/platform/tools/dokka-devsite-plugin/+/master:testData/compose/samples/material3/samples/SwipeToDismissSamples.kt

private val dummyItems = MockData.dummyListItem.toList()

@Composable
fun SwipeToDismissScreen(
    goBack: () -> Unit
) {
    val items = remember { mutableStateOf(dummyItems) }

    SwipeToDismissScreenSkeleton(
        goBack = goBack,
        items = items.value,
        onDelete = {
            items.value = items.value.toMutableList().apply {
                remove(it)
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun SwipeToDismissScreenSkeletonPreview() {
    AppTheme {
        val items = remember { mutableStateOf(dummyItems) }

        SwipeToDismissScreenSkeleton(
            items = items.value,
            onDelete = {
                items.value = items.value.toMutableList().apply {
                    remove(it)
                }
            }
        )
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun SwipeToDismissScreenSkeleton(
    items: List<ListItemModel>,
    onDelete: (ListItemModel) -> Unit,
    goBack: () -> Unit = {}
) {
    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            AppComponent.Header(
                "SwipeToDismiss",
                goBack = goBack
            )
        }
    ) { innerPadding ->
        LazyColumn(
            Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
        ) {
            items(
                items = items,
                key = { listItem: ListItemModel -> listItem.id }
            ) { item ->
                var deleted by remember { mutableStateOf(false) }
                var unread by remember { mutableStateOf(true) }
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        if (it == SwipeToDismissBoxValue.StartToEnd) unread = !unread

                        if (it == SwipeToDismissBoxValue.EndToStart) {
                            deleted = true
                            return@rememberSwipeToDismissBoxState true
                        }
                        return@rememberSwipeToDismissBoxState false
                    }
                )

                LaunchedEffect(deleted, onDelete) {
                    if (deleted) {
                        delay(500)

                        onDelete(item)
                    }
                }

                AnimatedVisibility(
                    visible = !deleted,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    SwipeToDismissBox(
                        state = dismissState,
                        backgroundContent = {
                            val direction =
                                dismissState.dismissDirection

                            val color by animateColorAsState(
                                when (dismissState.targetValue) {
                                    SwipeToDismissBoxValue.Settled -> Color.Transparent
                                    SwipeToDismissBoxValue.StartToEnd -> Color.Green
                                    SwipeToDismissBoxValue.EndToStart -> Color.Red
                                }
                            )
                            // Box(Modifier.fillMaxSize().background(color))

                            val alignment = when (direction) {
                                SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
                                SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
                                SwipeToDismissBoxValue.Settled -> Alignment.Center
                            }
                            val icon = when (direction) {
                                SwipeToDismissBoxValue.StartToEnd -> Icons.Default.Done
                                SwipeToDismissBoxValue.EndToStart -> Icons.Default.Delete
                                SwipeToDismissBoxValue.Settled -> Icons.Default.Delete
                            }
                            val scale by animateFloatAsState(
                                if (dismissState.targetValue == SwipeToDismissBoxValue.Settled) 0.75f else 1f
                            )

                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(horizontal = 20.dp),
                                contentAlignment = alignment
                            ) {
                                Icon(
                                    icon,
                                    contentDescription = "Localized description",
                                    modifier = Modifier.scale(scale)
                                )
                            }
                        }
                    ) {
                        Card {
                            ListItem(
                                headlineContent = {
                                    Text(
                                        item.name,
                                        fontWeight = if (unread) FontWeight.Bold else null,
                                        textDecoration = if (unread) TextDecoration.None else TextDecoration.LineThrough
                                    )
                                },
                                supportingContent = { Text("Swipe me left or right!") }
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}
