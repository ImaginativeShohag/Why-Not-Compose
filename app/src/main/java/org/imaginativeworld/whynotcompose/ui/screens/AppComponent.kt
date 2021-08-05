package org.imaginativeworld.whynotcompose.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

object AppComponent {

    @Composable
    fun Header(
        text: String
    ) {
        Text(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 32.dp,
                    end = 16.dp,
                    bottom = 32.dp
                )
                .fillMaxWidth(),
            text = text,
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
        )
    }

    @Composable
    fun SubHeader(
        text: String
    ) {
        Text(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .fillMaxWidth(),
            text = text,
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center,
        )
    }

    @Composable
    fun MediumSpacer() {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
    }

    @Composable
    fun BigSpacer() {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )
    }

}