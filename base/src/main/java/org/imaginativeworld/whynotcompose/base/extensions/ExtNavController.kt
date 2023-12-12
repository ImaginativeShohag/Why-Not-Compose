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
