package org.imaginativeworld.whynotcompose.ui.screens.composition.checkbox

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.ui.screens.AppComponent
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme

private val ELEMENT_HEIGHT = 56.dp

@Composable
fun CheckBoxScreen() {
    CheckBoxScreenSkeleton()
}

@Preview
@Composable
fun CheckBoxScreenSkeletonPreview() {
    AppTheme {
        CheckBoxScreenSkeleton()
    }
}

@Composable
fun CheckBoxScreenSkeleton() {
    Scaffold(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            AppComponent.Header("Check Box")

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

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

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

            AppComponent.SubHeader("Tri-State Check Box")

            // ----------------------------------------------------------------

            TriStateCheckboxSample()

            // ----------------------------------------------------------------

            AppComponent.BigSpacer()

        }
    }
}

@Composable
fun TriStateCheckboxSample() {
    Column {
        // define dependent checkboxes states
        val (state1, onStateChange1) = remember { mutableStateOf(true) }
        val (state2, onStateChange2) = remember { mutableStateOf(false) }

        // TriStateCheckbox state reflects state of dependent checkboxes
        val parentState = remember(state1, state2) {
            if (state1 && state2) ToggleableState.On
            else if (!state1 && !state2) ToggleableState.Off
            else ToggleableState.Indeterminate
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
            onClick = onParentClick,
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
    onClick: () -> Unit
) {
    Row(
        Modifier
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
            style = MaterialTheme.typography.body1.merge(),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun GeneralCheckBox(
    text: String,
    state: Boolean,
    onStateChange: (Boolean) -> Unit
) {
    Row(
        Modifier
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
            style = MaterialTheme.typography.body1.merge(),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
