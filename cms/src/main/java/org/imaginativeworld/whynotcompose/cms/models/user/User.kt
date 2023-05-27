package org.imaginativeworld.whynotcompose.cms.models.user

import androidx.annotation.ColorRes
import androidx.compose.ui.graphics.Color
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Locale
import org.imaginativeworld.whynotcompose.base.extensions.toLower
import org.imaginativeworld.whynotcompose.common.compose.theme.FlatColor

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "gender")
    val gender: String,
    @Json(name = "status")
    val status: String
) {
    fun getStatusLabel(): String {
        return status.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
    }

    fun getGenderLabel(): String {
        return gender.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
    }

    @ColorRes
    fun getStatusColor(): Color {
        return if (status.toLower() == "active") {
            FlatColor.FlatAwesomeGreen2
        } else {
            FlatColor.FlatRed2
        }
    }
}
