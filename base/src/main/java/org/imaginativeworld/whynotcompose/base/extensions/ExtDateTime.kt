package org.imaginativeworld.whynotcompose.base.extensions

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Return date in yyyy-MM-dd format.
 *
 * Note: Month is 1 to 12.
 */
fun Date?.getYYYYMMDD(): String {
    if (this == null) {
        return ""
    }

    return try {
        return DateFormat.format("yyyy-MM-dd", this).toString()
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

fun Date?.getHumanReadableDate(): String {
    if (this == null) {
        return ""
    }

    val simpleDateFormat = SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH)
    return try {
        simpleDateFormat.format(this)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

fun Date?.getHumanReadableDateTime(): String {
    if (this == null) {
        return ""
    }

    val simpleDateFormat = SimpleDateFormat("EEE, d MMM yyyy, hh:mm a", Locale.ENGLISH)
    return try {
        simpleDateFormat.format(this)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}
