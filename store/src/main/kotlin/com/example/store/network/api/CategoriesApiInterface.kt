package com.example.store.network.api

import retrofit2.Response
import retrofit2.http.GET

interface CategoriesApiInterface {

    @GET("products/categories")
    suspend fun getCategories(): Response<List<String>>
}
