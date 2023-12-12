package org.imaginativeworld.whynotcompose.base.extensions

import androidx.annotation.MainThread
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import org.imaginativeworld.whynotcompose.base.datasource.cache.MemoryCache
import org.imaginativeworld.whynotcompose.base.datasource.cache.MemoryCacheKeyForNavController

/**
 * Navigate with [args] to a route in the current NavGraph. If an invalid route is given, an
 * [IllegalArgumentException] will be thrown.
 *
 * @param route route for the destination
 * @param args arguments to pass in the target destination
 * @param navOptions special options for this navigation operation
 * @param navigatorExtras extras to pass to the [Navigator]
 *
 * @throws IllegalArgumentException if the given route is invalid
 */
@MainThread
fun NavHostController.navigate(
    route: String,
    args: Map<String, Any>,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    for (arg in args) {
        val compositeKey = route + arg.key
        MemoryCache.set(
            MemoryCacheKeyForNavController.Args(compositeKey),
            arg.value
        )
    }

    navigate(
        route = route,
        navOptions = navOptions,
        navigatorExtras = navigatorExtras
    )
}

/**
 * Get the argument that passed on navigation.
 *
 * @param key the key of the argument
 * @return the value based on the [key]. If no data found, returns `null`.
 */
inline fun <reified T> NavBackStackEntry.arg(key: String): T? {
    val compositeKey = this.destination.route + key
    return MemoryCache.get(MemoryCacheKeyForNavController.Args(compositeKey))
}
