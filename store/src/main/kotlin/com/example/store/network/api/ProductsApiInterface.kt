package com.example.store.network.api

import com.example.store.models.product.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsApiInterface {

    @GET("products")
    suspend fun getProducts(@Query("page") page: Long): Response<List<Product>>

    @GET("products/categories")
    suspend fun getCategories(): Response<List<String>>

    @GET("products/category/{categoryName}")
    suspend fun getProductsByCategory(@Path("categoryName") categoryName: String): Response<List<Product>>
}
