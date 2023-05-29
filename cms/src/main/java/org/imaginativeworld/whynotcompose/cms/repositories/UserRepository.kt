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
 * Project: Simple MVVM
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * Source: https://github.com/ImaginativeShohag/Simple-MVVM
 */

package org.imaginativeworld.whynotcompose.cms.repositories

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.imaginativeworld.whynotcompose.base.network.SafeApiRequest
import org.imaginativeworld.whynotcompose.cms.db.AppDatabase
import org.imaginativeworld.whynotcompose.cms.models.user.User
import org.imaginativeworld.whynotcompose.cms.models.user.UserEntity
import org.imaginativeworld.whynotcompose.cms.network.api.UserApiInterface

class UserRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: UserApiInterface,
    private val db: AppDatabase
) {
    suspend fun getUsers(page: Long) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.getUsers(page)
        }
    }

    suspend fun getUser(userId: Int) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
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

    suspend fun signIn(user: User) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.createUser(user)
        }
    }

    suspend fun signOut() = withContext(Dispatchers.IO) {
        db.todoDao().removeAll()
    }

    // ----------------------------------------------------------------
    // Database
    // ----------------------------------------------------------------

    suspend fun saveUserInDB(
        userModel: UserEntity
    ): Long {
        return withContext(Dispatchers.IO) {
            db.userDao().insert(userModel)
        }
    }

    suspend fun removeAllUsersFromDB() {
        return withContext(Dispatchers.IO) {
            db.userDao().removeAll()
        }
    }

    suspend fun getUsersFromDB(): List<UserEntity> {
        return withContext(Dispatchers.IO) {
            db.userDao().getAll()
        }
    }

    suspend fun updateUserToDB(user: UserEntity) {
        return withContext(Dispatchers.IO) {
            db.userDao().update(user)
        }
    }
}
