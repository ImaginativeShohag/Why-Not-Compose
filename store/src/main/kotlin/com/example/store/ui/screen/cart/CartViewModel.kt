package com.example.store.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.store.models.cart.CartUIModel
import com.example.store.models.product.Product
import com.example.store.repositories.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    val cartItems: StateFlow<List<CartUIModel>> = cartRepository.cartItems
        .onStart {
            _isLoading.value = true
            delay(500)
        }
        .onEach {
            _isLoading.value = false
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val totalPrice: StateFlow<Double> = cartItems.map { items ->
        items.sumOf { it.product.price * it.quantity }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    fun addToCart(product: Product) {
        viewModelScope.launch {
            _isLoading.value = true
            delay(200)
            cartRepository.addToCart(product)
            _isLoading.value = false
        }
    }

    fun removeFromCart(product: Product) {
        viewModelScope.launch {
            _isLoading.value = true
            delay(200)
            cartRepository.removeFromCart(product)
            _isLoading.value = false
        }
    }

    fun incrementQuantity(cartItem: CartUIModel) {
        addToCart(cartItem.product)
    }

    fun decrementQuantity(cartItem: CartUIModel) {
        removeFromCart(cartItem.product)
    }

    fun clearCart() {
        viewModelScope.launch {
            _isLoading.value = true
            delay(500)
            cartRepository.clearCart()
            _isLoading.value = false
        }
    }
}
