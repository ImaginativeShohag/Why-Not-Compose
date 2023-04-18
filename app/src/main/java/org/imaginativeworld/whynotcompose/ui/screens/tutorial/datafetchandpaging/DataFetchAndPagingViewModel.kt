/*
 * Copyright 2021 Md. Mahmudul Hasan Shohag
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ------------------------------------------------------------------------
 *
 * Project: Why Not Compose!
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * Source: https://github.com/ImaginativeShohag/Why-Not-Compose
 */

package org.imaginativeworld.whynotcompose.ui.screens.tutorial.datafetchandpaging

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.base.models.Event
import org.imaginativeworld.whynotcompose.datasource.GithubRepoDataSource
import org.imaginativeworld.whynotcompose.models.github.GithubRepo
import org.imaginativeworld.whynotcompose.repositories.AppRepository
import timber.log.Timber

@HiltViewModel
class DataFetchAndPagingViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _eventShowLoading = MutableStateFlow(false)

    private val _eventShowMessage = MutableStateFlow<Event<String>?>(null)

    private var _items = MutableStateFlow<Flow<PagingData<GithubRepo>>>(emptyFlow())

    // ----------------------------------------------------------------

    private val _state = MutableStateFlow(ListViewState())
    val state: StateFlow<ListViewState>
        get() = _state

    // ----------------------------------------------------------------

    init {
        viewModelScope.launch {
            combine(
                _eventShowLoading,
                _eventShowMessage,
                _items
            ) { showLoading, showMessage, items ->

                ListViewState(
                    loading = showLoading,
                    message = showMessage,
                    items = items
                )
            }.catch { throwable ->
                // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                _state.value = it
            }
        }
    }

    // ----------------------------------------------------------------
    // ----------------------------------------------------------------

    val searchText = mutableStateOf(TextFieldValue("Jetpack Compose"))

    // ----------------------------------------------------------------

    private var openSearchTrueOnce = false
    val openSearch = mutableStateOf(false)

    fun setOpenSearch(isOpenSearch: Boolean) {
        Timber.e("setOpenSearch: $isOpenSearch")
        if (openSearchTrueOnce && isOpenSearch) {
            return
        }

        if (isOpenSearch) {
            openSearchTrueOnce = true
        }

        openSearch.value = isOpenSearch
    }

    // ----------------------------------------------------------------

    private var prevSearchResult: Flow<PagingData<GithubRepo>>? = null

    private val cacheQuery = Query()

    private var searchJob: Job? = null

    private fun checkIsSameQueryAndCache(currentQuery: Query): Boolean {
        Timber.e("query: $currentQuery")

        if (currentQuery == cacheQuery) {
            return true
        }

        cacheQuery.query = currentQuery.query

        return false
    }

    fun loadPosts(
        query: String?,
        delayRequest: Boolean = !query.isNullOrBlank()

    ) {
        Timber.e("query: %s", query)

        // --------------------------------
        // Check if all fields are empty
        // --------------------------------

        if (query.isNullOrBlank()) {
            _items.value = emptyFlow()

            return
        }

        // --------------------------------
        // Check if all fields are same
        // --------------------------------
        val isSame = checkIsSameQueryAndCache(
            Query(
                query = query
            )
        )

        if (prevSearchResult != null && isSame) {
            _items.value = prevSearchResult ?: emptyFlow()

            return
        }

        // --------------------------------
        // New data, so cancel old request if any.
        // --------------------------------

        searchJob?.cancel()

        // --------------------------------
        // Let's call the API
        // --------------------------------

        searchJob = viewModelScope.launch {
            if (delayRequest) {
                delay(300)
            }

            prevSearchResult = Pager(
                config = PagingConfig(
                    pageSize = 10,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = {
                    GithubRepoDataSource(
                        repository = repository,
                        query = query
                    )
                }
            )
                .flow
                .cachedIn(viewModelScope)

            _items.value = prevSearchResult ?: emptyFlow()
        }
    }
}

data class ListViewState(
    val loading: Boolean = false,
    val message: Event<String>? = null,
    val items: Flow<PagingData<GithubRepo>> = emptyFlow()
)

private data class Query(
    var query: String? = null
)
