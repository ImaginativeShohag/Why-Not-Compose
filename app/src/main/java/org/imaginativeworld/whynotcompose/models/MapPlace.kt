package org.imaginativeworld.whynotcompose.models

import androidx.annotation.Keep
import com.google.android.libraries.maps.model.LatLng
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class MapPlace(
    val name: String,
    val location: LatLng,
)