package org.imaginativeworld.whynotcompose.base.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import org.imaginativeworld.whynotcompose.base.datasource.memorycache.MemoryCache
import org.imaginativeworld.whynotcompose.base.datasource.memorycache.MemoryCacheKeyForNavController
import timber.log.Timber

/**
 * Set results to receive on previous back stack.
 *
 * @param results results to pass on previous back stack
 */
fun NavController.popBackStackWithResult(vararg results: Pair<String, Any>) {
    for (result in results) {
        Timber.e("key:value = ${result.first}:${result.second}")
        MemoryCache.set(
            MemoryCacheKeyForNavController.Result(result.first),
            result.second
        )
    }

    popBackStack()
}

/**
 * Receive result from another view.
 *
 * @param key the key of the result
 * @return the value based on the [key]. If no result found, returns `null`.
 */
@Composable
inline fun <reified T> navResult(key: String): T? = remember {
    val cacheKey = MemoryCacheKeyForNavController.Result(key)
    val value: T? = MemoryCache.get(cacheKey)
    MemoryCache.remove(cacheKey)
    Timber.e("key:value = $key:$value")
    return@remember value
}
