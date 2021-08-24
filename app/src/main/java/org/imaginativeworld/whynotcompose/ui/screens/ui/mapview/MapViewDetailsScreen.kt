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

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import org.imaginativeworld.whynotcompose.models.MapPlace
import org.imaginativeworld.whynotcompose.repositories.MapPlaceRepo
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme

@Composable
fun MapViewDetailsScreen(
    item: MapPlace,
    goBack: () -> Unit
) {
    MapViewDetailsScreenSkeleton(
        item = item,
        goBack = goBack,
    )
}

@Preview
@Composable
fun MapViewDetailsScreenSkeletonPreview() {
    AppTheme {
        MapViewDetailsScreenSkeleton(
            item = MapPlaceRepo.getDemoPlace()
        )
    }
}

@Preview
@Composable
fun MapViewDetailsScreenSkeletonPreviewDark() {
    AppTheme(darkTheme = true) {
        MapViewDetailsScreenSkeleton(
            item = MapPlaceRepo.getDemoPlace()
        )
    }
}

@Composable
fun MapViewDetailsScreenSkeleton(
    item: MapPlace,
    goBack: () -> Unit = {},
) {
    Scaffold(
        Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding()
    ) {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            TopAppBar(
                title = { Text(item.name) },
                navigationIcon = {
                    IconButton(onClick = {
                        goBack()
                    }) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                },
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 16.dp, 16.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .alpha(.8f),
                        text = "LATITUDE",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "${item.location.latitude}",
                        fontWeight = FontWeight.Medium,
                    )
                }

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .alpha(.8f),
                        text = "LONGITUDE",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "${item.location.longitude}",
                        fontWeight = FontWeight.Medium,
                    )
                }
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                text = item.description
            )

        }
    }
}