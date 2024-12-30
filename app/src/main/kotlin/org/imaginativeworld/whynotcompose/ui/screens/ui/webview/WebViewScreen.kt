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

package org.imaginativeworld.whynotcompose.ui.screens.ui.webview

import android.view.ViewGroup
import android.webkit.WebView
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.common.compose.R as CommonComposeR
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent.Header
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.common.compose.theme.TailwindCSSColor
import org.imaginativeworld.whynotcompose.ui.screens.ui.webview.components.ErrorView
import timber.log.Timber

// TODO: Swipe to refresh
// TODO: Error/message pages

sealed class WebViewTarget(val name: String, val url: String) {
    data object AboutMe : WebViewTarget(
        name = "About Me",
        url = "https://imaginativeshohag.github.io"
    )

    data object SourceCode : WebViewTarget(
        name = "Source Code",
        url = "https://github.com/ImaginativeShohag/Why-Not-Compose"
    )
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun WebViewScreen(
    viewModel: WebViewViewModel,
    target: WebViewTarget,
    goBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    BackHandler {
        if (viewModel.webViewCanGoBack()) {
            viewModel.webViewGoBack()
        } else {
            goBack()
        }
    }

    WebViewSkeleton(
        title = target.name,
        goBack = {
            goBack()
        },
        webView = { modifier ->
            WebViewContainer(
                modifier = modifier,
                url = target.url,
                loadingProgress = state.loadingProgress,
                initSwipeRefresh = viewModel::initSwipeRefresh,
                initWebView = viewModel::initWebView
            )
        },
        webViewError = state.error,
        onRetry = viewModel::webViewReload
    )
}

@PreviewLightDark
@Composable
private fun WebViewSkeletonPreview() {
    AppTheme {
        WebViewSkeleton(
            title = WebViewTarget.AboutMe.name,
            goBack = {},
            webView = {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(TailwindCSSColor.Yellow500)
                )
            }
        )
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun WebViewSkeleton(
    title: String,
    goBack: () -> Unit,
    webView: @Composable (Modifier) -> Unit,
    webViewError: WebViewError? = null,
    onRetry: () -> Unit = {}
) {
    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            Header(
                title,
                goBack = goBack
            )
        }
    ) { innerPadding ->
        Box(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            webView(Modifier.fillMaxSize())

            AnimatedVisibility(
                visible = webViewError != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                webViewError?.let {
                    ErrorView(
                        errorCode = webViewError.errorCode,
                        description = webViewError.description,
                        failingUrl = webViewError.failingUrl,
                        onRetry = onRetry
                    )
                }
            }
        }
    }
}

@Composable
private fun WebViewContainer(
    url: String,
    loadingProgress: Int?,
    initSwipeRefresh: (swipeRefreshLayout: SwipeRefreshLayout) -> Unit,
    initWebView: (webView: WebView) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(Modifier.fillMaxSize()) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    Timber.e("AndroidView: factory")

                    SwipeRefreshLayout(context).apply {
                        initSwipeRefresh(this)

                        addView(
                            WebView(context).apply {
                                id = CommonComposeR.id.webView

                                initWebView(this)
                            }
                        )
                    }
                },
                update = { swipeRefreshLayout ->
                    Timber.e("AndroidView: update")

                    val webView =
                        swipeRefreshLayout.findViewById<WebView>(CommonComposeR.id.webView)

                    swipeRefreshLayout.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    webView.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    webView.loadUrl(url)
                }
            )

            LoadingContainer(
                progress = loadingProgress ?: 0,
                visible = loadingProgress != null
            )
        }
    }
}

@Preview
@Composable
private fun LoadingContainerPreview() {
    val scope = rememberCoroutineScope()
    val progress = remember { mutableIntStateOf(0) }

    LaunchedEffect(true) {
        scope.launch {
            while (true) {
                progress.intValue = 0

                delay(1000)

                progress.intValue = 33

                delay(1000)

                progress.intValue = 66

                delay(1000)

                progress.intValue = 100

                delay(1000)
            }
        }
    }

    LoadingContainer(
        progress.intValue
    )
}

@Composable
private fun LoadingContainer(
    progress: Int,
    visible: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Loading")
    val color by infiniteTransition.animateColor(
        initialValue = TailwindCSSColor.Green500,
        targetValue = TailwindCSSColor.Green700,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Color"
    )

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black.copy(.25f))
        ) {
            Row(
                Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .height(24.dp)
                    .padding(start = 32.dp, end = 32.dp)
                    .background(Color(0xffdddddd), CircleShape)
            ) {
                BoxWithConstraints {
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .clip(CircleShape)
                            .animateContentSize()
                            .width(this.maxWidth * progress / 100)
                            .background(color, CircleShape)
                    )
                }
            }
        }
    }
}
