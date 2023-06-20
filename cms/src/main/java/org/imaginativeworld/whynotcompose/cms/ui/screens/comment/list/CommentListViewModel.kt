package org.imaginativeworld.whynotcompose.cms.ui.screens.comment.list

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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.base.models.Event
import org.imaginativeworld.whynotcompose.cms.datasource.CommentPagingSource
import org.imaginativeworld.whynotcompose.cms.models.Comment
import org.imaginativeworld.whynotcompose.cms.repositories.CommentRepository

@HiltViewModel
class CommentListViewModel @Inject constructor(
    private val CommentRepository: CommentRepository
) : ViewModel() {

    private val _eventShowLoading = MutableStateFlow(false)

    private val _eventShowMessage = MutableStateFlow<Event<String>?>(null)

    private var _items = MutableStateFlow<Flow<PagingData<Comment>>>(emptyFlow())

    // ----------------------------------------------------------------

    private val _state = MutableStateFlow(CommentListViewState())
    val state: StateFlow<CommentListViewState>
        get() = _state

    // ----------------------------------------------------------------

    init {
        viewModelScope.launch {
            combine(
                _eventShowLoading,
                _eventShowMessage,
                _items
            ) { showLoading, showMessage, items ->

                CommentListViewState(
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

    fun loadComments(postId: Int) {
        _items.value = Pager(PagingConfig(pageSize = 20)) {
            CommentPagingSource(postId, CommentRepository)
        }
            .flow
            .cachedIn(viewModelScope)
    }
}

data class CommentListViewState(
    val loading: Boolean = false,
    val message: Event<String>? = null,
    val items: Flow<PagingData<Comment>> = emptyFlow()
)
