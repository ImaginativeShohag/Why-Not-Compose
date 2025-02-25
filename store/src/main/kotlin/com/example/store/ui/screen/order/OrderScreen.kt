package com.example.store.ui.screen.order

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.store.theme.StoreAppTheme
import com.example.store.ui.compositions.OrderCard
import com.example.store.ui.screen.productdetails.Product

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun OrdersScreen(
    orders: List<Order>,
    goBack: () -> Unit
) {
    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(text = "Orders") },
//                navigationIcon = {
//                    IconButton(onClick = goBack) {
//                        Icon(
//                            Icons.Default.ArrowBack,
//                            contentDescription = "Back",
//                            tint = Color.Blue
//                        )
//                    }
//                }
//            )
//        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(orders) { order ->
                OrderCard(order = order)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

data class Order(
    val id: Int,
    val date: String,
    val products: List<Product>
)

@PreviewLightDark
@Composable
private fun OrdersScreenPreview() {
    StoreAppTheme {
        val sampleOrders = listOf(
            Order(
                id = 7,
                date = "01 Mar 2020",
                listOf(
                    Product(
                        category = "T-shirt",
                        id = 2,
                        title = "Mens Casual Premium Slim Fit T-Shirts",
                        imageUrl = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
                        price = 22.3,
                        rating = 4.5,
                        reviewCount = 259,
                        description = "Slim-fitting style, contrast raglan long sleeve, three-button henley placket, light weight & soft fabric for breathable and comfortable wearing.",
                        quantity = 8
                    )
                )
            ),
            Order(
                id = 6,
                date = "01 Mar 2020",
                listOf(
                    Product(
                        category = "Backpack",
                        id = 1,
                        title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
                        imageUrl = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
                        price = 109.95,
                        rating = 4.5,
                        reviewCount = 120,
                        description = "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
                        quantity = 10
                    )
                )
            ),
            Order(
                id = 5,
                date = "01 Mar 2020",
                listOf(
                    Product(
                        category = "Jacket",
                        id = 3,
                        title = "Mens Cotton Jacket",
                        imageUrl = "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg",
                        price = 55.99,
                        rating = 4.5,
                        reviewCount = 500,
                        description = "Great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling.",
                        quantity = 5
                    )
                )
            )
        )

        OrdersScreen(
            orders = sampleOrders,
            goBack = {}
        )
    }
}
