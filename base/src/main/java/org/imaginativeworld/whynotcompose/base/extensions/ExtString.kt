package org.imaginativeworld.whynotcompose.base.extensions

import android.util.Patterns
import java.util.*

fun String.toLower() = this.lowercase(Locale.ENGLISH)

fun String.toUpper() = this.uppercase(Locale.ENGLISH)

fun CharSequence?.isValidEmail() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
