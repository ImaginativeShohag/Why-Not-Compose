package com.example.store.ui.screen.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.store.models.categorie.Category
import com.example.store.theme.StoreAppTheme
import com.example.store.ui.compositions.CategoryCard
import com.example.store.ui.compositions.StoreAppBar

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun CategoriesScreen(
    onCategoryClick: (Category) -> Unit,
    toggleUIMode: () -> Unit,
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    val categories by viewModel.categories.collectAsState()
//    val isLoading by viewModel.isLoading.collectAsState()
    CategoriesScreenSkeleton(
        categories = categories,
        onCategoryClick = {
            onCategoryClick(it)
        },
        toggleUIMode = toggleUIMode
    )
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun CategoriesScreenSkeleton(
    categories: List<Category>,
    toggleUIMode: () -> Unit = {},
    onCategoryClick: (Category) -> Unit = {}
) {
    Scaffold(
        topBar = {
            StoreAppBar(
                title = "Category",
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
            if (categories.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No categories found")
                }
            } else {
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
}

@PreviewLightDark
@Composable
private fun CategoriesScreenSkeletonPreview() {
    StoreAppTheme {
        CategoriesScreenSkeleton(
            categories = listOf(),
            toggleUIMode = {}
        )
    }
}
