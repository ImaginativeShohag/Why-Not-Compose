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

import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme
import org.imaginativeworld.whynotcompose.ui.theme.TailwindCSSColor
import timber.log.Timber

// TODO: Swipe to refresh

sealed class WebViewTarget(val name: String, val url: String) {
    object AboutMe : WebViewTarget(
        name = "About Me",
        url = "https://imaginativeshohag.github.io"
    )

    object SourceCode : WebViewTarget(
        name = "Source Code",
        url = "https://github.com/ImaginativeShohag/Why-Not-Compose"
    )
}

@Composable
fun WebViewScreen(
    viewModel: WebViewViewModel,
    target: WebViewTarget,
    goBack: () -> Unit,
) {
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
        webView = {
            MapViewContainer(
                url = target.url,
                initWebView = viewModel::initWebView
            )
        },
    )
}

@Preview
@Composable
fun WebViewSkeletonPreview() {
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

@Preview
@Composable
fun WebViewSkeletonPreviewDark() {
    AppTheme(darkTheme = true) {
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

@Composable
fun WebViewSkeleton(
    title: String,
    goBack: () -> Unit,
    webView: @Composable (Modifier) -> Unit,
) {

    Scaffold(
        Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding()
    ) {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = { goBack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_left_single),
                            contentDescription = "Back"
                        )
                    }
                },
            )

            webView(
                Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun MapViewContainer(
    url: String,
    initWebView: (webView: WebView) -> Unit
) {
    val currentProgress = remember { mutableStateOf(0) }
    val loadingVisibility = remember { mutableStateOf(true) }

    Box(Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                Timber.e("AndroidView: WebView: factory")

                WebView(context).apply {
                    initWebView(this)

                    val webSettings = this.settings

                    webSettings.run {
                        javaScriptEnabled = true
                    }

                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(
                            view: WebView?,
                            url: String?,
                            favicon: Bitmap?
                        ) {
                            loadingVisibility.value = true

                            super.onPageStarted(view, url, favicon)
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            loadingVisibility.value = false

                            super.onPageFinished(view, url)
                        }
                    }

                    webChromeClient = object : WebChromeClient() {
                        override fun onProgressChanged(view: WebView?, newProgress: Int) {
                            currentProgress.value = newProgress
                        }
                    }
                }
            },
            update = { view ->
                Timber.e("AndroidView: WebView: update")

                view.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                view.loadUrl(url)
            }
        )

        LoadingContainer(
            progress = currentProgress.value,
            visible = loadingVisibility.value
        )
    }
}

@Preview
@Composable
fun LoadingContainerPreview() {
    val scope = rememberCoroutineScope()
    val progress = remember { mutableStateOf(0) }

    LaunchedEffect(key1 = "key1", block = {
        scope.launch {
            while (true) {
                progress.value = 0

                delay(1000)

                progress.value = 33

                delay(1000)

                progress.value = 66

                delay(1000)

                progress.value = 100

                delay(1000)
            }
        }
    })

    LoadingContainer(
        progress.value
    )
}

@Composable
private fun LoadingContainer(
    progress: Int,
    visible: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition()
    val color by infiniteTransition.animateColor(
        initialValue = Color(0xff999999),
        targetValue = Color(0xff333333),
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
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
                            .width(maxWidth * progress / 100)
                            .background(color, CircleShape)
                    )
                }
            }
        }
    }
}
