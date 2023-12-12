package org.imaginativeworld.whynotcompose.datasource.cache

sealed class MemoryCacheKey(val name: String) {
    data object DataOne : MemoryCacheKey("data-one")
    class DataWithParam(val id: Int) : MemoryCacheKey("data-with-param-$id")
}
