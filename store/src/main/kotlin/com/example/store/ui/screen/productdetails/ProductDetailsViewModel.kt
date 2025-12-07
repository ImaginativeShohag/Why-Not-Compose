package com.example.store.ui.screen.productdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.store.models.product.Product
import com.example.store.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.base.models.Event

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val eventShowLoading = MutableStateFlow(false)
    private val eventShowMessage = MutableStateFlow<Event<String>?>(null)
    private val productId = MutableStateFlow(-1)

    private val _state = MutableStateFlow(ProductDetailsScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                eventShowLoading,
                eventShowMessage,
                productId
            ) { showLoading, showMessage, productId ->

                ProductDetailsScreenState(
                    loading = showLoading,
                    message = showMessage,
                    product = null
                )
            }.catch { throwable ->
                eventShowMessage.emit(Event("Something went wrong"))
                throw throwable
            }.collect {
                _state.value = it
            }
        }
    }

    fun loadProductDetails(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }

            try {
                val response = productRepository.getProductsId(id)

                if (response != null) {
                    _state.update {
                        it.copy(loading = false, product = response)
                    }
                } else {
                    _state.update {
                        it.copy(loading = false, message = Event("Product not found"))
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(loading = false, message = Event("Failed to load details"))
                }
            }
        }
    }
}

data class ProductDetailsScreenState(
    val loading: Boolean = false,
    val message: Event<String>? = null,
    val product: Product? = null
)
