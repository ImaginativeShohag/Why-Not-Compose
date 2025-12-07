package com.example.store.models.product

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    @Json(name = "category")
    val category: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "image")
    val image: String,
    @Json(name = "price")
    val price: Double,
    @Json(name = "rating")
    val rating: Rating,
    @Json(name = "title")
    val title: String,
    @Json(name = "quantity")
    var quantity: Int = 0
)
