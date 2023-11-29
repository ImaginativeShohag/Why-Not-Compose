package org.imaginativeworld.whynotcompose.cms.datasource.cache

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import org.imaginativeworld.whynotcompose.base.extensions.getJsonFromObj
import org.imaginativeworld.whynotcompose.base.extensions.getObjFromJson

@Singleton
class StorageCacheDataSource @Inject constructor(
    @ApplicationContext context: Context
) {
    private val context: Context = context.applicationContext

    @Volatile
    private var sharedPref: SharedPreferences? = null

    @PublishedApi
    internal fun getSharedPerf(): SharedPreferences {
        return sharedPref ?: synchronized(this) {
            context.getSharedPreferences(
                "${context.packageName}.storage.cache",
                Context.MODE_PRIVATE
            )
        }
    }

    inline fun <reified T> get(forKey: StorageCacheKey): T? {
        val data = getSharedPerf().getString(forKey.name, null) ?: return null

        return data.getObjFromJson<T>(false)
    }

    inline fun <reified T> set(forKey: StorageCacheKey, value: T?) {
        getSharedPerf()
            .edit()
            .apply {
                if (value == null) {
                    remove(forKey.name)
                } else {
                    putString(forKey.name, value.getJsonFromObj<T>(false))
                }
                apply()
            }
    }

    fun reset() {
        getSharedPerf().edit().clear().apply()
    }
}
