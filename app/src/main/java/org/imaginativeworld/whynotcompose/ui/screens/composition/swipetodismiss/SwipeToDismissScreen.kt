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
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection.EndToStart
import androidx.compose.material.DismissDirection.StartToEnd
import androidx.compose.material.DismissValue.Default
import androidx.compose.material.DismissValue.DismissedToEnd
import androidx.compose.material.DismissValue.DismissedToStart
import androidx.compose.material.Divider
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.models.ListItem
import org.imaginativeworld.whynotcompose.repositories.MockData
import org.imaginativeworld.whynotcompose.ui.screens.AppComponent
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme

// Source:
// https://cs.android.com/androidx/platform/tools/dokka-devsite-plugin/+/master:testData/compose/samples/material/samples/SwipeToDismissSamples.kt

private val dummyItems = MockData.dummyListItem.toList()

@Composable
fun SwipeToDismissScreen() {
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

@Preview
@Composable
fun SwipeToDismissScreenSkeletonPreview() {
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

@Preview
@Composable
fun SwipeToDismissScreenSkeletonPreviewDark() {
    AppTheme(darkTheme = true) {
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

@Composable
fun SwipeToDismissScreenSkeleton(
    items: List<ListItem>,
    onDelete: (ListItem) -> Unit,
) {
    val scope = rememberCoroutineScope()

    Scaffold(
        Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding()
    ) {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            AppComponent.Header("SwipeToDismiss")

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

            // ----------------------------------------------------------------

            // This is an example of a list of dismissible items, similar to what you would see in an
            // email app. Swiping left reveals a 'delete' icon and swiping right reveals a 'done' icon.
            // The background will start as grey, but once the dismiss threshold is reached, the colour
            // will animate to red if you're swiping left or green if you're swiping right. When you let
            // go, the item will animate out of the way if you're swiping left (like deleting an email) or
            // back to its default position if you're swiping right (like marking an email as read/unread).
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
            ) {
                items(
                    items = items,
                    key = { listItem: ListItem -> listItem.id }
                ) { item ->
                    var deleted by remember { mutableStateOf(false) }
                    var unread by remember { mutableStateOf(true) }
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissedToEnd) unread = !unread

                            if (it == DismissedToStart) {
                                deleted = true
                                return@rememberDismissState true
                            }
                            return@rememberDismissState false
                        }
                    )

                    if (deleted) {
                        scope.launch {
                            delay(500)

                            onDelete(item)
                        }
                    }

                    AnimatedVisibility(
                        visible = !deleted,
                        enter = expandVertically(),
                        exit = shrinkVertically()
                    ) {

                        SwipeToDismiss(
                            state = dismissState,
                            modifier = Modifier.padding(vertical = 4.dp),
                            directions = setOf(
                                StartToEnd,
                                EndToStart
                            ),
                            dismissThresholds = { direction ->
                                FractionalThreshold(if (direction == StartToEnd) 0.25f else 0.5f)
                            },
                            background = {
                                val direction =
                                    dismissState.dismissDirection ?: return@SwipeToDismiss
                                val color by animateColorAsState(
                                    when (dismissState.targetValue) {
                                        Default -> Color.LightGray
                                        DismissedToEnd -> Color.Green
                                        DismissedToStart -> Color.Red
                                    }
                                )
                                val alignment = when (direction) {
                                    StartToEnd -> Alignment.CenterStart
                                    EndToStart -> Alignment.CenterEnd
                                }
                                val icon = when (direction) {
                                    StartToEnd -> Icons.Default.Done
                                    EndToStart -> Icons.Default.Delete
                                }
                                val scale by animateFloatAsState(
                                    if (dismissState.targetValue == Default) 0.75f else 1f
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
                            },
                            dismissContent = {
                                Card(
                                    elevation = animateDpAsState(
                                        if (dismissState.dismissDirection != null) 4.dp else 0.dp
                                    ).value
                                ) {
                                    ListItem(
                                        text = {
                                            Text(
                                                item.name,
                                                fontWeight = if (unread) FontWeight.Bold else null,
                                                textDecoration = if (unread) TextDecoration.None else TextDecoration.LineThrough,
                                            )
                                        },
                                        secondaryText = { Text("Swipe me left or right!") }
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}