package com.example.store.ui.screen.productdetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.store.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
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
                    productId = productId
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
            try {
                val response = productRepository.getProductsId(id)
                Log.d("Log404", "loadProductDetails viewMOdel response: $response")
            } catch (e: Exception) {
                eventShowMessage.emit(Event("Failed to load product details"))
            } finally {
                eventShowLoading.emit(false)
            }
        }
    }
}

data class ProductDetailsScreenState(
    val loading: Boolean = false,
    val message: Event<String>? = null,
    val productId: Int = -1
)
