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

package org.imaginativeworld.whynotcompose.ui.screens.composition.checkbox

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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

private val ELEMENT_HEIGHT = 56.dp

@Composable
fun CheckBoxScreen(
    goBack: () -> Unit
) {
    CheckBoxScreenSkeleton(
        goBack = goBack
    )
}

@PreviewLightDark
@Composable
private fun CheckBoxScreenSkeletonPreview() {
    AppTheme {
        CheckBoxScreenSkeleton()
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun CheckBoxScreenSkeleton(
    goBack: () -> Unit = {}
) {
    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            AppComponent.Header(
                "Check Box",
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
                AppComponent.SubHeader("Simple Check Box")

                // ----------------------------------------------------------------

                val (state1, onStateChange1) = remember { mutableStateOf(false) }
                val (state2, onStateChange2) = remember { mutableStateOf(true) }

                GeneralCheckBox(
                    text = "Do what you Love",
                    state = state1,
                    onStateChange = onStateChange1
                )
                GeneralCheckBox(
                    text = "Love what you Do",
                    state = state2,
                    onStateChange = onStateChange2
                )
            }

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            HorizontalDivider()

            Column(Modifier.padding(start = 16.dp, end = 16.dp)) {
                AppComponent.SubHeader("Tri-State Check Box")

                // ----------------------------------------------------------------

                TriStateCheckboxSample()

                // ----------------------------------------------------------------

                AppComponent.BigSpacer()
            }
        }
    }
}

@Composable
private fun TriStateCheckboxSample() {
    Column {
        // define dependent checkboxes states
        val (state1, onStateChange1) = remember { mutableStateOf(true) }
        val (state2, onStateChange2) = remember { mutableStateOf(false) }

        // TriStateCheckbox state reflects state of dependent checkboxes
        val parentState = remember(state1, state2) {
            if (state1 && state2) {
                ToggleableState.On
            } else if (!state1 && !state2) {
                ToggleableState.Off
            } else {
                ToggleableState.Indeterminate
            }
        }
        // click on TriStateCheckbox can set state for dependent checkboxes
        val onParentClick = {
            val s = parentState != ToggleableState.On
            onStateChange1(s)
            onStateChange2(s)
        }

        GeneralTriStateCheckBox(
            text = "Love you Life",
            state = parentState,
            onClick = onParentClick
        )
        Column(Modifier.padding(32.dp, 0.dp, 0.dp, 0.dp)) {
            GeneralCheckBox(
                text = "Love what you Do",
                state = state1,
                onStateChange = onStateChange1
            )
            GeneralCheckBox(
                text = "Do what you Love",
                state = state2,
                onStateChange = onStateChange2
            )
        }
    }
}

@Composable
fun GeneralTriStateCheckBox(
    text: String,
    state: ToggleableState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .fillMaxWidth()
            .height(ELEMENT_HEIGHT)
            .selectable(
                selected = state == ToggleableState.On,
                onClick = { onClick() },
                role = Role.Checkbox
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TriStateCheckbox(state, null)
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.merge(),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun GeneralCheckBox(
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
                role = Role.RadioButton
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(state, null)
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.merge(),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
