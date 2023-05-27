package org.imaginativeworld.whynotcompose.cms.models.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    var name: String,
    var phone: String,
    var image: String?,
    var isFav: Boolean = false
)
