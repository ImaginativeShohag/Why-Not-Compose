package com.example.store.network.api

import com.example.store.models.auth.LoginRequest
import com.example.store.models.auth.LoginResponse
import com.example.store.models.auth.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiInterface {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("users")
    suspend fun getAllUsers(): Response<List<UserResponse>>
}