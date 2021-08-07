package org.imaginativeworld.whynotcompose.ui.screens.composition.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.imaginativeworld.whynotcompose.ui.screens.AppComponent
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme

@Composable
fun CardScreen() {
    CardScreenSkeleton()
}

@Preview
@Composable
fun CardScreenSkeletonPreview() {
    AppTheme {
        CardScreenSkeleton()
    }
}

@Composable
fun CardScreenSkeleton() {
    Scaffold(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            AppComponent.Header("Card")

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

            AppComponent.SubHeader("Simple Card")

            // ----------------------------------------------------------------

            Card(
                Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp, 8.dp),
                    text = "Smile! :)\n" +
                            "It's Sunnah."
                )
            }

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            var count by remember { mutableStateOf(0) }

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { count++ }) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "You Clicked This Card",
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = "$count time${if (count > 1) "s" else ""}",
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                    )
                }
            }

            // ----------------------------------------------------------------

            AppComponent.BigSpacer()

        }
    }
}
