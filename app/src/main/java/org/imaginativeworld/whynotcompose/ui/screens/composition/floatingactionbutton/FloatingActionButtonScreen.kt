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

package org.imaginativeworld.whynotcompose.ui.screens.composition.floatingactionbutton

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

// Source:
// https://cs.android.com/androidx/platform/tools/dokka-devsite-plugin/+/master:testData/compose/samples/material/samples/FloatingActionButtonSamples.kt

@Composable
fun FloatingActionButtonScreen() {
    FloatingActionButtonScreenSkeleton()
}

@Preview
@Composable
fun FloatingActionButtonScreenSkeletonPreview() {
    AppTheme {
        FloatingActionButtonScreenSkeleton()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FloatingActionButtonScreenSkeletonPreviewDark() {
    AppTheme {
        FloatingActionButtonScreenSkeleton()
    }
}

@Composable
fun FloatingActionButtonScreenSkeleton() {
    Scaffold(
        Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 16.dp, end = 16.dp)
        ) {
            AppComponent.Header("FloatingActionButton")

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            FloatingActionButton(onClick = { /*do something*/ }) {
                Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
            }

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            ExtendedFloatingActionButton(
                text = { Text("EXTENDED") },
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            ExtendedFloatingActionButton(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                text = { Text("ADD TO BASKET") },
                onClick = { /*do something*/ }
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            ExtendedFloatingActionButton(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                text = { Text("FLUID FAB") },
                onClick = { /*do something*/ },
                modifier = Modifier.fillMaxWidth()
            )

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.BigSpacer()
        }
    }
}
