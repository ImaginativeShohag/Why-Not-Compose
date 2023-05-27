package org.imaginativeworld.whynotcompose.cms.models.todo

import androidx.annotation.ColorRes
import androidx.compose.ui.graphics.Color
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date
import java.util.Locale
import org.imaginativeworld.whynotcompose.base.extensions.getHumanReadableDate
import org.imaginativeworld.whynotcompose.base.extensions.toLower
import org.imaginativeworld.whynotcompose.common.compose.theme.FlatColor

@JsonClass(generateAdapter = true)
data class Todo(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "title")
    val title: String,
    @Json(name = "due_on")
    val dueOn: Date?,
    @Json(name = "status")
    val status: String,
    @Json(name = "user_id")
    val userId: Int = 1 // This is used for demo only.
) {
    fun getDueDate(): String {
        return dueOn?.getHumanReadableDate() ?: "No"
    }

    fun getStatusLabel(): String {
        return status.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
    }

    @ColorRes
    fun getStatusColor(): Color {
        return if (status.toLower() == "completed") {
            FlatColor.FlatAwesomeGreen2
        } else {
            FlatColor.FlatRed2
        }
    }
}

fun Todo.asEntity() = TodoEntity(
    id = id,
    title = title,
    dueOn = dueOn,
    status = status,
    userId = userId
)
