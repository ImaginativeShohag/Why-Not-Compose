package org.imaginativeworld.whynotcompose.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Keep
@JsonClass(generateAdapter = true)
@Parcelize
data class DemoData(
    val id: Int,
    val name: String,
    val ranks: List<String>
) : Parcelable
