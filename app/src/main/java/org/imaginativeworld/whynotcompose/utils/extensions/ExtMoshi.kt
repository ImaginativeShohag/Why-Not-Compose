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

package org.imaginativeworld.whynotcompose.utils.extensions

import com.squareup.moshi.Moshi
import org.imaginativeworld.whynotcompose.network.ApiClient
import timber.log.Timber
import java.net.URLEncoder
import java.util.Date

object MoshiUtil {
    fun getMoshi(): Moshi {
        return Moshi.Builder()
            .add(Date::class.java, ApiClient.DateJsonAdapter())
            .build()
    }
}

inline fun <reified T> String?.getObjFromJson(): T? {
    if (this == null) return null

    Timber.e("getObjFromJson: $this")

    val jsonAdapter = MoshiUtil.getMoshi().adapter(T::class.java).lenient()

    return jsonAdapter.fromJson(this)
}

inline fun <reified T> T?.getJsonFromObj(): String? {
    if (this == null) return null

    Timber.e("getJsonFromObj: $this")

    val jsonAdapter = MoshiUtil.getMoshi().adapter(T::class.java).lenient()

    return jsonAdapter.toJson(this).urlEncode()
}

fun String.urlEncode(): String {
    return URLEncoder.encode(this, "utf-8")
}
