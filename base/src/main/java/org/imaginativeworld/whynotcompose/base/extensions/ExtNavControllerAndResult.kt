package org.imaginativeworld.whynotcompose.base.extensions

import androidx.navigation.NavBackStackEntry

/**
 * Set results to receive on previous back stack entry.
 *
 * Example:
 *
 * Let assume, we navigate to screen B from screen A for result.
 *
 * Observe result in screen A:
 *
 * ```kotlin
 * val result = backStackEntry.savedStateHandle
 *     .getLiveData<T>("key")
 *     .observeAsState()
 * ```
 *
 * Set Result in screen B:
 *
 * ```kotlin
 * navController.previousBackStackEntry
 *     ?.setResults(
 *         "key" to value
 *     )
 * ```
 *
 * @param items result items.
 */
fun NavBackStackEntry.setResults(vararg items: Pair<String, Any>) {
    savedStateHandle.apply {
        for (item in items) {
            set(item.first, item.second)
        }
    }
}
