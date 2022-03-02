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
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.base.extensions.combine
import org.imaginativeworld.whynotcompose.base.models.Event
import org.imaginativeworld.whynotcompose.models.MapPlace
import org.imaginativeworld.whynotcompose.repositories.MapPlaceRepo
import timber.log.Timber

@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {

    private val _eventShowLoading = MutableStateFlow(false)

    private val _eventShowEmpty = MutableStateFlow(false)

    private val _eventShowMessage = MutableStateFlow<Event<String>?>(null)

    private val _eventCurrentLocationName = MutableStateFlow("Loading...")

    private val _places = MutableStateFlow<List<MapPlace>>(emptyList())

    private val _selectedPlace = MutableStateFlow<MapPlace?>(null)

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
                _places,
                _selectedPlace,
            ) { showLoading, showEmpty, showMessage, currentLocationName, places, selectedPlace ->
                MapViewState(
                    loading = showLoading,
                    empty = showEmpty,
                    message = showMessage,
                    currentLocationName = currentLocationName,
                    places = places,
                    selectedPlace = selectedPlace,
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

    private var isFirstTime = true

    private var previousResult = listOf<MapPlace>()

    // ----------------------------------------------------------------

    fun loadMapPlaces() {
        if (previousResult.isNotEmpty()) return

        viewModelScope.launch {
            _eventShowLoading.value = true

            // Simulating API delay
            delay(2000)

            val places = MapPlaceRepo.getPlaces()

            previousResult = places

            _places.value = places

            _eventShowEmpty.value = places.isEmpty()

            _eventShowLoading.value = false
        }
    }

    fun isFirstTime(): Boolean {
        return if (_places.value.isNotEmpty() && isFirstTime) {
            isFirstTime = false
            return true
        } else false
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

    fun setSelectedMapPlace(mapPlace: MapPlace?) {
        _selectedPlace.value = mapPlace
    }

    fun clearSelectedMapPlace() {
        _selectedPlace.value = null
    }

    // ----------------------------------------------------------------

    fun hasLocationPermission(context: Context) = ActivityCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

data class MapViewState(
    val loading: Boolean = false,
    val empty: Boolean = false,
    val message: Event<String>? = null,
    val currentLocationName: String = "Loading...",
    val places: List<MapPlace> = emptyList(),
    val selectedPlace: MapPlace? = null
)
