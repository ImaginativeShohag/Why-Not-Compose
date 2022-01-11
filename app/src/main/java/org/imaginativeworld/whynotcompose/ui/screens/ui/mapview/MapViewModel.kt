/*
 * Copyright 2021 Md. Mahmudul Hasan Shohag
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ------------------------------------------------------------------------
 *
 * Project: Why Not Compose!
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * Source: https://github.com/ImaginativeShohag/Why-Not-Compose
 */

package org.imaginativeworld.whynotcompose.ui.screens.ui.mapview

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.ktx.addMarker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.models.Event
import org.imaginativeworld.whynotcompose.models.MapPlace
import org.imaginativeworld.whynotcompose.repositories.MapPlaceRepo
import org.imaginativeworld.whynotcompose.common.compose.composeutils.deselectMarker
import org.imaginativeworld.whynotcompose.common.compose.composeutils.selectMarker
import org.imaginativeworld.whynotcompose.common.compose.composeutils.setZoom
import org.imaginativeworld.whynotcompose.utils.extensions.dpToPx
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {

    private val _eventShowLoading = MutableStateFlow(false)

    private val _eventShowEmpty = MutableStateFlow(false)

    private val _eventShowMessage = MutableStateFlow<Event<String>?>(null)

    private val _eventCurrentLocationName = MutableStateFlow("Loading...")

    // ----------------------------------------------------------------

    private val _state = MutableStateFlow(MapViewState())
    val state: StateFlow<MapViewState>
        get() = _state

    // ----------------------------------------------------------------

    init {
        viewModelScope.launch {
            combine(
                _eventShowLoading,
                _eventShowEmpty,
                _eventShowMessage,
                _eventCurrentLocationName,
            ) { showLoading, showEmpty, showMessage, currentLocationName ->

                MapViewState(
                    loading = showLoading,
                    empty = showEmpty,
                    message = showMessage,
                    currentLocationName = currentLocationName,
                )
            }.catch { throwable ->
                // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                _state.value = it
            }
        }
    }

    // ----------------------------------------------------------------

    private var previousResult = listOf<MapPlace>()

    // ----------------------------------------------------------------

    fun loadMapPlaces(
        context: Context
    ) {
        if (previousResult.isNotEmpty()) return

        viewModelScope.launch {
            _eventShowLoading.value = true

            // Simulating delay
            delay(2000)

            val places = MapPlaceRepo.getPlaces()

            previousResult = places

            resetAndAddMapMarkers(context, places)

            _eventShowEmpty.value = places.isEmpty()

            _eventShowLoading.value = false
        }
    }

    // ----------------------------------------------------------------

    fun getCurrentLocationName(
        context: Context,
        location: LatLng?
    ) {
        Timber.e("getCurrentLocationName")

        if (location == null) return

        try {
            val geocoder = Geocoder(context, Locale.getDefault())

            // Note: Here "maxResult = 1" represent max location result to returned,
            // by documents it recommended 1 to 5
            val addresses = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
            )

            val address = addresses[0].getAddressLine(0)

            _eventCurrentLocationName.value = address
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    // ----------------------------------------------------------------
    // ----------------------------------------------------------------

    private var cacheSelectedVendor: MapPlace? = null

    fun setSelectedMapPlace(mapPlace: MapPlace?) {
        if (mapPlace != null)
            cacheSelectedVendor = mapPlace
    }

    fun clearSelectedMapPlaceCache() {
        cacheSelectedVendor = null
    }

    // ----------------------------------------------------------------

    // ----------------------------------------------------------------
    // Map Related Methods/Functions
    // ----------------------------------------------------------------

    private var selectedMarker: Marker? = null

    fun setSelectedMarker(marker: Marker?) {
        selectedMarker = marker
        selectedMarker?.showInfoWindow()
    }

    fun getSelectedMarker(): Marker? {
        return selectedMarker
    }

    // ----------------------------------------------------------------

    private var currentGoogleMap: GoogleMap? = null

    private val points = mutableListOf<LatLng>()

    private val addedMapPlaces = mutableListOf<MapPlace>()

    fun setGoogleMap(context: Context, googleMap: GoogleMap) {
        Timber.e("setGoogleMap")
        if (currentGoogleMap == null || currentGoogleMap.hashCode() != googleMap.hashCode()) {
            Timber.e("currentGoogleMap.hashCode() != googleMap.hashCode()")

            currentGoogleMap = googleMap

            initGoogleMap(context)

            resetAndAddMapMarkers(context, previousResult)
        }
    }

    @SuppressLint("MissingPermission")
    private fun initGoogleMap(context: Context) {
        currentGoogleMap?.let { gMap ->
            Timber.e("initGoogleMap")

            gMap.uiSettings.apply {
                isMyLocationButtonEnabled = false
                isMapToolbarEnabled = false
                isRotateGesturesEnabled = true
            }

            gMap.setZoom()

            if (ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context, Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
            ) {
                gMap.isMyLocationEnabled = true
            }
        }
    }

    fun gotoCurrentLocation(location: LatLng) {
        currentGoogleMap?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                location,
                15f
            )
        )
    }

    private fun clearAllMarkers() {
        currentGoogleMap?.let { gMap ->
            addedMapPlaces.clear()

            gMap.clear()

            points.clear()

            setSelectedMarker(null)
            setSelectedMapPlace(null)
        }
    }

    private fun isMarkerExist(): Boolean {
        return addedMapPlaces.isNotEmpty()
    }

    private fun resetAndAddMapMarkers(
        context: Context,
        currentVendors: List<MapPlace>,
        forceShowEmptyView: Boolean = false
    ) {
        Timber.e("resetAndAddMapMarkers")

        clearAllMarkers()

        addMapMarkers(context, currentVendors)

        viewModelScope.launch {
            if (forceShowEmptyView) {
                _eventShowEmpty.value = true
            } else {
                _eventShowEmpty.value = currentVendors.isEmpty() && previousResult.isEmpty()
            }
        }
    }

    private fun addMapMarkers(context: Context, currentMapPlaces: List<MapPlace>) {
        currentGoogleMap?.let { gMap ->
            Timber.e("addMapMarkers: currentVendors: ${currentMapPlaces.size}")

            currentMapPlaces.forEach { place ->
                if (addedMapPlaces.contains(place)) {
                    return@forEach
                }

                Timber.e("new place added to map: ${place.name}")

                addedMapPlaces.add(place)

                val latLng = LatLng(place.location.latitude, place.location.longitude)

                gMap.addMarker {
                    position(latLng)
                    title(place.name)
                    snippet("Click for details")

                    if (place == cacheSelectedVendor) {
                        selectMarker(context)
                    } else {
                        deselectMarker(context)
                    }
                }
                    ?.apply {
                        tag = place

                        if (place == cacheSelectedVendor) {
                            setSelectedMarker(this)
                            showInfoWindow()
                        }
                    }

                points.add(LatLng(place.location.latitude, place.location.longitude))
            }

            if (currentMapPlaces.isNotEmpty()) {
                if (hasMapState()) {
                    restoreMapState()
                    clearMapState()
                } else {
                    zoomToAll(true)
                }
            }
        }
    }

    fun zoomToAll(silent: Boolean) {
        Timber.e("zoomToAll")
        currentGoogleMap?.let { gMap ->
            if (points.isEmpty()) {
                if (!silent) {
                    viewModelScope.launch {
                        _eventShowMessage.value = Event("No result!")
                    }
                }
            } else {
                val boundsBuilder = LatLngBounds.Builder()
                for (point in points) {
                    boundsBuilder.include(point)
                }
                gMap.animateCamera(
                    CameraUpdateFactory.newLatLngBounds(
                        boundsBuilder.build(),
                        48.dpToPx()
                    )
                )
            }
        }
    }

    // ----------------------------------------------------------------
    // State
    // ----------------------------------------------------------------

    private var lastZoomState = 0f
    private var lastCameraPosition = LatLng(0.0, 0.0)

    private fun hasMapState(): Boolean {
        Timber.e("hasState")
        return lastZoomState != 0f && lastCameraPosition.latitude != 0.0 && lastCameraPosition.longitude != 0.0
    }

    private fun clearMapState() {
        Timber.e("clearState")
        lastZoomState = 0f
        lastCameraPosition = LatLng(0.0, 0.0)
    }

    fun saveMapState() {
        Timber.e("saveState")
        currentGoogleMap?.let { gMap ->
            lastZoomState = gMap.cameraPosition.zoom
            lastCameraPosition = gMap.cameraPosition.target
        }
    }

    private fun restoreMapState() {
        Timber.e("restoreState")
        currentGoogleMap?.let { gMap ->
            if (lastZoomState != 0f && lastCameraPosition.latitude != 0.0 && lastCameraPosition.longitude != 0.0) {
                gMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(lastCameraPosition, lastZoomState)
                )
            }
        }
    }
}

data class MapViewState(
    val loading: Boolean = false,
    val empty: Boolean = false,
    val message: Event<String>? = null,
    val currentLocationName: String = "Loading...",
)
