package com.example.store.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.store.datasource.ProductPagingSource
import com.example.store.models.categorie.Category
import com.example.store.models.product.Product
import com.example.store.repositories.CategoriesRepository
import com.example.store.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.base.models.Event

@HiltViewModel
class StoreHomeScreenViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoriesRepository: CategoriesRepository
) : ViewModel() {

    private val eventShowLoading = MutableStateFlow(false)
    private val eventShowMessage = MutableStateFlow<Event<String>?>(null)
    private var items = MutableStateFlow<Flow<PagingData<Product>>>(emptyFlow())
    private val categories = MutableStateFlow<List<Category>>(emptyList())

    // ---------------- UI State ------------------

    private val _state = MutableStateFlow(StoreHomeScreenState())
    val state = _state.asStateFlow()

    // ---------------- Combine States ------------------

    init {
        viewModelScope.launch {
            combine(
                eventShowLoading,
                eventShowMessage,
                items,
                categories
            ) { showLoading, showMessage, items, categories ->

                StoreHomeScreenState(
                    loading = showLoading,
                    message = showMessage,
                    items = items,
                    categories = categories
                )
            }.catch { throwable ->
                eventShowMessage.emit(Event("Something went wrong"))
                throw throwable
            }.collect {
                Log.d("Log404", "Collecting UI state: ${it.items}")
                _state.value = it
            }
        }

        loadProducts()
        Log.d("Log404", "Collecting UI state: ${loadProducts()}")
    }

    // ---------------- Load Products ------------------

    fun loadProducts() {
        items.value = Pager(PagingConfig(pageSize = 10)) {
            ProductPagingSource(productRepository)
        }
            .flow
            .cachedIn(viewModelScope)
    }

    fun loadCategories() {
        viewModelScope.launch {
            try {
                val response = categoriesRepository.getCategories()
                val categoriesList = response?.mapIndexed { index, name ->
                    Category(id = index + 1, name = name, imageUrl = "")
                } ?: emptyList()

                categories.value = categoriesList
            } catch (e: Exception) {
                eventShowMessage.emit(Event("Failed to load categories"))
            } finally {
                eventShowLoading.emit(false)
            }
        }
    }
}

data class StoreHomeScreenState(
    val loading: Boolean = false,
    val message: Event<String>? = null,
    val items: Flow<PagingData<Product>> = emptyFlow(),
    val categories: List<Category> = emptyList()
)
