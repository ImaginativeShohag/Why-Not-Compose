package org.imaginativeworld.whynotcompose.ui.screens.ui.mapview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.MapView
import com.google.maps.android.ktx.awaitMap
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.models.MapPlace
import org.imaginativeworld.whynotcompose.ui.compositions.CustomSnackbarHost
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme
import org.imaginativeworld.whynotcompose.ui.theme.TailwindCSSColor
import org.imaginativeworld.whynotcompose.utils.composeutils.deselectMarker
import org.imaginativeworld.whynotcompose.utils.composeutils.rememberMapViewWithLifecycle
import org.imaginativeworld.whynotcompose.utils.composeutils.selectMarker

// TODO: add location permission
// TODO: add current location request

@Composable
fun MapScreen(
    viewModel: MapViewModel,
    goBack: () -> Unit,
    gotoDetailsScreen: (MapPlace) -> Unit,
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadMapPlaces(context)
    }

    // ================================================================
    // Map related things
    // ================================================================

    MapSkeleton(
        showLoadingView = state.loading,
        showEmptyView = state.empty && !state.loading,
        goBack = goBack,
        mapView = { modifier ->
            MapPlaceMapView(
                modifier = modifier,
                mapViewUpdateCallback = { gMap ->

                    viewModel.setGoogleMap(context, gMap)

                    gMap.setOnCameraIdleListener {
                        viewModel.saveMapState()
                    }

                    gMap.setOnMarkerClickListener { marker ->
                        if (!marker.isInfoWindowShown) {
                            val mapPlace = marker.tag as MapPlace?

                            mapPlace?.let {
                                // deselect previous selected marker
                                viewModel.getSelectedMarker()?.deselectMarker(context)

                                // select new marker
                                marker.selectMarker(context)

                                viewModel.setSelectedMarker(marker)
                                viewModel.setSelectedMapPlace(mapPlace)

                                viewModel.saveMapState()
                            }
                        }

                        false
                    }

                    gMap.setOnMapClickListener {
                        viewModel.getSelectedMarker()?.deselectMarker(context)
                        viewModel.setSelectedMarker(null)

                        viewModel.setSelectedMapPlace(null)

                        viewModel.clearSelectedMapPlaceCache()
                    }

                    gMap.setOnInfoWindowClickListener { marker ->
                        val mapPlace = marker.tag as MapPlace?

                        mapPlace?.let { place ->
                            viewModel.setSelectedMarker(marker)
                            viewModel.setSelectedMapPlace(place)

                            viewModel.saveMapState()

                            gotoDetailsScreen(place)
                        }
                    }
                }
            )
        }
    )
}

@Preview
@Composable
fun MapSkeletonPreview() {
    AppTheme {
        MapSkeleton(
            mapView = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(TailwindCSSColor.Green500)
                )
            },
        )
    }
}

@Preview
@Composable
fun MapSkeletonPreviewDark() {
    AppTheme(darkTheme = true) {
        MapSkeleton(
            mapView = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(TailwindCSSColor.Green500)
                )
            },
        )
    }
}

@Composable
fun MapSkeleton(
    mapView: @Composable (Modifier) -> Unit,
    showLoadingView: Boolean = true,
    showEmptyView: Boolean = true,
    goBack: () -> Unit = {},
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding(),
        scaffoldState = scaffoldState,
        snackbarHost = { CustomSnackbarHost(it) },
    ) {
        mapView(
            Modifier
                .padding(top = 56.dp)
                .fillMaxSize()
        )

        MapEmptyView(
            modifier = Modifier
                .padding(top = 56.dp)
                .fillMaxSize(),
            show = showEmptyView,
        )

        Column(Modifier.fillMaxSize()) {
            TopAppBar(
                title = { Text("Divisions of Bangladesh") },
                navigationIcon = {
                    IconButton(onClick = {
                        goBack()
                    }) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                },
            )

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
private fun MapPlaceMapView(
    modifier: Modifier,
    mapViewUpdateCallback: suspend (GoogleMap) -> Unit,
) {
    // The MapView lifecycle is handled by this composable. As the MapView also needs to be updated
    // with input from Compose UI, those updates are encapsulated into the MapViewContainer
    // composable. In this way, when an update to the MapView happens, this composable won't
    // recompose and the MapView won't need to be recreated.
    val mapView = rememberMapViewWithLifecycle()
    MapPlaceMapViewContainer(modifier, mapView, mapViewUpdateCallback)
}

@Composable
private fun MapPlaceMapViewContainer(
    modifier: Modifier,
    map: MapView,
    mapViewUpdateCallback: suspend (GoogleMap) -> Unit,
) {
    LaunchedEffect(map) {
        val googleMap = map.awaitMap()

        mapViewUpdateCallback(googleMap)
    }

    AndroidView(
        modifier = modifier,
        factory = { map },
        update = {}
    )
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
                .background(MaterialTheme.colors.surface)
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
                color = MaterialTheme.colors.onSurface.copy(alpha = .75f)
            )
        }
    }
}

@Composable
fun MapEmptyView(
    modifier: Modifier = Modifier,
    show: Boolean = true,
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
                    .background(MaterialTheme.colors.surface)
                    .padding(start = 32.dp, end = 32.dp, bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .size(96.dp),
                    painter = painterResource(id = R.drawable.ic_location_on_map_selected),
                    contentDescription = "Empty",
                    alpha = .25f,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
                )

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Nothing to see!",
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.onSurface.copy(alpha = .7f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}