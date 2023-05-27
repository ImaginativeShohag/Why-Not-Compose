package org.imaginativeworld.whynotcompose.cms.models.todo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey
    val id: Int,

    val title: String,
    val dueOn: Date?,
    val status: String,
    val userId: Int = 1
)
fun TodoEntity.asModel() = Todo(
    id = id,
    title = title,
    dueOn = dueOn,
    status = status,
    userId = userId
)
