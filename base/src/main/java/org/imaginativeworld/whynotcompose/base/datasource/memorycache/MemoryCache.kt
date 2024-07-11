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

package org.imaginativeworld.whynotcompose.base.datasource.memorycache

import androidx.annotation.NonNull

/**
 * Memory cache.
 */
object MemoryCache {
    var cache = mutableMapOf<String, Any>()
        private set

    inline fun <reified T> get(forKey: MemoryCacheKey): T? = cache[forKey.name] as? T

    inline fun <reified T> set(forKey: MemoryCacheKey, @NonNull value: T) {
        if (value != null) {
            cache[forKey.name] = value
        }
    }

    fun remove(key: MemoryCacheKey) {
        cache.remove(key.name)
    }

    fun reset() {
        cache.clear()
    }
}
