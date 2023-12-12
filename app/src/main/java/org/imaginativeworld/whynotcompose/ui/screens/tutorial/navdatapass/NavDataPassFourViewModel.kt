package org.imaginativeworld.whynotcompose.ui.screens.tutorial.navdatapass

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.imaginativeworld.whynotcompose.datasource.cache.MemoryCache
import org.imaginativeworld.whynotcompose.datasource.cache.MemoryCacheKey
import org.imaginativeworld.whynotcompose.models.DemoData

class NavDataPassFourViewModel : ViewModel() {
    private val _data = MutableStateFlow<DemoData?>(null)
    val data = _data.asStateFlow()

    init {
        _data.value = MemoryCache.get(MemoryCacheKey.DataOne)
    }
}
