package org.imaginativeworld.whynotcompose.cms.ui.screens.post.edit

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
import org.imaginativeworld.whynotcompose.cms.models.Post
import org.imaginativeworld.whynotcompose.cms.repositories.PostRepository

@HiltViewModel
class PostEditViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {
    private val _eventShowLoading = MutableStateFlow(false)

    private val _eventShowMessage = MutableStateFlow<Event<String>?>(null)

    private val _eventUpdatePostSuccess = MutableStateFlow<Event<Boolean>?>(null)

    private val _post = MutableStateFlow<Post?>(null)

    // ----------------------------------------------------------------

    private val _state = MutableStateFlow(PostEditViewState())
    val state: StateFlow<PostEditViewState>
        get() = _state

    // ----------------------------------------------------------------

    init {
        viewModelScope.launch {
            combine(
                _eventShowLoading,
                _eventShowMessage,
                _eventUpdatePostSuccess,
                _post
            ) { showLoading, showMessage, updatePostSuccess, post ->

                PostEditViewState(
                    loading = showLoading,
                    message = showMessage,
                    post = post,
                    updatePostSuccess = updatePostSuccess
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

    fun getDetails(postId: Int) = viewModelScope.launch {
        _eventShowLoading.value = true

        try {
            val post = repository.getPost(postId)

            _post.value = post
        } catch (e: ApiException) {
            _eventShowMessage.value = Event(e.message ?: "Unknown error!")
        }

        _eventShowLoading.value = false
    }

    // ----------------------------------------------------------------

    private fun isValid(
        title: String,
        body: String
    ): Boolean {
        if (title.isBlank()) {
            _eventShowMessage.value = Event("Please enter title!")
            return false
        }

        if (body.isBlank()) {
            _eventShowMessage.value = Event("Please enter body!")
            return false
        }

        return true
    }

    fun updatePost(
        userId: Int,
        postId: Int,
        title: String,
        body: String
    ) = viewModelScope.launch {
        if (!isValid(title, body)) {
            return@launch
        }

        _eventShowLoading.value = true

        try {
            repository.updatePost(
                postId,
                Post(
                    id = 0, // This will be ignored in the server.
                    userId = userId,
                    title = title,
                    body = body
                )
            )

            _eventUpdatePostSuccess.value = Event(true)
        } catch (e: ApiException) {
            _eventShowMessage.value = Event(e.message ?: "Unknown error!")
        }

        _eventShowLoading.value = false
    }
}

data class PostEditViewState(
    val loading: Boolean = false,
    val message: Event<String>? = null,
    val post: Post? = null,
    val updatePostSuccess: Event<Boolean>? = null
)
