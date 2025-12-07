package com.example.store.ui.compositions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.store.models.product.Product
import com.example.store.theme.StoreAppTheme

@Composable
fun CartItemCard(
    product: Product,
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    val totalItemPrice = product.price * quantity
    val priceRed = Color(0xFFFF3B30)
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    product.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        HorizontalDivider()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.weight(.4f)
            ) {
                IconButton(
                    onClick = onDecrement
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = .7f))
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
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
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                IconButton(
                    onClick = onIncrement
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = .7f))
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

            Text(
                text = "$${String.format("%.2f", totalItemPrice)}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                color = priceRed,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(.5f)
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun CartItemCardPreview() {
    StoreAppTheme {
        val dummyProduct = Product(
            id = 1,
            title = "Mens Cotton Jacket",
            price = 55.99,
            description = "Great outerwear for winter.",
            category = "men's clothing",
            image = "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg",
            rating = com.example.store.models.product.Rating(rate = 4.7, count = 500),
            quantity = 0
        )

        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            CartItemCard(
                product = dummyProduct,
                quantity = 2,
                onIncrement = {},
                onDecrement = {}
            )
        }
    }
}
