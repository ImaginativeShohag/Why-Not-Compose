package com.example.store.models.product

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Rating(
    @Json(name = "count")
    var count: Int,
    @Json(name = "rate")
    var rate: Double
)
