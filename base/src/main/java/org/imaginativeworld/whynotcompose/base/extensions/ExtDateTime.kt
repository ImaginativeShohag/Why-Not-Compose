/*
 * Copyright 2023 Md. Mahmudul Hasan Shohag
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
