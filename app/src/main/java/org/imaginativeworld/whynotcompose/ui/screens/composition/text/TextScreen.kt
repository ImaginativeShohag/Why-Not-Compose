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

package org.imaginativeworld.whynotcompose.ui.screens.composition.text

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.imaginativeworld.whynotcompose.base.extensions.toast
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppFont
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

// Help: https://developer.android.com/jetpack/compose/text

@Composable
fun TextScreen(
    goBack: () -> Unit
) {
    TextScreenSkeleton(
        goBack = goBack
    )
}

@Preview
@Composable
fun TextScreenSkeletonPreview() {
    AppTheme {
        TextScreenSkeleton()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TextScreenSkeletonPreviewDark() {
    AppTheme {
        TextScreenSkeleton()
    }
}

@Composable
fun TextScreenSkeleton(
    goBack: () -> Unit = {}
) {
    val context = LocalContext.current

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
                .verticalScroll(rememberScrollState())
        ) {
            AppComponent.Header(
                "Text",
                goBack = goBack
            )

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()

                Text(
                    text = "This is a normal text.",
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = AppFont.TitilliumWeb
                    // etc.
                )

                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()

                Text(
                    buildAnnotatedString {
                        append("This is an ")

                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colors.primary
                            )
                        ) {
                            append("annotated")
                        }

                        append(" text.")
                    }
                )

                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()

                val annotatedString = with(AnnotatedString.Builder("Hello")) {
                    // push green text style so that any appended text will be green
                    pushStyle(SpanStyle(color = MaterialTheme.colors.primary))
                    // append new text, this text will be rendered as green
                    append(" World")
                    // pop the green style
                    pop()
                    // append a string without style
                    append("!")
                    // then style the last added word as red, exclamation mark will be red
                    addStyle(
                        SpanStyle(color = MaterialTheme.colors.secondary),
                        "Hello World".length,
                        this.length
                    )

                    toAnnotatedString()
                }

                Text(annotatedString)

                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()

                val text =
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                        "incididunt ut labore et dolore magna aliqua.\nUt enim ad minim veniam, quis " +
                        "nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."

                val paragraphStyle1 = ParagraphStyle(
                    textIndent = TextIndent(firstLine = 14.sp)
                )
                val paragraphStyle2 = ParagraphStyle(
                    lineHeight = 30.sp
                )

                Text(
                    text = buildAnnotatedString {
                        append(text)
                        addStyle(paragraphStyle1, 0, text.indexOf('\n') + 1)
                        addStyle(paragraphStyle2, text.indexOf('\n') + 1, text.length)
                    }
                )

                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()

                SelectionContainer {
                    Text("This text is selectable")
                }

                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()

                SelectionContainer {
                    Column {
                        Text("This text is selectable")
                        Text("This one too")
                        Text("This one as well")
                        DisableSelection {
                            Text("But not this one")
                            Text("Neither this one")
                        }
                        Text("But again, you can select this one")
                        Text("And this one too")
                    }
                }

                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()

                ClickableText(
                    text = AnnotatedString("Click Me"),
                    style = MaterialTheme.typography.body1,
                    onClick = { offset ->
                        context.toast("$offset -th character is clicked.")
                    }
                )

                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()

                val annotatedText = buildAnnotatedString {
                    append("Click ")

                    // We attach this *URL* annotation to the following content
                    // until `pop()` is called
                    pushStringAnnotation(
                        tag = "URL",
                        annotation = "https://developer.android.com"
                    )
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("here")
                    }

                    pop()
                }

                ClickableText(
                    text = annotatedText,
                    style = MaterialTheme.typography.body1,
                    onClick = { offset ->
                        // We check if there is an *URL* annotation attached to the text
                        // at the clicked position
                        annotatedText.getStringAnnotations(
                            tag = "URL",
                            start = offset,
                            end = offset
                        )
                            .firstOrNull()?.let { annotation ->
                                // If yes, we log its value
                                context.toast("Clicked URL: " + annotation.item)
                            }
                    }
                )
            }

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.BigSpacer()
        }
    }
}
