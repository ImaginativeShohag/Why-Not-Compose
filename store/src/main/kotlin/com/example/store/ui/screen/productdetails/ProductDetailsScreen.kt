package com.example.store.ui.screen.productdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.store.theme.StoreAppTheme
import com.example.store.ui.compositions.StoreAppBar

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun ProductDetailsScreen(
    product: Product,
    goBack: () -> Unit = {},
    toggleUIMode: () -> Unit = {}
) {
    ProductDetailsScreenSkeleton(
        product = product,
        goBack = goBack,
        toggleUIMode = toggleUIMode
    )
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun ProductDetailsScreenSkeleton(
    product: Product,
    goBack: () -> Unit = {},
    toggleUIMode: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            StoreAppBar(
                title = "Product Details",
                goBack = goBack,
                toggleUIMode = toggleUIMode
            )
        }
    ) { innerPadding ->
        var quantity by remember { mutableIntStateOf(0) }
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .background(MaterialTheme.colorScheme.outline)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = product.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1

            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = Color.Yellow
                )
                Text(
                    text = ("${product.rating} (${product.reviewCount})"),
                    modifier = Modifier.padding(start = 4.dp),
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { if (quantity > 0) quantity-- },
                        modifier = Modifier
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Blue)
                                .padding(4.dp)
                        ) {
                            Icon(
                                Icons.Default.Remove,
                                contentDescription = "Decrease",
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }
                    }

                    Text(
                        text = "$quantity",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    )

                    IconButton(
                        onClick = { quantity++ }
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Blue)
                                .padding(4.dp)
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Increase",
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }
                    }
                }
            }
        }
    }
}

data class Product(
    val category: String,
    val id: Int,
    val title: String,
    val imageUrl: String,
    val price: Double,
    val rating: Double,
    val reviewCount: Int,
    val description: String,
    var quantity: Int
)

val dummyProducts = listOf(
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
    ),
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
    ),
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
    ),
    Product(
        category = "Clothing",
        id = 4,
        title = "Mens Casual Slim Fit",
        imageUrl = "https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg",
        price = 15.99,
        rating = 4.5,
        reviewCount = 430,
        description = "The color could be slightly different between on the screen and in practice.",
        quantity = 12
    ),
    Product(
        category = "Jewelry",
        id = 5,
        title = "John Hardy Women's Legends Naga Gold & Silver Dragon Station Chain Bracelet",
        imageUrl = "https://fakestoreapi.com/img/71pWzhdJNwL._AC_UL640_QL65_ML3_.jpg",
        price = 695.0,
        rating = 4.5,
        reviewCount = 400,
        description = "From our Legends Collection, the Naga was inspired by the mythical water dragon that protects the ocean's pearl.",
        quantity = 7
    ),
    Product(
        category = "Jewelry",
        id = 6,
        title = "Solid Gold Petite Micropave",
        imageUrl = "https://fakestoreapi.com/img/61sbMiUnoGL._AC_UL640_QL65_ML3_.jpg",
        price = 168.0,
        rating = 4.5,
        reviewCount = 70,
        description = "Satisfaction Guaranteed. Return or exchange any order within 30 days.",
        quantity = 10
    ),
    Product(
        category = "Jewelry",
        id = 7,
        title = "White Gold Plated Princess",
        imageUrl = "https://fakestoreapi.com/img/71YAIFU48IL._AC_UL640_QL65_ML3_.jpg",
        price = 9.99,
        rating = 4.5,
        reviewCount = 400,
        description = "Classic Created Wedding Engagement Solitaire Diamond Promise Ring for Her.",
        quantity = 15
    ),
    Product(
        category = "Jewelry",
        id = 8,
        title = "Pierced Owl Rose Gold Plated Stainless Steel Double",
        imageUrl = "https://fakestoreapi.com/img/51UDEzMJVpL._AC_UL640_QL65_ML3_.jpg",
        price = 10.99,
        rating = 4.5,
        reviewCount = 100,
        description = "Rose Gold Plated Double Flared Tunnel Plug Earrings.",
        quantity = 3
    ),
    Product(
        category = "External Hard Drive",
        id = 9,
        title = "WD 2TB Elements Portable External Hard Drive - USB 3.0",
        imageUrl = "https://fakestoreapi.com/img/61IBBVJvSDL._AC_SY879_.jpg",
        price = 64.0,
        rating = 4.5,
        reviewCount = 203,
        description = "USB 3.0 and USB 2.0 Compatibility Fast data transfers.",
        quantity = 4
    ),
    Product(
        category = "Storage",
        id = 10,
        title = "SanDisk SSD PLUS 1TB Internal SSD - SATA III 6 Gb/s",
        imageUrl = "https://fakestoreapi.com/img/61U7T1koQqL._AC_SX679_.jpg",
        price = 109.0,
        rating = 4.7,
        reviewCount = 470,
        description = "Easy upgrade for faster boot up, shutdown, application load and response.",
        quantity = 6
    )
)

@Preview(showBackground = true)
@Composable
private fun ProductDetailsScreenSkeletonPreview() {
    StoreAppTheme {
        val sampleProduct = Product(
            category = "Storage",
            id = 10,
            title = "SanDisk SSD PLUS 1TB Internal SSD - SATA III 6 Gb/s",
            imageUrl = "https://fakestoreapi.com/img/61U7T1koQqL._AC_SX679_.jpg",
            price = 109.0,
            rating = 4.7,
            reviewCount = 470,
            description = "Easy upgrade for faster boot up, shutdown, application load and response.",
            quantity = 6
        )

        ProductDetailsScreenSkeleton(
            product = sampleProduct
        )
    }
}
