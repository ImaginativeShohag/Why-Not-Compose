package com.example.store.ui.compositions

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.store.theme.StoreAppTheme
import com.example.store.ui.screen.cart.CartScreen
import com.example.store.ui.screen.categories.CategoriesScreen
import com.example.store.ui.screen.categories.Category
import com.example.store.ui.screen.home.StoreHomeScreen
import com.example.store.ui.screen.productdetails.Product
import com.example.store.ui.screen.productdetails.dummyProducts

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun TabScreen(
    userName: String,
    onOrderClick: () -> Unit,
    onSignOutClick: () -> Unit,
    product: List<Product>,
    goBack: () -> Unit,
    toggleUIMode: () -> Unit,
    onCheckout: () -> Unit,
    onProductClick: (Product) -> Unit,
    onCategoryClick: (Category) -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavigationItems().forEach { navigationItem ->
                    NavigationBarItem(
                        selected = currentRoute == navigationItem.route,
                        label = { Text(navigationItem.label) },
                        icon = {
                            Icon(
                                navigationItem.icon,
                                contentDescription = navigationItem.label
                            )
                        },
                        onClick = {
                            if (currentRoute != navigationItem.route) {
                                navController.navigate(navigationItem.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        },
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screens.Home.route) {
                StoreHomeScreen(
                    userName = userName,
                    onOrderClick = onOrderClick,
                    onSignOutClick = onSignOutClick,
                    toggleUIMode = toggleUIMode,
                    products = product,
                    onProductClick = onProductClick,
                    onCategoryClick = onCategoryClick
                )
            }
            composable(Screens.Categories.route) {
                CategoriesScreen(
                    goBack = goBack,
                    toggleUIMode = toggleUIMode,
                    onCategoryClick = onCategoryClick
                )
            }
            composable(Screens.Cart.route) {
                CartScreen(
                    products = product,
                    goBack = goBack,
                    onCheckout = onCheckout,
                    toggleUIMode = toggleUIMode
                )
            }
        }
    }
}

data class NavigationItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

fun bottomNavigationItems(): List<NavigationItem> = listOf(
    NavigationItem("Home", Icons.Filled.Home, Screens.Home.route),
    NavigationItem("Categories", Icons.Filled.Category, Screens.Categories.route),
    NavigationItem("Cart", Icons.Filled.ShoppingCart, Screens.Cart.route)
)

sealed class Screens(val route: String) {
    object Home : Screens("home")
    object Categories : Screens("categories")
    object Cart : Screens("cart")
}

@PreviewLightDark
@Composable
private fun TabScreenPreview() {
    StoreAppTheme {
        TabScreen(
            userName = "John Doe",
            onOrderClick = {},
            onSignOutClick = {},
            product = dummyProducts,
            goBack = {},
            toggleUIMode = {},
            onCheckout = {},
            onProductClick = {},
            onCategoryClick = {}
        )
    }
}
