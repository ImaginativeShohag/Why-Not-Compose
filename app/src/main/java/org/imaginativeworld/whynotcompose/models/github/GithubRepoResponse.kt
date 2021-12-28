package org.imaginativeworld.whynotcompose.models.github


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class GithubRepoResponse(
    @Json(name = "total_count")
    val totalCount: Int?,
    @Json(name = "incomplete_results")
    val incompleteResults: Boolean?,
    @Json(name = "items")
    val items: List<GithubRepo>?,
    @Json(name = "message")
    val message: String?
)