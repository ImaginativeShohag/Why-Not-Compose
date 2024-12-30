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

package org.imaginativeworld.whynotcompose.ui.screens.composition.switch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

private val ELEMENT_HEIGHT = 56.dp

@Composable
fun SwitchScreen(
    goBack: () -> Unit
) {
    SwitchScreenSkeleton(
        goBack = goBack
    )
}

@PreviewLightDark
@Composable
private fun SwitchScreenSkeletonPreview() {
    AppTheme {
        SwitchScreenSkeleton()
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun SwitchScreenSkeleton(
    goBack: () -> Unit = {}
) {
    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            AppComponent.Header(
                "Switch",
                goBack = goBack
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(Modifier.padding(start = 16.dp, end = 16.dp)) {
                AppComponent.SubHeader("Start Switch")

                // ----------------------------------------------------------------

                val (state1, onStateChange1) = remember { mutableStateOf(true) }
                val (state2, onStateChange2) = remember { mutableStateOf(false) }

                GeneralStartSwitch(
                    text = "Do what you Love",
                    state = state1,
                    onStateChange = onStateChange1
                )

                GeneralStartSwitch(
                    text = "Love what you Do",
                    state = state2,
                    onStateChange = onStateChange2
                )
            }

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            HorizontalDivider()

            Column(Modifier.padding(start = 16.dp, end = 16.dp)) {
                AppComponent.SubHeader("End Switch")

                // ----------------------------------------------------------------

                val (state3, onStateChange3) = remember { mutableStateOf(false) }
                val (state4, onStateChange4) = remember { mutableStateOf(true) }

                GeneralEndSwitch(
                    text = "Do what you Love",
                    state = state3,
                    onStateChange = onStateChange3
                )

                GeneralEndSwitch(
                    text = "Love what you Do",
                    state = state4,
                    onStateChange = onStateChange4
                )
            }

            // ----------------------------------------------------------------

            AppComponent.BigSpacer()
        }
    }
}

@Composable
fun GeneralStartSwitch(
    text: String,
    state: Boolean,
    onStateChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .fillMaxWidth()
            .height(ELEMENT_HEIGHT)
            .selectable(
                selected = state,
                onClick = { onStateChange(!state) },
                role = Role.Switch
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Switch(
            checked = state,
            onCheckedChange = null
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.merge(),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun GeneralEndSwitch(
    text: String,
    state: Boolean,
    onStateChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight? = MaterialTheme.typography.bodyLarge.fontWeight
) {
    Row(
        modifier
            .fillMaxWidth()
            .height(ELEMENT_HEIGHT)
            .selectable(
                selected = state,
                onClick = { onStateChange(!state) },
                role = Role.Switch
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = fontWeight
            ),
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f)
        )
        Switch(
            checked = state,
            onCheckedChange = null
        )
    }
}
