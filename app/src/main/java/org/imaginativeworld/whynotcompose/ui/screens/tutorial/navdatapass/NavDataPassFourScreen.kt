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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.models.DemoData

@Composable
fun NavDataPassFourScreen(
    id: Int,
    name: String,
    ranks: List<String>,
    goBack: () -> Unit,
    backWithData: (DemoData) -> Unit,
    goAnotherScreen: () -> Unit
) {
    NavDataPassFourScreenSkeleton(
        id = id,
        name = name,
        ranks = ranks,
        goBack = goBack,
        backWithData = backWithData,
        goAnotherScreen = goAnotherScreen
    )
}

@PreviewLightDark
@Composable
private fun NavDataPassFourScreenSkeletonPreview() {
    AppTheme {
        NavDataPassFourScreenSkeleton(
            id = 1,
            name = "Mahmudul Hasan Shohag",
            ranks = listOf("A", "B", "C")
        )
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun NavDataPassFourScreenSkeleton(
    id: Int,
    name: String,
    ranks: List<String>,
    goBack: () -> Unit = {},
    backWithData: (DemoData) -> Unit = {},
    goAnotherScreen: () -> Unit = {}
) {
    var text by remember { mutableStateOf("Mahmudul Hasan") }

    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            AppComponent.Header(
                "Data passed by Memory Cache",
                goBack = goBack
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppComponent.BigSpacer()

            Text(
                "Received Data",
                fontWeight = FontWeight.Bold
            )

            AppComponent.MediumSpacer()

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                text = "ID: $id"
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                text = "Name: $name"
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                text = "Ranks: ${ranks.joinToString()}"
            )

            AppComponent.BigSpacer()

            // ----------------------------------------------------------------

            HorizontalDivider()

            AppComponent.BigSpacer()

            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
                value = text,
                onValueChange = { text = it },
                label = {
                    Text("Return Name")
                }
            )

            Button(onClick = {
                backWithData(
                    DemoData(
                        id = 9,
                        name = text,
                        ranks = listOf("A+", "B+", "C+")
                    )
                )
            }) {
                Text("Return data (By Memory Cache)")
            }

            AppComponent.BigSpacer()

            // ----------------------------------------------------------------

            HorizontalDivider()

            AppComponent.BigSpacer()

            Button(onClick = {
                goAnotherScreen()
            }) {
                Text("Go Another Screen")
            }

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.BigSpacer()
        }
    }
}
