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

package org.imaginativeworld.whynotcompose.ui.screens.composition.listitem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

@Composable
fun ListItemScreen(
    goBack: () -> Unit
) {
    ListItemScreenSkeleton(
        goBack = goBack
    )
}

@Preview
@Composable
fun ListItemScreenSkeletonPreview() {
    AppTheme {
        ListItemScreenSkeleton()
    }
}

@Preview
@Composable
fun ListItemScreenSkeletonPreviewDark() {
    AppTheme(
        darkTheme = true
    ) {
        ListItemScreenSkeleton()
    }
}

@Composable
fun ListItemScreenSkeleton(
    goBack: () -> Unit = {}
) {
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
            AppComponent.Header(
                "ListItem",
                goBack = goBack
            )

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

            // ----------------------------------------------------------------
            // One-line items
            // ----------------------------------------------------------------

            val scrollState = rememberScrollState()

            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(0.dp)
            ) {
                ListItem(text = { Text("One line list item with no icon") })
                Divider()
                ListItem(
                    text = { Text("One line list item with 24x24 icon") },
                    icon = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = null
                        )
                    }
                )
                Divider()
                ListItem(
                    text = { Text("One line list item with 40x40 icon") },
                    icon = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                )
                Divider()
                ListItem(
                    text = { Text("One line list item with 56x56 icon") },
                    icon = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(56.dp)
                        )
                    }
                )
                Divider()
                ListItem(
                    text = { Text("One line clickable list item") },
                    icon = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(56.dp)
                        )
                    },
                    modifier = Modifier.clickable { }
                )
                Divider()
                ListItem(
                    text = { Text("One line list item with trailing icon") },
                    trailing = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Localized Description"
                        )
                    }
                )
                Divider()
                ListItem(
                    text = { Text("One line list item") },
                    icon = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                    },
                    trailing = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Localized description"
                        )
                    }
                )
                Divider()

                // ----------------------------------------------------------------
                // Two-line items
                // ----------------------------------------------------------------

                ListItem(
                    text = { Text("Two line list item") },
                    secondaryText = { Text("Secondary text") }
                )
                Divider()
                ListItem(
                    text = { Text("Two line list item") },
                    overlineText = { Text("OVERLINE") }
                )
                Divider()
                ListItem(
                    text = { Text("Two line list item with 24x24 icon") },
                    secondaryText = { Text("Secondary text") },
                    icon = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = null
                        )
                    }
                )
                Divider()
                ListItem(
                    text = { Text("Two line list item with 40x40 icon") },
                    secondaryText = { Text("Secondary text") },
                    icon = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                )
                Divider()
                ListItem(
                    text = { Text("Two line list item with 40x40 icon") },
                    secondaryText = { Text("Secondary text") },
                    trailing = { Text("meta") },
                    icon = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                )
                Divider()

                // ----------------------------------------------------------------
                // Three-line items
                // ----------------------------------------------------------------

                ListItem(
                    text = { Text("Three line list item") },
                    secondaryText = {
                        Text(
                            "This is a long secondary text for the current list item, " +
                                "displayed on two lines"
                        )
                    },
                    singleLineSecondaryText = false,
                    trailing = { Text("meta") }
                )
                Divider()
                ListItem(
                    text = { Text("Three line list item") },
                    overlineText = { Text("OVERLINE") },
                    secondaryText = { Text("Secondary text") }
                )
                Divider()
                ListItem(
                    text = { Text("Three line list item with 24x24 icon") },
                    secondaryText = {
                        Text(
                            "This is a long secondary text for the current list item " +
                                "displayed on two lines"
                        )
                    },
                    singleLineSecondaryText = false,
                    icon = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = null
                        )
                    }
                )
                Divider()
                ListItem(
                    text = { Text("Three line list item with trailing icon") },
                    secondaryText = {
                        Text(
                            "This is a long secondary text for the current list" +
                                " item, displayed on two lines"
                        )
                    },
                    singleLineSecondaryText = false,
                    trailing = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Localized description"
                        )
                    }
                )
                Divider()
                ListItem(
                    text = { Text("Three line list item") },
                    overlineText = { Text("OVERLINE") },
                    secondaryText = { Text("Secondary text") },
                    trailing = { Text("meta") }
                )
                Divider()

                // ----------------------------------------------------------------
                // You can combine this component with a checkbox or switch as in
                // the following examples:
                // ----------------------------------------------------------------

                var switched by remember { mutableStateOf(false) }
                val onSwitchedChange: (Boolean) -> Unit = { switched = it }
                ListItem(
                    text = { Text("Switch ListItem") },
                    trailing = {
                        Switch(
                            checked = switched,
                            onCheckedChange = null // null recommended for accessibility with screenreaders
                        )
                    },
                    modifier = Modifier.toggleable(
                        value = switched,
                        onValueChange = onSwitchedChange
                    )
                )
                Divider()
                var checked by remember { mutableStateOf(true) }
                val onCheckedChange: (Boolean) -> Unit = { checked = it }
                ListItem(
                    text = { Text("Checkbox ListItem") },
                    trailing = {
                        Checkbox(
                            checked = checked,
                            onCheckedChange = null // null recommended for accessibility with screenreaders
                        )
                    },
                    modifier = Modifier.toggleable(
                        value = checked,
                        onValueChange = onCheckedChange
                    )
                )
                Divider()
            }
        }
    }
}
