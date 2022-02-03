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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

// Source: https://cs.android.com/androidx/platform/tools/dokka-devsite-plugin/+/master:testData/compose/samples/foundation/samples/LazyDslSamples.kt

@Composable
fun LazyColumnSampleTwoScreen() {
    LazyColumnSampleTwoScreenSkeleton()
}

@Preview
@Composable
fun LazyColumnSampleTwoScreenSkeletonPreview() {
    AppTheme {
        LazyColumnSampleTwoScreenSkeleton()
    }
}

@Preview
@Composable
fun LazyColumnSampleTwoScreenSkeletonPreviewDark() {
    AppTheme(
        darkTheme = true
    ) {
        LazyColumnSampleTwoScreenSkeleton()
    }
}

@Composable
fun LazyColumnSampleTwoScreenSkeleton() {
    Scaffold(
        Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding()
    ) {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            AppComponent.Header("Sticky Header Sample")

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

            val sections = listOf("A", "B", "C", "D", "E", "F", "G")

            LazyColumn(reverseLayout = true) {
                sections.forEach { section ->
                    stickyHeader {
                        Text(
                            "Section $section",
                            Modifier
                                .fillMaxWidth()
                                .background(Color.LightGray)
                                .padding(8.dp)
                        )
                    }
                    items(10) {
                        AppComponent.CustomListItem("Item $it from the section $section")
                    }
                }
            }
        }
    }
}
