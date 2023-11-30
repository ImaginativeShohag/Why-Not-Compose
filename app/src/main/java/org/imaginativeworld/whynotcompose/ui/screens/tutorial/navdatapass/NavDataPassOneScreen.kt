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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.models.DemoData

@Composable
fun NavDataPassOneScreen(
    data: DemoData,
    goBack: () -> Unit,
    backWithData: (DemoData) -> Unit
) {
    NavDataPassOneScreenSkeleton(
        goBack = goBack,
        data = data,
        backWithData = { data ->
            backWithData(data)
        }
    )
}

@Preview
@Composable
fun NavDataPassOneScreenSkeletonPreview() {
    AppTheme {
        NavDataPassOneScreenSkeleton(
            data = DemoData(
                id = 1,
                name = "John Doe",
                ranks = listOf("A", "B", "C")
            )
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NavDataPassOneScreenSkeletonPreviewDark() {
    AppTheme {
        NavDataPassOneScreenSkeleton(
            data = DemoData(
                id = 1,
                name = "John Doe",
                ranks = listOf("A", "B", "C")
            )
        )
    }
}

@Composable
fun NavDataPassOneScreenSkeleton(
    data: DemoData,
    goBack: () -> Unit = {},
    backWithData: (DemoData) -> Unit = {}
) {
    var text by remember { mutableStateOf("Mahmudul Hasan") }

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
                "Data passed as String",
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
                text = "$data",
                textAlign = TextAlign.Center
            )

            AppComponent.BigSpacer()

            // ----------------------------------------------------------------

            Divider()

            AppComponent.BigSpacer()

            OutlinedTextField(
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
                Text("Return data")
            }

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.BigSpacer()
        }
    }
}
