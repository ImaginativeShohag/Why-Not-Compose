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

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.LinkInteractionListener
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.imaginativeworld.whynotcompose.base.extensions.toast
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppFont
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

// Source:
// https://developer.android.com/jetpack/compose/text
// https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/foundation/foundation/integration-tests/foundation-demos/src/main/java/androidx/compose/foundation/demos/text/Hyperlinks.kt
// https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/ui/ui-text/samples/src/main/java/androidx/compose/ui/text/samples/AnnotatedStringBuilderSamples.kt

private const val WEB_LINK = "https://github.com/ImaginativeShohag"
private const val LONG_WEB_LINK =
    "https://github.com/ImaginativeShohag/Why-Not-Compose/blob/main/README.md"
private const val PHONE_URI = "tel:+123456789"

@Composable
fun TextScreen(
    goBack: () -> Unit
) {
    TextScreenSkeleton(
        goBack = goBack
    )
}

@PreviewLightDark
@Composable
private fun TextScreenSkeletonPreview() {
    AppTheme {
        TextScreenSkeleton()
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun TextScreenSkeleton(
    goBack: () -> Unit = {}
) {
    val context = LocalContext.current

    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            AppComponent.Header(
                "Text",
                goBack = goBack
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            Text(
                text = "This is a normal text.",
                color = MaterialTheme.colorScheme.onBackground,
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
                            color = MaterialTheme.colorScheme.primary
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
                pushStyle(SpanStyle(color = MaterialTheme.colorScheme.primary))
                // append new text, this text will be rendered as green
                append(" World")
                // pop the green style
                pop()
                // append a string without style
                append("!")
                // then style the last added word as red, exclamation mark will be red
                addStyle(
                    SpanStyle(color = MaterialTheme.colorScheme.secondary),
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
                style = MaterialTheme.typography.bodyLarge,
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
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("here")
                }

                pop()
            }

            ClickableText(
                text = annotatedText,
                style = MaterialTheme.typography.bodyLarge,
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

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            Hyperlinks()

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.BigSpacer()
        }
    }
}

@Composable
private fun Sample(title: String, content: @Composable () -> Unit) {
    Column(Modifier.fillMaxWidth().border(2.dp, Color.Black).padding(8.dp)) {
        Text(title, Modifier.align(Alignment.CenterHorizontally), fontWeight = FontWeight.Bold)
        content()
    }
}

@Composable
private fun Hyperlinks() {
    Column(
        modifier =
        Modifier.wrapContentWidth(Alignment.CenterHorizontally)
            .widthIn(max = 400.dp)
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Sample("State-based styling through builder") {
            Text(
                buildAnnotatedString {
                    append("Text and a ")
                    withLink(
                        LinkAnnotation.Url(
                            "https://developer.android.com",
                            TextLinkStyles(
                                style = SpanStyle(color = Color.Magenta),
                                focusedStyle =
                                SpanStyle(background = Color.Yellow.copy(alpha = 0.3f)),
                                hoveredStyle = SpanStyle(textDecoration = TextDecoration.Underline),
                                pressedStyle = SpanStyle(color = Color.Red)
                            )
                        )
                    ) {
                        append("DEVELOPER ANDROID COM LINK")
                    }
                    append(" immediately following.")
                }
            )
        }
        Sample("Link custom styling") {
            val string = buildAnnotatedString {
                withStyle(SpanStyle(Color.Green)) {
                    append("Text. ")
                    withLink(
                        LinkAnnotation.Url(
                            "tr",
                            styles =
                            TextLinkStyles(
                                SpanStyle(Color.Blue, textDecoration = TextDecoration.Underline)
                            )
                        )
                    ) {
                        append("VERY")
                        withStyle(SpanStyle(Color.Cyan)) {
                            append("LO")
                            withStyle(SpanStyle(Color.Magenta)) { append("NG") }
                        }
                        append("LINK.")
                    }
                    append(" Text.")
                }
            }
            Text(string)
        }
        Sample("State-based styling from Html-tagged string") {
            val htmlString =
                """
                This is a <span style="color:red"><a href="https://developer.android.com">link</a></span> here.
                Another <a href="https://developer.android.com">link</a> follows.
            """
                    .trimIndent()
            val annotatedString = AnnotatedString.fromHtml(htmlString)
            Text(annotatedString)
        }
        Sample("Single link styling with SpanStyle") {
            val stringWithLink = buildAnnotatedString {
                append("Example with a custom style ")
                withStyle(SpanStyle(fontSize = 26.sp)) {
                    withLink(LinkAnnotation.Url(WEB_LINK)) { append("developer.android.com") }
                }
                append(" link and a phone number ")
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    withLink(LinkAnnotation.Url(PHONE_URI)) { append("+1 (234) 567890") }
                }
                append(" with a custom style.")
            }
            Text(text = stringWithLink)
        }
        Sample("Material colors for links from builder") {
            Text(
                buildAnnotatedString {
                    append("Text and ")
                    withLink(LinkAnnotation.Url(WEB_LINK)) { append("developer.android.com") }
                    append(" link.")
                }
            )
        }
        Sample("Material colors for links from html") {
            val htmlString =
                "Text and <a href=\"https://developer.android.com\">developer.android.com</a> link"
            Text(AnnotatedString.fromHtml(htmlString))
        }
        Sample("Long links") {
            val text = buildAnnotatedString {
                append("Example that contains ")
                withLink(LinkAnnotation.Url(LONG_WEB_LINK)) {
                    append("a very very very very very very long long long long link")
                }
                append(" followed by another long link ")
                withLink(LinkAnnotation.Url(LONG_WEB_LINK)) { append(LONG_WEB_LINK) }
            }
            Text(text)
        }
        Sample("Links with overlapped bounds") {
            val text = buildAnnotatedString {
                withLink(LinkAnnotation.Url(LONG_WEB_LINK)) { append("The first link") }
                append(" immediately followed by ")
                withLink(LinkAnnotation.Url(LONG_WEB_LINK)) { append("the second quite long link") }
                append(" so their bounds are overlapped")
            }
            Text(text)
        }
        Sample("Link inside clickable text") {
            Text(
                buildAnnotatedString {
                    append("Clickable text with a ")
                    withLink(LinkAnnotation.Url(WEB_LINK)) { append("developer.android.com") }
                    append(" link.")
                },
                Modifier.clickable {}
            )
        }
        Sample("Link inside selectable text") {
            SelectionContainer {
                Text(
                    buildAnnotatedString {
                        append("Selectable text with a ")
                        withLink(LinkAnnotation.Url(WEB_LINK)) { append("developer.android.com") }
                        append(" link.")
                    }
                )
            }
        }
        Sample("Link and inline content in text") {
            val fontSize = 20.sp
            val inlineTextContent =
                InlineTextContent(
                    placeholder = Placeholder(fontSize, fontSize, PlaceholderVerticalAlign.Center)
                ) {
                    Box(modifier = Modifier.fillMaxSize().background(Color.Green))
                }
            Text(
                buildAnnotatedString {
                    append("A ")
                    appendInlineContent("box")
                    append(" inline content and a ")
                    withLink(LinkAnnotation.Url(WEB_LINK)) { append("developer.android.com") }
                    append(" link.")
                },
                inlineContent = mapOf("box" to inlineTextContent)
            )
        }
        Sample("Invalid link not opened") {
            Text(
                buildAnnotatedString {
                    append("Attached ")
                    withLink(LinkAnnotation.Url("asdf")) {
                        withStyle(SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                            append("link")
                        }
                    }
                    append(" is invalid and won't be opened.")
                },
                color = Color.Red
            )
        }
        Sample("Samples") {
            AnnotatedStringWithLinkSample()
            AnnotatedStringWithHoveredLinkStylingSample()
            AnnotatedStringWithListenerSample()
        }
        Sample("Link with listener - accessibility") {
            val localUriHandler = LocalUriHandler.current
            val listener = LinkInteractionListener { localUriHandler.openUri(WEB_LINK) }
            val text = buildAnnotatedString {
                append("Click ")
                withLink(LinkAnnotation.Clickable("tag", linkInteractionListener = listener)) {
                    append("the first link")
                }
                append(" or ")
                withLink(LinkAnnotation.Url(WEB_LINK, linkInteractionListener = listener)) {
                    append("the second link")
                }
            }
            Text(text)
        }
    }
}

@Composable
private fun AnnotatedStringWithLinkSample() {
    // Display a link in the text
    Text(
        buildAnnotatedString {
            append("Build better apps faster with ")
            withLink(
                LinkAnnotation.Url(
                    "https://developer.android.com/jetpack/compose",
                    TextLinkStyles(style = SpanStyle(color = Color.Blue))
                )
            ) {
                append("Jetpack Compose")
            }
        }
    )
}

@Composable
private fun AnnotatedStringWithHoveredLinkStylingSample() {
    // Display a link in the text that gets an underline when hovered
    Text(
        buildAnnotatedString {
            append("Build better apps faster with ")
            val link =
                LinkAnnotation.Url(
                    "https://developer.android.com/jetpack/compose",
                    TextLinkStyles(
                        style = SpanStyle(color = Color.Blue),
                        hoveredStyle = SpanStyle(textDecoration = TextDecoration.Underline)
                    )
                )
            withLink(link) { append("Jetpack Compose") }
        }
    )
}

@Composable
private fun AnnotatedStringWithListenerSample() {
    // Display a link in the text and log metrics whenever user clicks on it. In that case we handle
    // the link using openUri method of the LocalUriHandler
    val uriHandler = LocalUriHandler.current
    Text(
        buildAnnotatedString {
            append("Build better apps faster with ")
            val link =
                LinkAnnotation.Url(
                    "https://developer.android.com/jetpack/compose",
                    TextLinkStyles(SpanStyle(color = Color.Blue))
                ) {
                    val url = (it as LinkAnnotation.Url).url
                    // log some metrics
                    uriHandler.openUri(url)
                }
            withLink(link) { append("Jetpack Compose") }
        }
    )
}
