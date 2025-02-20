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

package org.imaginativeworld.whynotcompose.ui.screens.tutorial.index

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent.Header
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.ui.screens.TutorialsScreen
import org.imaginativeworld.whynotcompose.utils.LiteMarkdown

@Composable
fun TutorialIndexScreen(
    goBack: () -> Unit,
    navigate: (TutorialsScreen) -> Unit
) {
    TutorialIndexSkeleton(
        goBack = goBack,
        navigate = navigate
    )
}

@PreviewLightDark
@Composable
private fun TutorialIndexSkeletonPreview() {
    AppTheme {
        TutorialIndexSkeleton()
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun TutorialIndexSkeleton(
    goBack: () -> Unit = {},
    navigate: (TutorialsScreen) -> Unit = {}
) {
    Scaffold(
        Modifier
            .testTag("screen:tutorials:index")
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            Header(
                "Tutorials",
                goBack = goBack
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn(
                Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                itemsIndexed(Tutorial.tutorialList) { index, item ->
                    if (index != 0) {
                        HorizontalDivider(Modifier.padding(16.dp, 0.dp))
                    }

                    MenuItem(
                        item = item,
                        onClick = {
                            navigate(item.route)
                        },
                        modifier = Modifier
                            .testTag("list-item")
                    )
                }
            }
        }
    }
}

@Composable
private fun MenuItem(
    item: Tutorial,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .clickable {
                onClick()
            }
            .padding(
                start = 16.dp,
                top = 8.dp,
                end = 16.dp,
                bottom = 8.dp
            )
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .testTag(item.testTag)
                .fillMaxWidth(),
            text = buildAnnotatedString {
                append(item.name)
                append(" ")

                withStyle(
                    SpanStyle(
                        color = item.level.color,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                ) {
                    append(item.level.name.uppercase())
                }
            }
        )

        if (item.description != null) {
            Text(
                modifier = Modifier.padding(
                    top = 4.dp
                ),
                text = buildAnnotatedString {
                    for (section in LiteMarkdown.getSections(item.description)) {
                        if (section.second) {
                            withStyle(
                                SpanStyle(
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 11.sp,
                                    background = MaterialTheme.colorScheme.onBackground.copy(
                                        .05f
                                    )
                                )
                            ) {
                                append(section.first)
                            }
                        } else {
                            append(section.first)
                        }
                    }
                },
                fontSize = 12.sp
            )
        }
    }
}
