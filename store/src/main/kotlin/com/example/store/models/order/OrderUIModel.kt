package com.example.store.models.order

import com.example.store.models.cart.CartUIModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderUIModel(
    @Json(name = "id")
    val id: Int,
    @Json(name = "date")
    val date: String,
    @Json(name = "totalPrice")
    val totalPrice: Double,
    @Json(name = "products")
    val products: List<CartUIModel>
)
