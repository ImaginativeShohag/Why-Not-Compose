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

package org.imaginativeworld.whynotcompose.ui.screens.tutorial.baselineprofiles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

/**
 * Documentation: https://developer.android.com/topic/performance/baselineprofiles/overview
 */

@Composable
fun BaselineProfilesScreen(
    viewModel: BaselineProfilesViewModel,
    goBack: () -> Unit
) {
    val profileInstallStatus = viewModel.profileInstallStatus.collectAsState()

    BaselineProfilesScreenSkeleton(
        goBack = goBack,
        profileInstallStatus = profileInstallStatus.value
    )
}

@PreviewLightDark
@Composable
private fun BaselineProfilesScreenSkeletonPreview() {
    AppTheme {
        BaselineProfilesScreenSkeleton(
            goBack = {},
            profileInstallStatus = "Loading..."
        )
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun BaselineProfilesScreenSkeleton(
    goBack: () -> Unit,
    profileInstallStatus: String
) {
    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            AppComponent.Header(
                "Baseline Profiles",
                goBack = goBack
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = "Baseline Profile Install Status",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = profileInstallStatus,
                textAlign = TextAlign.Center
            )
        }
    }
}
