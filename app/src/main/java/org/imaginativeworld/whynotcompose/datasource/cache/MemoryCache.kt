package org.imaginativeworld.whynotcompose.datasource.cache

import org.imaginativeworld.whynotcompose.base.extensions.getJsonFromObj
import org.imaginativeworld.whynotcompose.base.extensions.getObjFromJson

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
