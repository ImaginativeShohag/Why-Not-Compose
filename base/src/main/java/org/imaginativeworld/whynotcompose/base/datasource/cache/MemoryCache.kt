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

package org.imaginativeworld.whynotcompose.base.datasource.cache

import org.imaginativeworld.whynotcompose.base.extensions.getJsonFromObj
import org.imaginativeworld.whynotcompose.base.extensions.getObjFromJson

/**
 * Memory cache.
 */
object MemoryCache {
    var cache = mutableMapOf<String, String>()
        private set

    inline fun <reified T> get(forKey: MemoryCacheKey): T? {
        val data = cache[forKey.name] ?: return null

        return data.getObjFromJson<T>(false)
    }

    inline fun <reified T> set(forKey: MemoryCacheKey, value: T?) {
        if (value == null) {
            cache.remove(forKey.name)
        } else {
            value.getJsonFromObj<T>(false)?.let { jsonizedValue ->
                cache[forKey.name] = jsonizedValue
            }
        }
    }

    fun reset() {
        cache.clear()
    }
}
