package com.example.store.network.api

import com.example.store.models.cart.CartResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CartApiInterface {
    @GET("carts/user/{userId}")
    suspend fun getUserCart(@Path("userId") userId: Int): Response<List<CartResponse>>
}
