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

package org.imaginativeworld.whynotcompose.ui.screens.composition.radiobutton

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
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

private val ELEMENT_HEIGHT = 56.dp

@Composable
fun RadioButtonScreen(
    goBack: () -> Unit
) {
    RadioButtonScreenSkeleton(
        goBack = goBack
    )
}

@PreviewLightDark
@Composable
private fun RadioButtonScreenSkeletonPreview() {
    AppTheme {
        RadioButtonScreenSkeleton()
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun RadioButtonScreenSkeleton(
    goBack: () -> Unit = {}
) {
    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            AppComponent.Header(
                "Radio Button",
                goBack = goBack
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                Modifier.padding(start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppComponent.SubHeader("Simple Radio Button")

                // ----------------------------------------------------------------

                RadioGroupSample()
            }

            // ----------------------------------------------------------------

            AppComponent.BigSpacer()
        }
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun RadioGroupSample() {
    // The first item of Pair is the caption and the second item is the value
    // that can be sent to the server or used for other logic.
    val radioOptions = listOf("Happiness" to 1, "Money" to 2, "Both" to 3)
    val (selectedOption, onOptionSelected) = remember { mutableIntStateOf(radioOptions[0].second) }

    // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
    Column(Modifier.selectableGroup()) {
        radioOptions.forEach { text ->
            GeneralRadioButton(
                text = text.first,
                value = text.second,
                selectedOption = selectedOption,
                onOptionSelect = onOptionSelected
            )
        }
    }
}

@Composable
fun <T> GeneralRadioButton(
    text: String,
    value: T,
    selectedOption: T,
    onOptionSelect: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .fillMaxWidth()
            .height(ELEMENT_HEIGHT)
            .selectable(
                selected = (value == selectedOption),
                onClick = { onOptionSelect(value) },
                role = Role.RadioButton
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Note: `onClick = null` recommended for accessibility with screenreaders.
        RadioButton(
            selected = (value == selectedOption),
            onClick = null
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.merge(),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
