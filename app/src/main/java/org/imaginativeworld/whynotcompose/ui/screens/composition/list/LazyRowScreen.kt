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

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import org.imaginativeworld.whynotcompose.ui.screens.AppComponent
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme

@Composable
fun LazyRowScreen() {
    LazyRowScreenSkeleton()
}

@Preview
@Composable
fun LazyRowScreenSkeletonPreview() {
    AppTheme {
        LazyRowScreenSkeleton()
    }
}

@Preview
@Composable
fun LazyRowScreenSkeletonPreviewDark() {
    AppTheme(
        darkTheme = true
    ) {
        LazyRowScreenSkeleton()
    }
}

@Composable
fun LazyRowScreenSkeleton() {
    Scaffold(
        Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding()
    ) {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            AppComponent.Header("List with Row")

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

            val itemsList = (0..5).toList()
            val itemsIndexedList = listOf("A", "B", "C")

            LazyRow(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(8.dp, 8.dp)
            ) {
                items(itemsList) {
                    CustomItemList("Item is $it")
                }

                item {
                    CustomItemList("Single item")
                }

                itemsIndexed(itemsIndexedList) { index, item ->
                    CustomItemList("Item at index $index is $item")
                }
            }
        }
    }
}

@Composable
private fun CustomItemList(
    text: String
) {
    Text(
        modifier = Modifier
            .padding(4.dp)
            .shadow(2.dp, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                // do things here.
            }
            .background(MaterialTheme.colors.surface)
            .padding(32.dp, 32.dp),
        text = text,
        textAlign = TextAlign.Center,
    )
}