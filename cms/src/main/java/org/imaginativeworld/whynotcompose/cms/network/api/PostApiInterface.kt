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

import org.imaginativeworld.whynotcompose.cms.models.Post
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PostApiInterface {
    @GET("v2/users/{userId}/posts")
    suspend fun getPosts(
        @Path("userId") userId: Int,
        @Query("page") page: Int
    ): Response<List<Post>>

    @POST("v2/users/{userId}/posts")
    suspend fun addPost(@Path("userId") userId: Int, @Body post: Post): Response<Post>

    @GET("v2/posts/{id}")
    suspend fun getPost(@Path("id") id: Int): Response<Post>

    @DELETE("v2/posts/{id}")
    suspend fun deletePost(@Path("id") id: Int): Response<Unit>

    @PUT("v2/posts/{id}")
    suspend fun updatePost(@Path("id") id: Int, @Body post: Post): Response<Post>
}
