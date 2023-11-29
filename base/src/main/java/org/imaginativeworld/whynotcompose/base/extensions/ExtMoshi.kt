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

package org.imaginativeworld.whynotcompose.base.extensions

import com.squareup.moshi.Moshi
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.Date
import org.imaginativeworld.whynotcompose.base.network.jsonadapter.DateJsonAdapter
import timber.log.Timber

object MoshiUtil {
    fun getMoshi(): Moshi {
        return Moshi.Builder()
            .add(Date::class.java, DateJsonAdapter())
            .build()
    }
}

/**
 * Note for Navigation Component for Compose:
 * If the data has special character (eg., + etc.), the url encode-decode will not work properly.
 * For example, the "+" will be replaced with a space on decode.
 * Because Navigation Component auto convert "%2B" with "+",
 * so on decode it replace "+" by space.
 */
inline fun <reified T> String?.getObjFromJson(urlDecode: Boolean = true): T? {
    if (this == null) return null

    Timber.e("getObjFromJson: $this")

    val jsonAdapter = MoshiUtil.getMoshi().adapter(T::class.java).lenient()

    val result = jsonAdapter.fromJson(
        if (urlDecode) {
            this.urlDecode()
        } else {
            this
        }
    )

    Timber.e("getObjFromJson (after processing): $result")

    return result
}

/**
 * Note for Navigation Component for Compose:
 * If the data has special character (eg., + etc.), the url encode-decode will not work properly.
 * For example, the "+" will be replaced with a space on decode.
 * Because Navigation Component auto convert "%2B" with "+",
 * so on decode it replace "+" by space.
 */
inline fun <reified T> T?.getJsonFromObj(urlEncode: Boolean = true): String? {
    if (this == null) return null

    Timber.e("getJsonFromObj: $this")

    val jsonAdapter = MoshiUtil.getMoshi().adapter(T::class.java).lenient()

    return jsonAdapter.toJson(this).let { json ->
        val result = if (urlEncode) {
            json.urlEncode()
        } else {
            json
        }

        Timber.e("getJsonFromObj (after processing): $result")

        result
    }
}

fun String.urlEncode(): String {
    return URLEncoder.encode(this, "utf-8")
}

fun String.urlDecode(): String {
    return URLDecoder.decode(this, "utf-8")
}
