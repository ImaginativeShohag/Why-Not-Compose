package com.example.store.ui.screen.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.store.theme.StoreAppTheme
import com.example.store.ui.compositions.CategoryCard
import com.example.store.ui.compositions.StoreAppBar

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
        topBar = {
            StoreAppBar(
                title = "Category",
                goBack = goBack,
                toggleUIMode = toggleUIMode
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
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

data class Category(
    val id: Int,
    val name: String,
    val imageUrl: String
)

val categories = listOf(
    Category(id = 1, name = "Electronics", imageUrl = "https://fakestoreapi.com/img/61IBBVJvSDL._AC_SY879_.jpg"),
    Category(id = 2, name = "Jewelery", imageUrl = "https://fakestoreapi.com/img/71pWzhdJNwL._AC_UL640_QL65_ML3_.jpg"),
    Category(id = 3, name = "Men's Clothing", imageUrl = "https://fakestoreapi.com/img/71pWzhdJNwL._AC_UL640_QL65_ML3_.jpg"),
    Category(id = 4, name = "Women's Clothing", imageUrl = "https://fakestoreapi.com/img/61IBBVJvSDL._AC_SY879_.jpg")
)

@PreviewLightDark
@Composable
private fun CategoriesScreenSkeletonPreview() {
    StoreAppTheme {
        CategoriesScreenSkeleton(
            goBack = {},
            toggleUIMode = {}
        )
    }
}
