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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.base.extensions.dpToPx
import org.imaginativeworld.whynotcompose.common.compose.R as CommonComposeR
import org.imaginativeworld.whynotcompose.common.compose.composeutils.bitmapDescriptorFromVector
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.models.MapPlace
import org.imaginativeworld.whynotcompose.ui.compositions.CustomSnackbarHost
import timber.log.Timber

// TODO: add location permission
// TODO: add current location request
// TODO: Displaying info window if the place is selected:
// https://github.com/googlemaps/android-maps-compose/issues/16

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun MapScreen(
    viewModel: MapViewViewModel,
    goBack: () -> Unit,
    gotoDetailsScreen: (MapPlace) -> Unit
) {
    val context = LocalContext.current

    val cameraPositionState = rememberCameraPositionState()

    val state by viewModel.state.collectAsState()

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.loadMapPlaces()
    }

    LaunchedEffect(state.places) {
        if (viewModel.isFirstTime()) {
            Timber.e("state.places: ${state.places.count()}")
            val boundsBuilder = LatLngBounds.Builder()

            for (place in state.places) {
                boundsBuilder.include(place.location)
            }

            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngBounds(
                    boundsBuilder.build(),
                    48.dpToPx()
                )
            )
        }
    }

    MapSkeleton(
        showLoadingView = state.loading,
        showEmptyView = state.empty && !state.loading,
        goBack = goBack,
        onRetryClick = {
            viewModel.loadMapPlaces()
        },
        mapView = { modifier ->
            GoogleMap(
                modifier = modifier,
                cameraPositionState = cameraPositionState,
                googleMapOptionsFactory = {
                    GoogleMapOptions()
                },
                properties = MapProperties(
                    isMyLocationEnabled = viewModel.hasLocationPermission(context),
                    minZoomPreference = 2f,
                    maxZoomPreference = 15f
                ),
                uiSettings = MapUiSettings(
                    myLocationButtonEnabled = false,
                    mapToolbarEnabled = false,
                    rotationGesturesEnabled = true,
                    zoomControlsEnabled = false
                )
            ) {
                for (place in state.places) {
                    MarkerInfoWindow(
                        state = rememberMarkerState(position = place.location),
                        anchor = if (state.selectedPlace == place) {
                            Offset(.5f, .7f)
                        } else {
                            Offset(.5f, 1f)
                        },
                        title = place.name,
                        snippet = "Click for details",
                        icon = if (state.selectedPlace == place) {
                            bitmapDescriptorFromVector(
                                context,
                                CommonComposeR.drawable.ic_location_on_map_selected
                            )
                        } else {
                            bitmapDescriptorFromVector(
                                context,
                                CommonComposeR.drawable.ic_location_on_map
                            )
                        },
                        onClick = {
                            viewModel.setSelectedMapPlace(place)

                            false
                        },
                        onInfoWindowClick = {
                            scope.launch {
                                gotoDetailsScreen(place)
                            }
                        },
                        onInfoWindowClose = {
                            viewModel.clearSelectedMapPlace()
                        }
                    ) { market ->
                        Column(
                            Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.primary,
                                    RoundedCornerShape(8.dp)
                                )
                                .background(MaterialTheme.colorScheme.background)
                                .padding(8.dp, 2.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = market.title ?: "",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = market.snippet ?: "",
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun MapSkeletonPreview() {
    AppTheme {
        MapSkeleton(
            mapView = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }
        )
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun MapSkeleton(
    mapView: @Composable (Modifier) -> Unit,
    showLoadingView: Boolean = true,
    showEmptyView: Boolean = true,
    goBack: () -> Unit = {},
    onRetryClick: () -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            AppComponent.Header(
                "Divisions of Bangladesh",
                goBack = goBack
            )
        },
        snackbarHost = { CustomSnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        mapView(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )

        MapEmptyView(
            show = showEmptyView,
            onRetryClick = onRetryClick,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )

        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Box(
                Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                MapLoadingView(
                    modifier = Modifier
                        .padding(top = 16.dp),
                    isVisible = showLoadingView
                )
            }
        }
    }
}

@Composable
private fun MapLoadingView(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Row(
            modifier
                .shadow(2.dp, CircleShape)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface)
                .padding(0.dp, 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .size(16.dp),
                strokeWidth = 2.dp
            )
            Text(
                modifier = Modifier.padding(end = 12.dp),
                text = "Loading...",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = .75f)
            )
        }
    }
}

@Composable
private fun MapEmptyView(
    show: Boolean,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = show,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {},
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                Modifier
                    .clip(RoundedCornerShape(32.dp, 32.dp, 32.dp, 32.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(start = 32.dp, end = 32.dp, bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .size(96.dp),
                    painter = painterResource(
                        id = CommonComposeR.drawable.ic_location_on_map_selected
                    ),
                    contentDescription = "Empty",
                    alpha = .25f,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Nothing to see!",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = .7f),
                    textAlign = TextAlign.Center
                )

                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = "Please try again.",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = .7f),
                    textAlign = TextAlign.Center
                )

                TextButton(
                    modifier = Modifier.padding(top = 8.dp),
                    onClick = {
                        onRetryClick()
                    }
                ) {
                    Text(text = "Retry")
                }
            }
        }
    }
}
