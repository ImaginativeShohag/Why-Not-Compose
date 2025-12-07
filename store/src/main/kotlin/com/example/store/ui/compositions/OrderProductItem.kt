package com.example.store.ui.compositions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.store.models.cart.CartUIModel
import com.example.store.models.product.Product
import com.example.store.models.product.Rating
import com.example.store.theme.StoreAppTheme
import kotlin.text.format

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun OrderProductItem(
    cartItem: CartUIModel,
    modifier: Modifier = Modifier
) {
    val quantity = cartItem.quantity
    val totalPrice = cartItem.product.price * quantity
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = cartItem.product.image,
            contentDescription = "Product Image",
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = cartItem.product.title,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                color = MaterialTheme.colorScheme.outline,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("$${cartItem.product.price} × ")
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)) {
                            append("$quantity")
                        }
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )

                Text(
                    text = "$${String.format("%.2f", totalPrice)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun OrderProductItemPreview() {
    StoreAppTheme {
        val dummyProduct = Product(
            category = "Jacket",
            id = 3,
            title = "Mens Cotton Jacket",
            image = "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg",
            price = 55.99,
            rating = Rating(300, 4.5),
            description = "Great outerwear...",
            quantity = 0
        )
        OrderProductItem(
            cartItem = CartUIModel(
                product = dummyProduct,
                quantity = 5
            )
        )
    }
}
