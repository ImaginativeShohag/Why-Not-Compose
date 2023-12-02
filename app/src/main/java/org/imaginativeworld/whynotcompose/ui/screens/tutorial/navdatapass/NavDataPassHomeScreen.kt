/*
 * Copyright 2023 Md. Mahmudul Hasan Shohag
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

package org.imaginativeworld.whynotcompose.ui.screens.tutorial.navdatapass

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.models.DemoData

/**
 * Caution: Passing complex data structures over arguments is considered an anti-pattern.
 * Each destination should be responsible for loading UI data based on the minimum necessary
 * information, such as item IDs. This simplifies process recreation and avoids potential
 * data inconsistencies.
 *
 * Source: https://developer.android.com/guide/navigation/use-graph/pass-data#supported_argument_types
 *
 * For more info see: https://stackoverflow.com/a/67133534/2263329
 */

@Composable
fun NavDataPassHomeScreen(
    receivedData: DemoData?,
    goBack: () -> Unit,
    gotoScreenOne: (DemoData) -> Unit,
    gotoScreenTwo: (DemoData) -> Unit,
    gotoScreenThree: (id: Int, name: String) -> Unit
) {
    NavDataPassHomeScreenSkeleton(
        receivedData = receivedData,
        goBack = goBack,
        gotoScreenOne = {
            gotoScreenOne(
                DemoData(
                    id = 1,
                    name = "John Doe",
                    ranks = listOf("A", "B", "C")
                )
            )
        },
        gotoScreenTwo = {
            gotoScreenTwo(
                DemoData(
                    id = 1,
                    name = "John Doe",
                    ranks = listOf("A", "B", "C")
                )
            )
        },
        gotoScreenThree = gotoScreenThree
    )
}

@Preview
@Composable
fun NavDataPassHomeScreenSkeletonPreview() {
    AppTheme {
        NavDataPassHomeScreenSkeleton(
            receivedData = DemoData(
                id = 1,
                name = "John Doe",
                ranks = listOf("A", "B", "C")
            )
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NavDataPassHomeScreenSkeletonPreviewDark() {
    AppTheme {
        NavDataPassHomeScreenSkeleton(
            receivedData = DemoData(
                id = 1,
                name = "John Doe",
                ranks = listOf("A", "B", "C")
            )
        )
    }
}

@Composable
fun NavDataPassHomeScreenSkeleton(
    receivedData: DemoData?,
    goBack: () -> Unit = {},
    gotoScreenOne: () -> Unit = {},
    gotoScreenTwo: () -> Unit = {},
    gotoScreenThree: (id: Int, name: String) -> Unit = { _, _ -> }
) {
    var id by remember { mutableStateOf("1") }
    var name by remember { mutableStateOf("Mahmudul Hasan Shohag") }

    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding()
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppComponent.Header(
                "Navigation Data Pass",
                goBack = goBack
            )

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

            AppComponent.BigSpacer()

            Text(
                "Received Data",
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "$receivedData",
                textAlign = TextAlign.Center
            )

            AppComponent.BigSpacer()

            // ----------------------------------------------------------------

            Divider()

            AppComponent.BigSpacer()

            Text(
                "Pass simple data as parameter (Recommended)",
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                value = id,
                onValueChange = {
                    val regex = Regex("[^0-9]")
                    id = regex.replace(it, "")
                },
                label = {
                    Text("ID Parameter")
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                value = name,
                onValueChange = { name = it },
                label = {
                    Text("Name Parameter")
                }
            )

            AppComponent.MediumSpacer()

            Button(onClick = {
                gotoScreenThree(id.toInt(), name)
            }) {
                Text("Pass Parameters")
            }

            AppComponent.BigSpacer()

            // ----------------------------------------------------------------

            Divider()

            AppComponent.BigSpacer()

            Button(onClick = {
                gotoScreenOne()
            }) {
                Text("Pass complex data as String")
            }

            AppComponent.MediumSpacer()

            Button(onClick = {
                gotoScreenTwo()
            }) {
                Text("Pass complex data as Parcelable")
            }

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.BigSpacer()
        }
    }
}
