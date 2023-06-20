package org.imaginativeworld.whynotcompose.cms.ui.screens.post.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.base.models.Event
import org.imaginativeworld.whynotcompose.base.network.ApiException
import org.imaginativeworld.whynotcompose.cms.models.ActionMessage
import org.imaginativeworld.whynotcompose.cms.models.Post
import org.imaginativeworld.whynotcompose.cms.models.ViewAction
import org.imaginativeworld.whynotcompose.cms.repositories.PostRepository

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {
    private val _eventShowLoading = MutableStateFlow(false)

    private val _eventShowMessage = MutableStateFlow<Event<ActionMessage>?>(null)

    private val _post = MutableStateFlow<Post?>(null)

    private val _eventDeleteSuccess = MutableStateFlow<Event<Boolean>?>(null)

    // ----------------------------------------------------------------

    private val _state = MutableStateFlow(PostDetailsViewState())
    val state: StateFlow<PostDetailsViewState>
        get() = _state

    // ----------------------------------------------------------------

    init {
        viewModelScope.launch {
            combine(
                _eventShowLoading,
                _eventShowMessage,
                _post,
                _eventDeleteSuccess
            ) { showLoading, showMessage, post, deleteSuccess ->

                PostDetailsViewState(
                    loading = showLoading,
                    message = showMessage,
                    post = post,
                    deleteSuccess = deleteSuccess
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

    fun getDetails(
        PostId: Int
    ) = viewModelScope.launch {
        _eventShowLoading.value = true

        try {
            val post = repository.getPost(PostId)

            _post.value = post
        } catch (e: ApiException) {
            _eventShowMessage.value = Event(
                ActionMessage(
                    e.message ?: "Unknown error!",
                    action = PostDetailsViewAction.POST_LOAD_ERROR
                )
            )
        }

        _eventShowLoading.value = false
    }

    fun deletePost(PostId: Int) = viewModelScope.launch {
        _eventShowLoading.value = true

        try {
            repository.deletePost(PostId)

            _eventDeleteSuccess.value = Event(true)
        } catch (e: ApiException) {
            _eventShowMessage.value = Event(
                ActionMessage(e.message ?: "Unknown error!")
            )
        }

        _eventShowLoading.value = false
    }
}

data class PostDetailsViewState(
    val loading: Boolean = false,
    val message: Event<ActionMessage>? = null,
    val post: Post? = null,
    val deleteSuccess: Event<Boolean>? = null
)

enum class PostDetailsViewAction : ViewAction {
    POST_LOAD_ERROR
}
