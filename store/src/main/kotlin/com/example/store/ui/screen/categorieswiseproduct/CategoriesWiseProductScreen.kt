package com.example.store.ui.screen.categorieswiseproduct

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.store.models.product.Product
import com.example.store.theme.StoreAppTheme
import com.example.store.ui.compositions.ProductItem
import com.example.store.ui.compositions.StoreAppBar
import com.example.store.ui.screen.cart.CartViewModel
import java.util.Locale

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun CategoriesWiseProductScreen(
    categoryName: String,
    onProductClick: (Product) -> Unit,
    goBack: () -> Unit,
    toggleUIMode: () -> Unit,
    viewModel: CategoriesWiseProductViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val products by viewModel.products.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val cartItems by cartViewModel.cartItems.collectAsState()

    LaunchedEffect(categoryName) {
        viewModel.loadProductsByCategory(categoryName)
    }
    Scaffold(
        topBar = {
            StoreAppBar(
                title = categoryName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                goBack = goBack,
                toggleUIMode = toggleUIMode
            )
        }
    ) { innerPadding ->

        if (isLoading) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (products.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No products found in this category")
            }
        } else {
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(products.size) { index ->
                    val product = products[index]
                    val cartItem = cartItems.find { it.product.id == product.id }
                    val quantity = cartItem?.quantity ?: 0

                    ProductItem(
                        product = product,
                        cartQuantity = quantity,
                        onClick = { onProductClick(product) },
                        onIncrement = { cartViewModel.addToCart(product) },
                        onDecrement = { cartViewModel.removeFromCart(product) },
                        modifier = Modifier
                            .then(
                                if (index % 2 == 0) {
                                    Modifier.padding(start = 16.dp)
                                } else {
                                    Modifier.padding(end = 16.dp)
                                }
                            )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CategoriesWiseProductScreenPreview() {
    StoreAppTheme {
        CategoriesWiseProductScreen(
            categoryName = "electronics",
            onProductClick = {},
            goBack = {},
            toggleUIMode = {}
        )
    }
}
