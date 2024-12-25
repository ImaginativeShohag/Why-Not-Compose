package org.imaginativeworld.whynotcompose.ui.screens.ui.pager.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.common.compose.theme.AppleSystemColor
import org.imaginativeworld.whynotcompose.ui.screens.ui.pager.data.ElementCategory

@Composable
fun UiPagerCategoryScreen(
    categories: List<ElementCategory>,
    selectedCategory: ElementCategory,
    onClick: (ElementCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxSize()) {
        for (category in categories) {
            CategoryButton(
                title = category.title,
                isSelected = category == selectedCategory,
                onClick = { onClick(category) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun CategoryButton(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) AppleSystemColor.Green else Color.Unspecified
        )
    ) {
        Box(
            Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.displayLarge
            )

            if (isSelected) {
                Icon(
                    Icons.Rounded.CheckCircleOutline,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopEnd)
                        .size(64.dp)
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun UiPagerCategoryScreenPreview() {
    AppTheme {
        Scaffold { innerPadding ->
            UiPagerCategoryScreen(
                modifier = Modifier.padding(innerPadding),
                categories = ElementCategory.entries,
                selectedCategory = ElementCategory.Animal,
                onClick = {}
            )
        }
    }
}
