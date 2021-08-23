/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.imaginativeworld.whynotcompose.utils.composeutils

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.annotation.FloatRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.BitmapDescriptor
import com.google.android.libraries.maps.model.BitmapDescriptorFactory
import com.google.android.libraries.maps.model.Marker
import com.google.android.libraries.maps.model.MarkerOptions
import org.imaginativeworld.whynotcompose.R
import timber.log.Timber

// Source code from:
// https://github.com/android/compose-samples/tree/main/Crane

const val MinZoom = 2f
const val MaxZoom = 15f

/**
 * Remembers a MapView and gives it the lifecycle of the current LifecycleOwner
 */
@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = R.id.map
        }
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle, mapView) {
        // Make MapView follow the current lifecycle
        val lifecycleObserver = getMapLifecycleObserver(mapView)
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }

    return mapView
}

private fun getMapLifecycleObserver(mapView: MapView): LifecycleEventObserver =
    LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
            Lifecycle.Event.ON_START -> mapView.onStart()
            Lifecycle.Event.ON_RESUME -> mapView.onResume()
            Lifecycle.Event.ON_PAUSE -> mapView.onPause()
            Lifecycle.Event.ON_STOP -> mapView.onStop()
            Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
            else -> throw IllegalStateException()
        }
    }

fun GoogleMap.setZoom(
    @FloatRange(from = MinZoom.toDouble(), to = MaxZoom.toDouble()) zoom: Float
) {
    resetMinMaxZoomPreference()
    setMinZoomPreference(zoom)
    setMaxZoomPreference(zoom)
}

fun GoogleMap.setZoom() {
    resetMinMaxZoomPreference()
    setMinZoomPreference(MinZoom)
    setMaxZoomPreference(MaxZoom)
}

// ----------------------------------------------------------------

fun Marker.selectMarker(context: Context) {
    Timber.e("A marker selected")
    setIcon(bitmapDescriptorFromVector(context, R.drawable.ic_location_on_map_selected))
    setAnchor(.5f, .7f)
}

fun MarkerOptions.selectMarker(context: Context) {
    Timber.e("A marker selected (with MarkerOptions)")
    icon(bitmapDescriptorFromVector(context, R.drawable.ic_location_on_map_selected))
    anchor(.5f, .7f)
}

fun Marker?.deselectMarker(context: Context) {
    Timber.e("A marker de-selected")
    this?.apply {
        setIcon(bitmapDescriptorFromVector(context, R.drawable.ic_location_on_map))
        setAnchor(.5f, 1f)
    }
}

fun MarkerOptions.deselectMarker(context: Context) {
    Timber.e("A marker de-selected (with MarkerOptions)")
    icon(bitmapDescriptorFromVector(context, R.drawable.ic_location_on_map))
    anchor(.5f, 1f)
}

fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
    // below line is use to generate a drawable.
    val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)

    // below line is use to set bounds to our vector drawable.
    vectorDrawable!!.setBounds(
        0,
        0,
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight
    )

    // below line is use to create a bitmap for our
    // drawable which we have added.
    val bitmap = Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // below line is use to add bitmap in our canvas.
    val canvas = android.graphics.Canvas(bitmap)

    // below line is use to draw our
    // vector drawable in canvas.
    vectorDrawable.draw(canvas)

    // after generating our bitmap we are returning our bitmap.
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}