package com.example.store.ui.screen.categorieswiseproduct

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.store.theme.StoreAppTheme
import com.example.store.ui.compositions.ProductItem
import com.example.store.ui.compositions.StoreAppBar
import com.example.store.ui.screen.productdetails.Product
import com.example.store.ui.screen.productdetails.dummyProducts

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun CategoriesWiseProductScreen(
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    goBack: () -> Unit,
    toggleUIMode: () -> Unit
) {
    Scaffold(
        topBar = {
            StoreAppBar(
                title = "Category Wise Product",
                goBack = goBack,
                toggleUIMode = toggleUIMode
            )
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(dummyProducts.size) { index ->
                val product = dummyProducts[index]
                ProductItem(
                    product = product,
                    onClick = { onProductClick(product) },
                    modifier = Modifier
                )
            }
        }
    }
}

@Preview
@Composable
private fun CategoriesWiseProductScreenPreview() {
    StoreAppTheme {
        val products = dummyProducts
        CategoriesWiseProductScreen(
            products = products,
            onProductClick = {},
            goBack = {},
            toggleUIMode = {}
        )
    }
}
