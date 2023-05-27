package org.imaginativeworld.whynotcompose.cms.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Comment(
    @Json(name = "id")
    val id: Int,
    @Json(name = "body")
    val body: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "post_id")
    val postId: Int
)
