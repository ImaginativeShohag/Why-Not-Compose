package com.example.store.ui.screen.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun CategoriesScreen(
    goBack: () -> Unit,
    onCategoryClick: (Category) -> Unit,
    toggleUIMode: () -> Unit
) {
    CategoriesScreenSkeleton(
        goBack = goBack,
        onCategoryClick = {
            onCategoryClick(it)
        },
        toggleUIMode = toggleUIMode
    )
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun CategoriesScreenSkeleton(
    goBack: () -> Unit = {},
    toggleUIMode: () -> Unit = {},
    onCategoryClick: (Category) -> Unit = {}
) {
    Scaffold(
//        topBar = {
//            StoreAppBar(
//                title = "Category",
//                goBack = goBack,
//                toggleUIMode = toggleUIMode
//            )
//        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Categories",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            LazyVerticalGrid(
                modifier = Modifier,
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories.size) { index ->
                    val category = categories[index]
                    CategoryCard(
                        category = category,
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            onCategoryClick(category)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryCard(
    category: Category,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .size(150.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onClick()
            }
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
        )
        Text(
            text = category.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

data class Category(
    val id: Int,
    val name: String,
    val imageUrl: String
)

val categories = listOf(
    Category(id = 1, name = "Electronics", imageUrl = "https://fakestoreapi.com/img/61IBBVJvSDL._AC_SY879_.jpg"),
    Category(id = 2, name = "Jewelery", imageUrl = "https://fakestoreapi.com/img/71pWzhdJNwL._AC_UL640_QL65_ML3_.jpg"),
    Category(id = 3, name = "Men's Clothing", imageUrl = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops"),
    Category(id = 4, name = "Women's Clothing", imageUrl = "https://fakestoreapi.com/img/61pHAEJ4NML._AC_UX679_.jpg")
)

@PreviewLightDark
@Composable
private fun CategoriesScreenSkeletonPreview() {
    StoreAppTheme {
        CategoriesScreenSkeleton(
            goBack = {},
            toggleUIMode = {}
        )

//        CategoryCard(
//            category = Category(
//                id = 1,
//                name = "Electronics",
//                imageUrl = ""
//            )
//        )
    }
}
