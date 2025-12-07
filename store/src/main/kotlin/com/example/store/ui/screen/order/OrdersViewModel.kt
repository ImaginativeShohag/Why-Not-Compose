package com.example.store.ui.screen.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.store.models.order.OrderUIModel
import com.example.store.repositories.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val repository: CartRepository
) : ViewModel() {

    private val _orders = MutableStateFlow<List<OrderUIModel>>(emptyList())
    val orders = _orders.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        loadOrders()
    }

    private fun loadOrders() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getAllOrders(1)
            _orders.value = result
            _isLoading.value = false
        }
    }
}
