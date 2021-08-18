package org.imaginativeworld.whynotcompose.ui.screens.ui.webview

import android.webkit.WebView
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor() : ViewModel() {

    private var webView: WebView? = null

    fun initWebView(webView: WebView) {
        Timber.e("initWebView")
        this.webView = webView
    }

    fun webViewCanGoBack(): Boolean {
        webView?.let {
            Timber.e("webViewCanGoBack")
            return it.canGoBack()
        }
        return false
    }

    fun webViewGoBack() {
        Timber.e("webViewGoBack")
        webView?.goBack()
    }

}