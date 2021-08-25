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

package org.imaginativeworld.whynotcompose.ui.screens.composition.list.lazycolumn

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import org.imaginativeworld.whynotcompose.ui.screens.AppComponent
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme

// Source: https://cs.android.com/androidx/platform/tools/dokka-devsite-plugin/+/master:testData/compose/samples/foundation/samples/LazyDslSamples.kt

@Composable
fun LazyColumnSampleOneScreen() {
    LazyColumnSampleOneScreenSkeleton()
}

@Preview
@Composable
fun LazyColumnSampleOneScreenSkeletonPreview() {
    AppTheme {
        LazyColumnSampleOneScreenSkeleton()
    }
}

@Preview
@Composable
fun LazyColumnSampleOneScreenSkeletonPreviewDark() {
    AppTheme(
        darkTheme = true
    ) {
        LazyColumnSampleOneScreenSkeleton()
    }
}

@Composable
fun LazyColumnSampleOneScreenSkeleton() {
    Scaffold(
        Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding()
    ) {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            AppComponent.Header("Basic Sample")

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

            val itemsList = (0..10).toList()
            val itemsIndexedList = listOf("A", "B", "C", "D", "E", "F", "G")

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(0.dp, 8.dp)
            ) {
                items(itemsList) {
                    AppComponent.CustomListItem("Item is $it")
                }

                item {
                    AppComponent.CustomListItem("Single item")
                }

                itemsIndexed(itemsIndexedList) { index, item ->
                    AppComponent.CustomListItem("Item at index $index is $item")
                }
            }

        }
    }
}