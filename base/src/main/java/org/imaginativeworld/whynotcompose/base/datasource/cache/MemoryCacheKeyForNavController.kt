package org.imaginativeworld.whynotcompose.base.datasource.cache

/**
 * Memory cache key for nav controller data passing.
 */
object MemoryCacheKeyForNavController {
    data class Args(val key: String) : MemoryCacheKey("nav-controller-arg-$key")
}
