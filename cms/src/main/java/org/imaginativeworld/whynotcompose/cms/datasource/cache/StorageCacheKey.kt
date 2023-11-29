package org.imaginativeworld.whynotcompose.cms.datasource.cache

sealed class StorageCacheKey(val name: String) {
    data class UserList(val page: Long) : StorageCacheKey("user-list-$page")
    data class UserDetails(val userId: Int) : StorageCacheKey("user-$userId")
}
