package com.example.store.network.api

import com.example.store.models.product.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductDetailsApiInterface {

    @GET("products/{id}")
    suspend fun getProductDetails(@Path("id") id: Int): Response<Product>
}
