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

package org.imaginativeworld.whynotcompose.cms.repositories

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.imaginativeworld.whynotcompose.base.network.SafeApiRequest
import org.imaginativeworld.whynotcompose.cms.datasource.cache.StorageCacheDataSource
import org.imaginativeworld.whynotcompose.cms.datasource.cache.StorageCacheKey
import org.imaginativeworld.whynotcompose.cms.db.CMSDatabase
import org.imaginativeworld.whynotcompose.cms.models.user.User
import org.imaginativeworld.whynotcompose.cms.models.user.UserEntity
import org.imaginativeworld.whynotcompose.cms.network.api.UserApiInterface

class UserRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: UserApiInterface,
    private val db: CMSDatabase,
    private val cache: StorageCacheDataSource
) {
    suspend fun getUsers(page: Long) = withContext(Dispatchers.IO) {
        cache.get(StorageCacheKey.UserList(page = page))
            ?: SafeApiRequest.apiRequest(context) {
                api.getUsers(page)
            }
    }

    suspend fun getUser(userId: Int) = withContext(Dispatchers.IO) {
        cache.get(StorageCacheKey.UserDetails(userId = userId))
            ?: SafeApiRequest.apiRequest(context) {
                api.getUser(userId)
            }
    }

    suspend fun deleteUser(postId: Int) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.deleteUser(postId)
        }
    }

    suspend fun updateUser(id: Int, user: User) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.updateUser(id, user)
        }
    }

    suspend fun createUser(user: User) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.createUser(user)
        }
    }

    // ----------------------------------------------------------------
    // Database
    // ----------------------------------------------------------------

    suspend fun saveUserInDB(
        userModel: UserEntity
    ): Long = withContext(Dispatchers.IO) {
        db.userDao().insert(userModel)
    }

    suspend fun removeAllUsersFromDB() = withContext(Dispatchers.IO) {
        db.userDao().removeAll()
    }

    suspend fun getUsersFromDB(): List<UserEntity> = withContext(Dispatchers.IO) {
        db.userDao().getAll()
    }

    suspend fun updateUserToDB(user: UserEntity) = withContext(Dispatchers.IO) {
        db.userDao().update(user)
    }
}
