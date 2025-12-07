package com.example.store.ui.screen.cart

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.store.models.cart.CartUIModel
import com.example.store.theme.StoreAppTheme
import com.example.store.ui.compositions.CartItemCard
import com.example.store.ui.compositions.StoreAppBar

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun CartScreen(
    onCheckout: () -> Unit,
    toggleUIMode: () -> Unit,
    viewModel: CartViewModel = hiltViewModel()
) {
    val cartItems by viewModel.cartItems.collectAsState()
    val totalPrice by viewModel.totalPrice.collectAsState()
//    val isLoading by viewModel.isLoading.collectAsState()
    CartScreenSkeleton(
        cartItems = cartItems,
        totalPrice = totalPrice,
        isLoading = false,
        onCheckout = onCheckout,
        toggleUIMode = toggleUIMode,
        onIncrement = { viewModel.incrementQuantity(it) },
        onDecrement = { viewModel.decrementQuantity(it) }
    )
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun CartScreenSkeleton(
    cartItems: List<CartUIModel>,
    totalPrice: Double,
    isLoading: Boolean,
    onCheckout: () -> Unit = {},
    toggleUIMode: () -> Unit = {},
    onIncrement: (CartUIModel) -> Unit = {},
    onDecrement: (CartUIModel) -> Unit = {}
) {
    Scaffold(
        topBar = {
            StoreAppBar(
                title = "Cart",
                toggleUIMode = toggleUIMode
            )
        }
    ) { paddingValues ->
        val context = LocalContext.current

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (cartItems.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize().padding(top = 50.dp), contentAlignment = Alignment.Center) {
                            Text(
                                text = "Your cart is empty",
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    } else {
                        cartItems.forEach { cartItem ->
                            CartItemCard(
                                product = cartItem.product,
                                quantity = cartItem.quantity,
                                onIncrement = { onIncrement(cartItem) },
                                onDecrement = { onDecrement(cartItem) }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }

                if (cartItems.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "$${"%.2f".format(totalPrice)}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        TextButton(
                            onClick = {
                                Toast.makeText(context, "Order Placed Successfully!", Toast.LENGTH_SHORT).show()
                                onCheckout()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(
                                text = "Place Order",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.surface,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CartScreenSkeletonPreview() {
    StoreAppTheme {
//        CartScreenSkeleton(
//            products = dummyProducts,
//            goBack = {},
//            onCheckout = {},
//            toggleUIMode = {}
//        )
    }
}
