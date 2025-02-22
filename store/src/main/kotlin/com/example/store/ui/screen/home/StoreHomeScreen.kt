package com.example.store.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SportsBaseball
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.store.theme.StoreAppTheme
import com.example.store.ui.compositions.ProductItem
import com.example.store.ui.screen.categories.Category
import com.example.store.ui.screen.categories.categories
import com.example.store.ui.screen.productdetails.Product
import com.example.store.ui.screen.productdetails.dummyProducts

@Composable
fun StoreHomeScreen(
    userName: String,
    onCategoryClick: (Category) -> Unit,
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    toggleUIMode: () -> Unit
) {
    StoreHomeSkeleton(
        userName = userName,
        categories = categories,
        products = products,
        onCategoryClick = onCategoryClick,
        onProductClick = onProductClick,
        toggleUIMode = toggleUIMode
    )
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun StoreHomeSkeleton(
    userName: String,
    categories: List<Category>,
    products: List<Product>,
    onCategoryClick: (Category) -> Unit,
    onProductClick: (Product) -> Unit,
    toggleUIMode: () -> Unit
) {
    Scaffold(
//        topBar = {
//            StoreAppBar(
//                toggleUIMode = toggleUIMode
//            )
//        },
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Welcome, $userName",
                    style = MaterialTheme.typography.headlineMedium,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                )

                Image(
                    painter = painterResource(org.imaginativeworld.whynotcompose.common.compose.R.drawable.store),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(homeScreenImages.size) {
                    HomeScreenImage(
                        image = homeScreenImages[it],
                        modifier = Modifier
                            .width(300.dp)
                            .height(150.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categories.size) {
                    CategoryItem(
                        category = categories[it],
                        modifier = Modifier,
                        onClick = {
                            onCategoryClick(categories[it])
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(products.size) { index ->
                    ProductItem(
                        product = products[index],
                        onClick = {
                            onProductClick(products[index])
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: Category,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .clickable(
                onClick = {
                    onClick()
                }
            ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp,
                    horizontal = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.SportsBaseball,
                contentDescription = ""
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = category.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun HomeScreenImage(
    image: HomeScreenImage,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = image.imageUrl,
            contentDescription = "Product Image",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
                .background(color = MaterialTheme.colorScheme.outline)
        )
    }
}

data class HomeScreenImage(
    val imageUrl: String
)

val homeScreenImages = listOf(
    HomeScreenImage(imageUrl = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"),
    HomeScreenImage(imageUrl = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg"),
    HomeScreenImage(imageUrl = "https://fakestoreapi.com/img/61IBBVJvSDL._AC_SY879._SX._UX._SY._UY_.jpg")
)

@PreviewLightDark
@Composable
private fun StoreHomeScreenPreview() {
    StoreAppTheme {
        StoreHomeScreen(
            userName = "John Doe",
            onCategoryClick = {},
            products = dummyProducts,
            onProductClick = {},
            toggleUIMode = {}
        )
    }
}
