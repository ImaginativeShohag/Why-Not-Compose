package com.example.store.models.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    @param:Json(name = "username") val username: String,
    @param:Json(name = "password") val password: String
)

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "token") val token: String
)

@JsonClass(generateAdapter = true)
data class UserResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "email") val email: String,
    @Json(name = "username") val username: String,
    @Json(name = "name") val name: Name,
    @Json(name = "phone") val phone: String,
    @Json(name = "address") val address: Address
)

@JsonClass(generateAdapter = true)
data class Name(
    @Json(name = "firstname") val firstname: String,
    @Json(name = "lastname") val lastname: String
)

@JsonClass(generateAdapter = true)
data class Address(
    @Json(name = "city") val city: String,
    @Json(name = "street") val street: String,
    @Json(name = "number") val number: Int,
    @Json(name = "zipcode") val zipcode: String
)
