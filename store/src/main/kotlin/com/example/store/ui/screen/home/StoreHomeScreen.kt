package com.example.store.ui.screen.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.grid.GridItemSpan
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.example.store.models.product.Product
import com.example.store.theme.StoreAppTheme
import com.example.store.ui.compositions.ProductItem
import com.example.store.ui.compositions.StoreAppBar
import com.example.store.ui.screen.categories.Category
import com.example.store.ui.screen.categories.categories
import com.example.store.ui.screen.profile.ProfileScreen

@Suppress("ktlint:compose:param-order-check")
@Composable
fun StoreHomeScreen(
    viewModel: StoreHomeScreenViewModel,
    userName: String,
    onOrderClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onCategoryClick: (Category) -> Unit,
    onProductClick: (Product) -> Unit,
    toggleUIMode: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    val pagedProducts = state.items.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.loadProducts()
    }

    StoreHomeSkeleton(
        userName = userName,
        onOrdersClick = onOrderClick,
        onSignOutClick = onSignOutClick,
        categories = categories,
        products = pagedProducts,
        onCategoryClick = onCategoryClick,
        onProductClick = onProductClick,
        toggleUIMode = toggleUIMode
    )
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun StoreHomeSkeleton(
    userName: String,
    onOrdersClick: () -> Unit,
    onSignOutClick: () -> Unit,
    categories: List<Category>,
    products: LazyPagingItems<Product>,
    onCategoryClick: (Category) -> Unit,
    onProductClick: (Product) -> Unit,
    toggleUIMode: () -> Unit
) {
    var showProfileSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            StoreAppBar(
                toggleUIMode = toggleUIMode
            )
        },
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Welcome, $userName",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                        )

                        Image(
                            painter = painterResource(org.imaginativeworld.whynotcompose.common.compose.R.drawable.store),
                            contentDescription = "",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .clickable {
                                    showProfileSheet = true
                                },
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(
                            horizontal = 16.dp
                        )
                    ) {
                        items(homeScreenImages.size) {
                            HomeScreenImage(
                                image = homeScreenImages[it],
                                modifier = Modifier
                                    .width(300.dp)
                                    .height(150.dp)
                                    .background(MaterialTheme.colorScheme.surface)
                            )
                        }
                    }
                }

                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(
                            horizontal = 16.dp
                        )
                    ) {
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
                }
                items(products.itemCount) { index ->
                    val product = products[index]
                    if (product != null) {
                        Log.d("Log404", "StoreHomeSkeleton: $product")
                        ProductItem(
                            product = product,
                            onClick = {
                                onProductClick(product)
                            },
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
    if (showProfileSheet) {
        ProfileScreen(
            onDismiss = { showProfileSheet = false },
            onOrdersClick = onOrdersClick,
            onSignOutClick = onSignOutClick
        )
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
                style = MaterialTheme.typography.bodyMedium,
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
private fun StoreHomeSkeletonPreview() {
    StoreAppTheme {
//        StoreHomeSkeleton(
//            userName = "Shihab",
//            onOrdersClick = {},
//            onSignOutClick = {},
//            categories = categories,
//            products = {},
//            onCategoryClick = {},
//            onProductClick = {},
//            toggleUIMode = {}
//        )
    }
}
