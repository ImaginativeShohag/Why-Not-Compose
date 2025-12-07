package com.example.store.ui.screen.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.store.models.order.OrderUIModel
import com.example.store.theme.StoreAppTheme
import com.example.store.ui.compositions.OrderCard
import com.example.store.ui.compositions.StoreAppBar

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun OrdersScreen(
    goBack: () -> Unit,
    toggleUIMode: () -> Unit,
    viewModel: OrdersViewModel = hiltViewModel()
) {
    val orders by viewModel.orders.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    OrdersScreenSkeleton(
        orders = orders,
        isLoading = isLoading,
        goBack = goBack,
        toggleUIMode = toggleUIMode
    )
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun OrdersScreenSkeleton(
    orders: List<OrderUIModel>,
    isLoading: Boolean,
    goBack: () -> Unit,
    toggleUIMode: () -> Unit
) {
    Scaffold(
        topBar = {
            StoreAppBar(
                title = "Orders",
                goBack = goBack,
                toggleUIMode = toggleUIMode
            )
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (orders.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No orders found")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(orders) { order ->
                    OrderCard(order = order)
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun OrdersScreenPreview() {
    val product1 = com.example.store.models.product.Product(
        id = 1,
        title = "Fjallraven - Foldsack No. 1 Backpack",
        price = 109.95,
        description = "Your perfect pack for everyday use...",
        category = "men's clothing",
        image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
        rating = com.example.store.models.product.Rating(rate = 3.9, count = 120),
        quantity = 0
    )

    val product2 = com.example.store.models.product.Product(
        id = 2,
        title = "Mens Casual Premium Slim Fit T-Shirts",
        price = 22.3,
        description = "Slim-fitting style, contrast raglan long sleeve...",
        category = "men's clothing",
        image = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
        rating = com.example.store.models.product.Rating(rate = 4.1, count = 259),
        quantity = 0
    )
    val cartItem1 = com.example.store.models.cart.CartUIModel(product = product1, quantity = 1)
    val cartItem2 = com.example.store.models.cart.CartUIModel(product = product2, quantity = 2)

    val dummyOrders = listOf(
        OrderUIModel(
            id = 1001,
            date = "2025-12-05",
            totalPrice = 154.55,
            products = listOf(cartItem1, cartItem2)
        ),
        OrderUIModel(
            id = 1002,
            date = "2025-11-20",
            totalPrice = 109.95,
            products = listOf(cartItem1)
        ),
        OrderUIModel(
            id = 1003,
            date = "2025-10-15",
            totalPrice = 44.60,
            products = listOf(cartItem2)
        )
    )

    StoreAppTheme {
        OrdersScreenSkeleton(
            orders = dummyOrders,
            isLoading = false,
            goBack = {},
            toggleUIMode = {}
        )
    }
}
