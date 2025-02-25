package com.example.store.ui.compositions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.store.theme.StoreAppTheme
import com.example.store.ui.screen.categories.Category
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

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
            .haze(state = hazeState)
    ) {
        AsyncImage(
            model = category.imageUrl,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.3f),
                            Color.Transparent
                        )
                    )
                )
                .clip(RoundedCornerShape(12.dp))
                .hazeChild(state = hazeState)
        )
        Text(
            text = category.name,
           style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .hazeChild(
                    state = hazeState
                )
        )
//        Box {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    // Pass it the HazeState we stored above
//                    .hazeSource(state = hazeState)
//            ) {
//                // todo
//            }}
    }

}

@PreviewLightDark
@Composable
private fun CategoryCardPreview() {
    StoreAppTheme {
        CategoryCard(
            category = Category(
                id = 1,
                name = "Electronics",
                imageUrl = ""
            )
        )
    }
}
