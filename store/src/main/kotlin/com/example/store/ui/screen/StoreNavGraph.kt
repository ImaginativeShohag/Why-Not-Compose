package com.example.store.ui.screen

import LoginScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.store.ui.compositions.TabScreen
import com.example.store.ui.screen.categories.CategoriesScreen
import com.example.store.ui.screen.categorieswiseproduct.CategoriesWiseProductScreen
import com.example.store.ui.screen.home.StoreHomeScreen
import com.example.store.ui.screen.order.Order
import com.example.store.ui.screen.order.OrdersScreen
import com.example.store.ui.screen.productdetails.ProductDetailsScreen
import com.example.store.ui.screen.productdetails.dummyProducts
import com.example.store.ui.screen.profile.ProfileScreen
import com.example.store.ui.screen.splash.StoreSplashScreen
import kotlinx.serialization.Serializable
import org.imaginativeworld.whynotcompose.base.models.UIThemeMode
import org.imaginativeworld.whynotcompose.base.models.nextMode
import org.imaginativeworld.whynotcompose.base.utils.UIThemeController

sealed class SplashScreen {
    @Serializable
    object Splash
}

sealed class AuthScreen {
    @Serializable
    object Login
}

sealed class MainScreen {
    @Serializable
    object TabScreen
}

sealed class StoreScreen {
    @Serializable
    object StoreHome
}

@Serializable
sealed class CategorieScreen {
    @Serializable
    object Categories
}

@Serializable
sealed class CategoriesWiseProducts {
    @Serializable
    data class CategoriesWiseProduct(val categoryTitle: String)
}

@Serializable
sealed class DetailsScreen {
    @Serializable
    data class ProductDetails(val productId: Int)
}

@Serializable
sealed class CartScreen {
    @Serializable
    object Cart
}

@Serializable
sealed class ProfilesScreen {
    @Serializable
    object Profile
}

@Serializable
sealed class OrderScreen {
    @Serializable
    object Order
}

@Composable
fun StoreNavHost(
    navController: NavHostController,
    updateUiThemeMode: (UIThemeMode) -> Unit,
    goBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = SplashScreen.Splash
    ) {
        composable<SplashScreen.Splash> {
            StoreSplashScreen(
                gotoHomeIndex = {
                    navController.navigate(AuthScreen.Login) {
                        popUpTo(SplashScreen.Splash) { inclusive = true }
                    }
                }
            )
        }

        composable<AuthScreen.Login> {
            val isDarkMode by UIThemeController.uiThemeMode.collectAsState()

            LoginScreen(
                onLogin = {
                    navController.navigate(MainScreen.TabScreen) {
                        popUpTo(AuthScreen.Login) { inclusive = true }
                    }
                },
                toggleUIMode = {
                    updateUiThemeMode(isDarkMode.nextMode())
                }
            )
        }

        addStoreScreens(
            navController = navController,
            updateUiThemeMode = updateUiThemeMode,
            goBack = goBack
        )
    }
}

private fun NavGraphBuilder.addStoreScreens(
    navController: NavHostController,
    updateUiThemeMode: (UIThemeMode) -> Unit,
    goBack: () -> Unit
) {
    composable<MainScreen.TabScreen> {
        val isDarkMode by UIThemeController.uiThemeMode.collectAsState()

        TabScreen(
            userName = "John Doe",
            onProfileClick = {
                navController.navigate(ProfilesScreen.Profile)
            },
            product = dummyProducts,
            goBack = {
                navController.popBackStack()
            },
            toggleUIMode = {
                updateUiThemeMode(isDarkMode.nextMode())
            },
            onProductClick = { product ->
                navController.navigate(DetailsScreen.ProductDetails(product.id))
            },
            onCategoryClick = {
                navController.navigate(CategoriesWiseProducts.CategoriesWiseProduct(it.name))
            },
            onCheckout = {
                navController.navigate(MainScreen.TabScreen)
            }
        )
    }

    composable<StoreScreen.StoreHome> {
        val isDarkMode by UIThemeController.uiThemeMode.collectAsState()

        StoreHomeScreen(
            userName = "John Doe",
            onProfileClick = {
                navController.navigate(ProfilesScreen.Profile)
            },
            toggleUIMode = {
                updateUiThemeMode(isDarkMode.nextMode())
            },
            products = dummyProducts,
            onProductClick = { product ->
                navController.navigate(DetailsScreen.ProductDetails(product.id))
            },
            onCategoryClick = {
                navController.navigate(CategoriesWiseProducts.CategoriesWiseProduct(it.name))
            }
        )
    }

    composable<DetailsScreen.ProductDetails> { backStackEntry ->
        val productDetails: DetailsScreen.ProductDetails = backStackEntry.toRoute()
        val product = dummyProducts.find { it.id == productDetails.productId }
        val isDarkMode by UIThemeController.uiThemeMode.collectAsState()

        product?.let {
            ProductDetailsScreen(
                product = it,
                goBack = {
                    navController.popBackStack()
                },
                toggleUIMode = {
                    updateUiThemeMode(isDarkMode.nextMode())
                }
            )
        }
    }

    composable<CategorieScreen.Categories> {
        val isDarkMode by UIThemeController.uiThemeMode.collectAsState()
        CategoriesScreen(
            goBack = {
                navController.popBackStack()
            },
            onCategoryClick = {
                navController.navigate(CategoriesWiseProducts.CategoriesWiseProduct(it.name))
            },
            toggleUIMode = {
                updateUiThemeMode(isDarkMode.nextMode())
            }
        )
    }

    composable<CategoriesWiseProducts.CategoriesWiseProduct> { backStackEntry ->
        val categoryDetails: CategoriesWiseProducts.CategoriesWiseProduct = backStackEntry.toRoute()
        val isDarkMode by UIThemeController.uiThemeMode.collectAsState()
        val products = dummyProducts.filter { it.category == categoryDetails.categoryTitle }
        CategoriesWiseProductScreen(
            products = products,
            onProductClick = { product ->
                navController.navigate(DetailsScreen.ProductDetails(product.id))
            },
            goBack = {
                navController.popBackStack()
            },
            toggleUIMode = {
                updateUiThemeMode(isDarkMode.nextMode())
            }
        )
    }

    composable<ProfilesScreen.Profile> {
        val isDarkMode by UIThemeController.uiThemeMode.collectAsState()
        ProfileScreen(
            goBack = {},
            onOrdersClick = {
                navController.navigate(OrderScreen.Order) {
                }
            },
            onSignOutClick = {
                navController.navigate(AuthScreen.Login) {
                    popUpTo(MainScreen.TabScreen) { inclusive = true }
                }
            },
            toggleUIMode = {
                updateUiThemeMode(isDarkMode.nextMode())
            }
        )
    }

    composable<OrderScreen.Order> {
        val isDarkMode by UIThemeController.uiThemeMode.collectAsState()
        OrdersScreen(
            orders = listOf(
                Order(
                    id = 7,
                    date = "01 Mar 2020",
                    products = listOf(
                        dummyProducts[0],
                        dummyProducts[1]
                    )
                )
            ),
            goBack = {
                navController.popBackStack()
            }
        )
    }
}
