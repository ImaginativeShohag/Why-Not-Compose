package com.example.store.ui.screen.cart

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.store.theme.StoreAppTheme
import com.example.store.ui.compositions.CartItemCard
import com.example.store.ui.screen.productdetails.Product
import com.example.store.ui.screen.productdetails.dummyProducts

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun CartScreen(
    products: List<Product>,
    goBack: () -> Unit,
    onCheckout: () -> Unit,
    toggleUIMode: () -> Unit
) {
    CartScreenSkeleton(
        products = products,
        goBack = goBack,
        onCheckout = onCheckout,
        toggleUIMode = toggleUIMode
    )
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun CartScreenSkeleton(
    products: List<Product>,
    goBack: () -> Unit,
    onCheckout: () -> Unit = {},
    toggleUIMode: () -> Unit = {}
) {
    Scaffold(
        topBar = {
//            TopAppBar(
//                title = {},
//                navigationIcon = {},
//                actions = {
//                    IconButton(onClick = goBack) {
//                        Icon(Icons.Default.Menu, contentDescription = "Back")
//                    }
//                }
//            )
//            StoreAppBar(
//                title = "Store Overflow",
//                goBack = goBack,
//                toggleUIMode = toggleUIMode
//            )
        }
    ) { paddingValues ->
        val context = LocalContext.current
        val cartItems = remember { mutableStateListOf(*products.toTypedArray()) }
        val totalPrice by remember { mutableDoubleStateOf(cartItems.sumOf { it.price * it.quantity }) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Cart",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (cartItems.isEmpty()) {
                    Text(
                        text = "Your cart is empty",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                } else {
                    cartItems.forEach { product ->
                        CartItemCard(
                            product = product,
                            onQuantityChange = { newQuantity ->
                                if (newQuantity == 0) {
                                    cartItems.remove(product)
                                } else {
                                    product.quantity = newQuantity
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "$${"%.2f".format(totalPrice)}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(
                    onClick = {
                        Toast.makeText(context, "Order Placed Complete", Toast.LENGTH_SHORT).show()
                        onCheckout()
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = "Place Order",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CartScreenSkeletonPreview() {
    StoreAppTheme {
        CartScreenSkeleton(
            products = dummyProducts,
            goBack = {},
            onCheckout = {},
            toggleUIMode = {}
        )
    }
}
