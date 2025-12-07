package com.example.store.ui.compositions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.store.models.cart.CartUIModel
import com.example.store.models.order.OrderUIModel
import com.example.store.models.product.Product
import com.example.store.models.product.Rating
import com.example.store.theme.StoreAppTheme

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun OrderCard(
    order: OrderUIModel,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Order #${order.id}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = order.date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total Amount:",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "$${String.format("%.2f", order.totalPrice)}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { isExpanded = !isExpanded },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${order.products.size} Items",
                    color = MaterialTheme.colorScheme.primary
                )
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        if (isExpanded) {
            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    order.products.forEach { cartItem ->
                        OrderProductItem(cartItem = cartItem)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun OrderCardPreview() {
    StoreAppTheme {
        val dummyProduct1 = Product(
            id = 1,
            title = "Mens Cotton Jacket",
            price = 55.99,
            description = "Great outerwear for winter.",
            category = "men's clothing",
            image = "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg",
            rating = Rating(rate = 4.6, count = 500),
            quantity = 0
        )

        val dummyProduct2 = Product(
            id = 2,
            title = "Slim Fit T-Shirt",
            price = 22.30,
            description = "Casual slim fit t-shirt.",
            category = "men's clothing",
            image = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
            rating = Rating(rate = 4.6, count = 500),
            quantity = 0
        )

        val cartItem1 = CartUIModel(
            product = dummyProduct1,
            quantity = 2
        )

        val cartItem2 = CartUIModel(
            product = dummyProduct2,
            quantity = 1
        )

        val dummyOrder = OrderUIModel(
            id = 998877,
            date = "2025-12-05",
            totalPrice = 134.28,
            products = listOf(cartItem1, cartItem2)
        )

        OrderCard(
            order = dummyOrder
        )
    }
}
