package com.example.store.repositories

import com.example.store.models.cart.CartUIModel
import com.example.store.models.order.OrderUIModel
import com.example.store.models.product.Product
import com.example.store.network.api.CartApiInterface
import com.example.store.network.api.ProductDetailsApiInterface
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

@Singleton
class CartRepository @Inject constructor(
    private val cartApi: CartApiInterface,
    private val productDetailsApi: ProductDetailsApiInterface
) {

    private val _cartItems = MutableStateFlow<List<CartUIModel>>(emptyList())
    val cartItems = _cartItems.asStateFlow()

    fun addToCart(product: Product) {
        _cartItems.update { currentList ->
            val existingItem = currentList.find { it.product.id == product.id }

            if (existingItem != null) {
                currentList.map {
                    if (it.product.id == product.id) {
                        it.copy(quantity = it.quantity + 1)
                    } else {
                        it
                    }
                }
            } else {
                currentList + CartUIModel(product = product, quantity = 1)
            }
        }
    }

    fun removeFromCart(product: Product) {
        _cartItems.update { currentList ->
            val existingItem = currentList.find { it.product.id == product.id }

            if (existingItem != null) {
                if (existingItem.quantity > 1) {
                    currentList.map {
                        if (it.product.id == product.id) {
                            it.copy(quantity = it.quantity - 1)
                        } else {
                            it
                        }
                    }
                } else {
                    currentList.filter { it.product.id != product.id }
                }
            } else {
                currentList
            }
        }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    suspend fun getAllOrders(userId: Int): List<OrderUIModel> = withContext(Dispatchers.IO) {
        try {
            val cartResponse = cartApi.getUserCart(userId)

            if (cartResponse.isSuccessful && !cartResponse.body().isNullOrEmpty()) {
                val carts = cartResponse.body()!!

                val orders = carts.map { cart ->
                    async {
                        val productsDeferred = cart.products.map { cartItem ->
                            async {
                                val productResponse = productDetailsApi.getProductDetails(cartItem.productId)
                                if (productResponse.isSuccessful && productResponse.body() != null) {
                                    CartUIModel(
                                        product = productResponse.body()!!,
                                        quantity = cartItem.quantity
                                    )
                                } else {
                                    null
                                }
                            }
                        }

                        val products = productsDeferred.awaitAll().filterNotNull()
                        val total = products.sumOf { it.product.price * it.quantity }

                        val formattedDate = try {
                            cart.date.substring(0, 10)
                        } catch (e: Exception) {
                            cart.date
                        }

                        OrderUIModel(
                            id = cart.id,
                            date = formattedDate,
                            totalPrice = total,
                            products = products
                        )
                    }
                }
                return@withContext orders.awaitAll()
            } else {
                return@withContext emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext emptyList()
        }
    }
}
