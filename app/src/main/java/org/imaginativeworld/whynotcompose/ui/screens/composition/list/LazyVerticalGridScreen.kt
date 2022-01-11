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

package org.imaginativeworld.whynotcompose.ui.screens.composition.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import org.imaginativeworld.whynotcompose.common.compose.composeutils.rememberImagePainter
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

@Composable
fun LazyVerticalGridScreen() {
    LazyVerticalGridScreenSkeleton()
}

@Preview
@Composable
fun LazyVerticalGridScreenSkeletonPreview() {
    AppTheme {
        LazyVerticalGridScreenSkeleton()
    }
}

@Preview
@Composable
fun LazyVerticalGridScreenSkeletonPreviewDark() {
    AppTheme(
        darkTheme = true
    ) {
        LazyVerticalGridScreenSkeleton()
    }
}

@Composable
fun LazyVerticalGridScreenSkeleton() {
    Scaffold(
        Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding()
    ) {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            AppComponent.Header("Grid with LazyVerticalGrid")

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

            AppComponent.SubHeader(
                text = "Adaptive Columns"
            )

            Divider()

            val itemsList = (1..102).toList()

            LazyVerticalGrid(
                cells = GridCells.Adaptive(64.dp),
                modifier = Modifier
                    .weight(1f),
            ) {
                items(itemsList) { item ->
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        painter = rememberImagePainter(
                            data = "https://picsum.photos/seed/$item/128",
                            crossFade = true
                        ),
                        contentDescription = null
                    )
                }

                // You can also use:
                // - item {}
                // - itemsIndexed(items) { index, item -> }
            }

            // ----------------------------------------------------------------

            Divider()

            AppComponent.SubHeader(
                text = "Fixed Columns"
            )

            Divider()

            LazyVerticalGrid(
                cells = GridCells.Fixed(3),
                modifier = Modifier
                    .weight(1f),
            ) {
                items(itemsList) { item ->
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        painter = rememberImagePainter(
                            data = "https://picsum.photos/seed/$item/200",
                            crossFade = true
                        ),
                        contentDescription = null
                    )
                }

                // You can also use:
                // - item {}
                // - itemsIndexed(items) { index, item -> }
            }
        }
    }
}
