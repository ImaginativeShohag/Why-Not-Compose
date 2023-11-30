package org.imaginativeworld.whynotcompose.ui.screens.tutorial.navdatapass

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
    gotoScreenTwo: (DemoData) -> Unit
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
        }
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
    gotoScreenTwo: () -> Unit = {}
) {
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

            Button(onClick = {
                gotoScreenOne()
            }) {
                Text("Pass data as String")
            }

            Button(onClick = {
                gotoScreenTwo()
            }) {
                Text("Pass data as Parcelable")
            }

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.BigSpacer()
        }
    }
}
