package org.imaginativeworld.whynotcompose.ui.screens.ui.pager.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiFoodBeverage
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

@Composable
fun UiPagerTheEndSection(modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Rounded.EmojiFoodBeverage,
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            "The End",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@PreviewLightDark
@Composable
private fun UiPagerTheEndSectionPreview() {
    AppTheme {
        Scaffold { innerPadding ->
            UiPagerTheEndSection(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
