package org.imaginativeworld.whynotcompose.ui.screens.composition.switch

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import org.imaginativeworld.whynotcompose.ui.screens.AppComponent
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme

private val ELEMENT_HEIGHT = 56.dp

@Composable
fun SwitchScreen() {
    SwitchScreenSkeleton()
}

@Preview
@Composable
fun SwitchScreenSkeletonPreview() {
    AppTheme {
        SwitchScreenSkeleton()
    }
}

@Composable
fun SwitchScreenSkeleton() {
    Scaffold(
        Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            AppComponent.Header("Switch")

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

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

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

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

            // ----------------------------------------------------------------

            AppComponent.BigSpacer()
        }
    }
}

@Composable
fun GeneralStartSwitch(
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
            style = MaterialTheme.typography.body1.merge(),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun GeneralEndSwitch(
    text: String,
    state: Boolean,
    onStateChange: (Boolean) -> Unit,
    fontWeight: FontWeight? = MaterialTheme.typography.body1.fontWeight,
) {
    Row(
        Modifier
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
            style = MaterialTheme.typography.body1.copy(
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