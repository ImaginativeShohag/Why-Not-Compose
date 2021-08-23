package org.imaginativeworld.whynotcompose.repositories

import com.google.android.libraries.maps.model.LatLng
import org.imaginativeworld.whynotcompose.models.MapPlace
import kotlin.random.Random

object MapPlaceRepo {
    private val places = listOf(
        MapPlace(
            name = "Barisal Division",
            location = LatLng(22.6954585, 90.3187848)
        ),
        MapPlace(
            name = "Chattogram Division",
            location = LatLng(22.3260781, 91.7498278)
        ),
        MapPlace(
            name = "Dhaka Division",
            location = LatLng(23.7807777, 90.3492858)
        ),
        MapPlace(
            name = "Khulna Division",
            location = LatLng(22.8454448, 89.4624617)
        ),
        MapPlace(
            name = "Mymensingh Division",
            location = LatLng(24.7489639, 90.3789864)
        ),
        MapPlace(
            name = "Rajshahi Division",
            location = LatLng(24.3802282, 88.5709965)
        ),
        MapPlace(
            name = "Rangpur Division",
            location = LatLng(25.7499116, 89.2270261)
        ),
        MapPlace(
            name = "Sylhet Division",
            location = LatLng(24.8998373, 91.8259625)
        ),
    )

    fun getPlaces(): List<MapPlace> {
        // Note: It randomly send empty list to demonstrate empty view.
        val random = Random.nextInt(10)

        if (random > 3) {
            return places
        }

        return listOf()
    }
}