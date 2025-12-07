
package com.example.store.ui.screen.categories
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.store.models.categorie.Category
import com.example.store.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getCategories()
                val mappedCategories = response?.mapIndexed { index, name ->
                    Category(
                        id = index + 1,
                        name = name,
                        imageUrl = ""
                    )
                } ?: emptyList()

                _categories.value = mappedCategories
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}