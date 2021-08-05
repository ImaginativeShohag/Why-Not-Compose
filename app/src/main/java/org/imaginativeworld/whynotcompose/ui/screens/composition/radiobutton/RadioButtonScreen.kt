package org.imaginativeworld.whynotcompose.ui.screens.composition.radiobutton

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.ui.screens.AppComponent
import org.imaginativeworld.whynotcompose.ui.screens.composition.checkbox.CheckBoxScreenSkeleton
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme

/**
 * Sources:
 * - https://cs.android.com/androidx/platform/tools/dokka-devsite-plugin/+/master:testData/compose/samples/material/samples/SelectionControlsSamples.kt
 */

private val ELEMENT_HEIGHT = 56.dp

@Composable
fun RadioButtonScreen() {
    RadioButtonScreenSkeleton()
}

@Preview
@Composable
fun RadioButtonScreenSkeletonPreview() {
    AppTheme {
        CheckBoxScreenSkeleton()
    }
}

@Composable
fun RadioButtonScreenSkeleton() {
    Scaffold(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            AppComponent.Header("Radio Button")

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

            AppComponent.SubHeader("Simple Radio Button")

            // ----------------------------------------------------------------

            RadioGroupSample()

            // ----------------------------------------------------------------

            AppComponent.BigSpacer()

        }
    }
}

@Composable
fun RadioGroupSample() {
    val radioOptions = listOf("Happiness", "Money", "Both")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
    Column(Modifier.selectableGroup()) {
        radioOptions.forEach { text ->

            GeneralRadioButton(
                text = text,
                selectedOption = selectedOption,
                onOptionSelected = onOptionSelected
            )

        }
    }
}

@Composable
fun GeneralRadioButton(
    text: String,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(ELEMENT_HEIGHT)
            .selectable(
                selected = (text == selectedOption),
                onClick = { onOptionSelected(text) },
                role = Role.RadioButton
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = (text == selectedOption),
            onClick = null // null recommended for accessibility with screenreaders
        )
        Text(
            text = text,
            style = MaterialTheme.typography.body1.merge(),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}