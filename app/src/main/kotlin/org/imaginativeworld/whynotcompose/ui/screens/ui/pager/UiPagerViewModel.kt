package org.imaginativeworld.whynotcompose.ui.screens.ui.pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.ui.screens.ui.pager.data.Element
import org.imaginativeworld.whynotcompose.ui.screens.ui.pager.data.ElementCategory
import org.imaginativeworld.whynotcompose.ui.screens.ui.pager.data.UiPagerDataSource

@HiltViewModel
class UiPagerViewModel @Inject constructor() : ViewModel() {
    private var categories = MutableStateFlow<List<ElementCategory>>(ElementCategory.entries)
    private var items = MutableStateFlow<Flow<PagingData<Element>>>(emptyFlow())
    private var selectedCategory = MutableStateFlow<ElementCategory>(ElementCategory.Animal)

    // ----------------------------------------------------------------

    private val _state = MutableStateFlow(UiPagerViewState())
    val state = _state.asStateFlow()

    // ----------------------------------------------------------------

    init {
        viewModelScope.launch {
            combine(
                categories,
                items,
                selectedCategory
            ) { categories, items, selectedCategory ->
                UiPagerViewState(
                    categories = categories,
                    items = items,
                    selectedCategory = selectedCategory
                )
            }.collect {
                _state.value = it
            }
        }
    }

    // ----------------------------------------------------------------
    // ----------------------------------------------------------------

    fun load() {
        items.value = Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UiPagerDataSource(
                    type = selectedCategory.value
                )
            }
        )
            .flow
            .cachedIn(viewModelScope)
    }

    fun selectCategory(category: ElementCategory) {
        selectedCategory.value = category
        load()
    }
}

data class UiPagerViewState(
    val categories: List<ElementCategory> = emptyList(),
    val items: Flow<PagingData<Element>> = emptyFlow(),
    val selectedCategory: ElementCategory = ElementCategory.Animal
)
