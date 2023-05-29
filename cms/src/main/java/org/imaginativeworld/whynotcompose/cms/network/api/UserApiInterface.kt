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

package org.imaginativeworld.whynotcompose.cms.network.api

import org.imaginativeworld.whynotcompose.cms.models.user.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiInterface {
    @GET("v2/users")
    suspend fun getUsers(@Query("page") page: Long): Response<List<User>>

    @GET("v2/users/{id}")
    suspend fun getUser(@Path("id") userId: Int): Response<User>

    @POST("v2/users")
    suspend fun createUser(@Body user: User): Response<User>

    @DELETE("v2/users/{id}")
    suspend fun deleteUser(@Path("id") id: Int): Response<Unit>

    @PUT("v2/users/{id}")
    suspend fun updateUser(@Path("id") id: Int, @Body user: User): Response<User>
}
