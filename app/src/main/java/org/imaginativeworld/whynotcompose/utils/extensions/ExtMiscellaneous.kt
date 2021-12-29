package org.imaginativeworld.whynotcompose.utils.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Open given [url] to a browser.
 */
fun Context.openUrl(url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()

        longToast("Cannot open the link!")
    }
}
