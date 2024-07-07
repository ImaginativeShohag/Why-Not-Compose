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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
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

@PreviewLightDark
@Composable
private fun ListItemScreenSkeletonPreview() {
    AppTheme {
        ListItemScreenSkeleton()
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun ListItemScreenSkeleton(
    goBack: () -> Unit = {}
) {
    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            AppComponent.Header(
                "ListItem",
                goBack = goBack
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
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
                ListItem(headlineContent = { Text("One line list item with no icon") })
                HorizontalDivider()
                ListItem(
                    headlineContent = { Text("One line list item with 24x24 icon") },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Localized description"
                        )
                    }
                )
                HorizontalDivider()
                ListItem(
                    headlineContent = { Text("One line list item with 40x40 icon") },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                )
                HorizontalDivider()
                ListItem(
                    headlineContent = { Text("One line list item with 56x56 icon") },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(56.dp)
                        )
                    }
                )
                HorizontalDivider()
                ListItem(
                    headlineContent = { Text("One line clickable list item") },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(56.dp)
                        )
                    },
                    modifier = Modifier.clickable { }
                )
                HorizontalDivider()
                ListItem(
                    headlineContent = { Text("One line list item with trailing icon") },
                    trailingContent = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Localized Description"
                        )
                    }
                )
                HorizontalDivider()
                ListItem(
                    headlineContent = { Text("One line list item") },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                    },
                    trailingContent = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Localized description"
                        )
                    }
                )
                HorizontalDivider()

                // ----------------------------------------------------------------
                // Two-line items
                // ----------------------------------------------------------------

                ListItem(
                    headlineContent = { Text("Two line list item") },
                    supportingContent = { Text("Secondary text") }
                )
                HorizontalDivider()
                ListItem(
                    headlineContent = { Text("Two line list item") },
                    overlineContent = { Text("OVERLINE") }
                )
                HorizontalDivider()
                ListItem(
                    headlineContent = { Text("Two line list item with 24x24 icon") },
                    supportingContent = { Text("Secondary text") },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = null
                        )
                    }
                )
                HorizontalDivider()
                ListItem(
                    headlineContent = { Text("Two line list item with 40x40 icon") },
                    supportingContent = { Text("Secondary text") },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                )
                HorizontalDivider()
                ListItem(
                    headlineContent = { Text("Two line list item with 40x40 icon") },
                    supportingContent = { Text("Secondary text") },
                    trailingContent = { Text("meta") },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                )
                HorizontalDivider()

                // ----------------------------------------------------------------
                // Three-line items
                // ----------------------------------------------------------------

                ListItem(
                    headlineContent = { Text("Three line list item") },
                    supportingContent = {
                        Text(
                            "This is a long secondary text for the current list item, " +
                                "displayed on two lines"
                        )
                    },
                    trailingContent = { Text("meta") }
                )
                HorizontalDivider()
                ListItem(
                    headlineContent = { Text("Three line list item") },
                    overlineContent = { Text("OVERLINE") },
                    supportingContent = { Text("Secondary text") }
                )
                HorizontalDivider()
                ListItem(
                    headlineContent = { Text("Three line list item with 24x24 icon") },
                    supportingContent = {
                        Text(
                            "This is a long secondary text for the current list item " +
                                "displayed on two lines"
                        )
                    },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = null
                        )
                    }
                )
                HorizontalDivider()
                ListItem(
                    headlineContent = { Text("Three line list item with trailing icon") },
                    supportingContent = {
                        Text(
                            "This is a long secondary text for the current list" +
                                " item, displayed on two lines"
                        )
                    },
                    trailingContent = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Localized description"
                        )
                    }
                )
                HorizontalDivider()
                ListItem(
                    headlineContent = { Text("Three line list item") },
                    overlineContent = { Text("OVERLINE") },
                    supportingContent = { Text("Secondary text") },
                    trailingContent = { Text("meta") }
                )
                HorizontalDivider()

                // ----------------------------------------------------------------
                // You can combine this component with a checkbox or switch as in
                // the following examples:
                // ----------------------------------------------------------------

                var switched by remember { mutableStateOf(false) }
                val onSwitchedChange: (Boolean) -> Unit = { switched = it }
                ListItem(
                    headlineContent = { Text("Switch ListItem") },
                    trailingContent = {
                        // Note: `onCheckedChange = null` recommended for accessibility with screenreaders.
                        Switch(
                            checked = switched,
                            onCheckedChange = null
                        )
                    },
                    modifier = Modifier.toggleable(
                        value = switched,
                        onValueChange = onSwitchedChange
                    )
                )
                HorizontalDivider()
                var checked by remember { mutableStateOf(true) }
                val onCheckedChange: (Boolean) -> Unit = { checked = it }
                ListItem(
                    headlineContent = { Text("Checkbox ListItem") },
                    trailingContent = {
                        // Note: `onCheckedChange = null` recommended for accessibility with screenreaders.
                        Checkbox(
                            checked = checked,
                            onCheckedChange = null
                        )
                    },
                    modifier = Modifier.toggleable(
                        value = checked,
                        onValueChange = onCheckedChange
                    )
                )
                HorizontalDivider()
            }
        }
    }
}
