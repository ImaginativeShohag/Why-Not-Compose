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

package org.imaginativeworld.whynotcompose.common.compose.compositions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

object AppComponent {
    @Composable
    fun Header(
        text: String,
        modifier: Modifier = Modifier,
        goBack: () -> Unit
    ) {
        TopAppBar(
            modifier = modifier,
            title = { Text(text) },
            navigationIcon = {
                IconButton(
                    onClick = goBack,
                    modifier = Modifier.testTag("nav_btn_back")
                ) {
                    Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Go Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            )
        )
    }

    @Composable
    fun SubHeader(
        text: String,
        modifier: Modifier = Modifier
    ) {
        Text(
            modifier = modifier
                .padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .fillMaxWidth(),
            text = text,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }

    @Composable
    fun MediumSpacer(
        modifier: Modifier = Modifier
    ) {
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(16.dp)
        )
    }

    @Composable
    fun BigSpacer(
        modifier: Modifier = Modifier
    ) {
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(32.dp)
        )
    }

    @Composable
    fun CustomListItem(
        text: String,
        modifier: Modifier = Modifier
    ) {
        Card(modifier.padding(16.dp, 4.dp)) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp),
                text = text,
                textAlign = TextAlign.Center
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun AppComponentPreview() {
    AppTheme {
        Scaffold { innerPadding ->
            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
            ) {
                AppComponent.Header(text = "Lorem Ipsum") {}

                AppComponent.MediumSpacer()

                AppComponent.Header(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit") {}

                AppComponent.MediumSpacer()

                HorizontalDivider()

                AppComponent.SubHeader(text = "Lorem Ipsum")

                HorizontalDivider()

                AppComponent.SubHeader(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit")

                HorizontalDivider()

                AppComponent.MediumSpacer()

                HorizontalDivider()

                AppComponent.BigSpacer()

                HorizontalDivider()

                for (i in 1..10) {
                    AppComponent.CustomListItem(text = "Lorem Ipsum")

                    AppComponent.CustomListItem(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit")
                }
            }
        }
    }
}
