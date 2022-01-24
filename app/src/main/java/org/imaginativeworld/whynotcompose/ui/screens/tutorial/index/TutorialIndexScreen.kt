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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent.Header
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.ui.screens.TutorialsScreen
import timber.log.Timber

@Composable
fun TutorialIndexScreen(
    navigate: (TutorialsScreen) -> Unit,
) {
    TutorialIndexSkeleton(
        navigate = navigate
    )
}

@Preview
@Composable
fun TutorialIndexSkeletonPreview() {
    AppTheme {
        TutorialIndexSkeleton()
    }
}

@Composable
fun TutorialIndexSkeleton(
    navigate: (TutorialsScreen) -> Unit = {},
) {

    Scaffold(
        Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding()
    ) {

        Column(Modifier.fillMaxSize()) {
            Header("Tutorials")

            LazyColumn(
                Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 8.dp)
            ) {

                itemsIndexed(Tutorial.tutorialList) { index, item ->

                    if (index != 0) {
                        Divider(Modifier.padding(16.dp, 0.dp))
                    }

                    Column(
                        Modifier
                            .clickable {
                                navigate(item.route)
                            }
                            .padding(
                                start = 16.dp,
                                top = 8.dp,
                                end = 16.dp,
                                bottom = 8.dp,
                            )
                            .fillMaxWidth()
                    ) {

                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = buildAnnotatedString {
                                append(item.name)
                                append(" ")

                                withStyle(
                                    SpanStyle(
                                        color = item.level.color,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                    )
                                ) {
                                    append(item.level.name.uppercase())
                                }
                            }
                        )

                        if (item.description != null) {
                            // val description = remember { getSections(item.description) }
                            Text(
                                modifier = Modifier.padding(
                                    top = 4.dp,
                                ),
                                text = buildAnnotatedString {
                                    for (section in getSections(item.description)) {
                                        if (section.second) {
                                            withStyle(
                                                SpanStyle(
                                                    fontFamily = FontFamily.Monospace,
                                                    fontSize = 11.sp,
                                                    background = MaterialTheme.colors.onBackground.copy(
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
                                fontSize = 12.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Split a [text] into multiple sections.
 * Code sections are separated by pair of backtick (`) characters.
 *
 * Example [text]:
 *
 * > "The quick `brown fox` jumps over the `lazy dog`."
 *
 * Here total section is 5:
 *
 * 1. subText = "The quick ",       isCodeSection = false,
 * 2. subText = "brown fox",        isCodeSection = true
 * 3. subText = " jumps over the ", isCodeSection = false
 * 4. subText = "lazy dog",         isCodeSection = true
 * 5. subText = ".",                isCodeSection = false
 *
 * @return sections from [text] with "is a code section" flag.
 */
private fun getSections(text: String): List<Pair<String, Boolean>> {
    // List of sections with "is code section" flag.
    val tokens = mutableListOf<Pair<String, Boolean>>()

    var currentPart = ""
    var isCode = false

    for (letter in text) {
        if (letter == '`') {
            // End of a code section.
            if (isCode) {
                isCode = false

                if (currentPart.isNotEmpty()) {
                    tokens.add(Pair(currentPart, true))
                }
            }
            // Start of a code section.
            else {
                isCode = true

                if (currentPart.isNotEmpty()) {
                    tokens.add(Pair(currentPart, false))
                }
            }

            currentPart = ""

            continue
        }

        currentPart += letter
    }

    if (currentPart != "") {
        // Inside a code section.
        if (isCode) {
            tokens.add(Pair(currentPart, true))
        }
        // Not a code section.
        else {
            tokens.add(Pair(currentPart, false))
        }
    }

    return tokens
}
