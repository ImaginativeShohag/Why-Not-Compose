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

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(WebViewState())
    val state: StateFlow<WebViewState>
        get() = _state

    // ----------------------------------------------------------------

    private var _swipeRefreshLayout: WeakReference<SwipeRefreshLayout>? = null

    private var _webView: WeakReference<WebView>? = null

    // ----------------------------------------------------------------

    fun initSwipeRefresh(swipeRefreshLayout: SwipeRefreshLayout) {
        Timber.e("initSwipeRefresh")

        this._swipeRefreshLayout = WeakReference(swipeRefreshLayout)

        swipeRefreshLayout.setOnRefreshListener {
            _webView?.get()?.reload()
        }
    }

    // ----------------------------------------------------------------

    @SuppressLint("SetJavaScriptEnabled")
    fun initWebView(webView: WebView) {
        Timber.e("initWebView")

        this._webView = WeakReference(webView)

        webView.apply {

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
                    _state.value = _state.value.copy(
                        loadingProgress = 0
                    )

                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    _state.value = _state.value.copy(
                        loadingProgress = null
                    )

                    _swipeRefreshLayout?.get()?.isRefreshing = false

                    super.onPageFinished(view, url)
                }
            }

            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(
                    view: WebView?,
                    newProgress: Int
                ) {
                    _state.value = _state.value.copy(
                        loadingProgress = newProgress
                    )
                }
            }

        }
    }

    fun webViewCanGoBack(): Boolean {
        _webView?.get()?.let {
            Timber.e("webViewCanGoBack")

            return it.canGoBack()
        }
        return false
    }

    fun webViewGoBack() {
        Timber.e("webViewGoBack")

        _webView?.get()?.goBack()
    }
}

data class WebViewState(
    val loadingProgress: Int? = null,
)