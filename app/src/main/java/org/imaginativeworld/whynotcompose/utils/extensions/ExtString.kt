package org.imaginativeworld.whynotcompose.utils.extensions

import android.icu.text.MessageFormat
import android.os.Build
import java.util.Locale

fun Int.toWords(language: String = "en", country: String = "US"): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        val formatter = MessageFormat(
            "{0,spellout,currency}",
            Locale(language, country)
        )
        formatter.format(arrayOf(this))
    } else {
        this.toString()
    }
}
