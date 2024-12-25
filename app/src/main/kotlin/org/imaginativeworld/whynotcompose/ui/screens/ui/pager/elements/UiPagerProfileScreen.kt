package org.imaginativeworld.whynotcompose.ui.screens.ui.pager.elements

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.common.compose.theme.AppleSystemColor

@Composable
fun UiPagerProfileScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier
                    .size(200.dp)
                    .padding(16.dp)
                    .border(4.dp, AppleSystemColor.Gray, RoundedCornerShape(32.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "\uD83E\uDDD1\u200D\uD83D\uDCBB",
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayLarge
                )
            }

            Text(
                text = "Mahmudul Hasan Shohag",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayMedium
            )

            Text(
                text = "@ImaginativeShohag",
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Monospace,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun UiPagerProfileScreenPreview() {
    AppTheme {
        Scaffold { innerPadding ->
            UiPagerProfileScreen(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
