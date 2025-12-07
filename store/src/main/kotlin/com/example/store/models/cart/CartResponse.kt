package com.example.store.models.cart

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CartResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "date")
    val date: String,
    @Json(name = "products")
    val products: List<CartProductNetwork>
)

@JsonClass(generateAdapter = true)
data class CartProductNetwork(
    @Json(name = "productId") val productId: Int,
    @Json(name = "quantity") val quantity: Int
)
