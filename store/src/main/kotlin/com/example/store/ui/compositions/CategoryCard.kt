package com.example.store.ui.compositions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.store.theme.StoreAppTheme
import com.example.store.ui.screen.categories.Category
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze

@Composable
fun CategoryCard(
    category: Category,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val hazeState = remember { HazeState() }
    Box(
        modifier = modifier
            .size(150.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onClick()
            }
    ) {
        Image(
            painter = painterResource(id = org.imaginativeworld.whynotcompose.common.compose.R.drawable.store),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Box(
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(20))
                .align(Alignment.Center)
                .padding(16.dp)
                .clip(RoundedCornerShape(20))
                .background(MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.7f))
                .haze(state = hazeState),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun CategoryCardPreview() {
    StoreAppTheme {
        CategoryCard(
            category = Category(
                id = 1,
                name = " Women's Clothing",
                imageUrl = ""
            )
        )
    }
}
