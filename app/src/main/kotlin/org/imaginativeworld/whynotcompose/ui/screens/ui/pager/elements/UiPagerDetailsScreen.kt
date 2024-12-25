package org.imaginativeworld.whynotcompose.ui.screens.ui.pager.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.ui.screens.ui.pager.data.Element
import org.imaginativeworld.whynotcompose.ui.screens.ui.pager.data.UiPagerRepository

@Composable
fun UiPagerDetailsScreen(
    item: Element,
    modifier: Modifier = Modifier
) {
    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color(item.dominantColor.toColorInt()).copy(0.1f))
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = item.emoji,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge
            )

            Text(
                text = item.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge
            )

            Text(
                text = item.description,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun UiPagerDetailsScreenPreview() {
    AppTheme {
        Scaffold { innerPadding ->
            UiPagerDetailsScreen(
                item = UiPagerRepository.animals.first(),
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
