package org.imaginativeworld.whynotcompose.cms.ui.screens.post.add

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
class PostAddViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {
    private val _eventShowLoading = MutableStateFlow(false)

    private val _eventShowMessage = MutableStateFlow<Event<String>?>(null)

    private val _eventAddPostSuccess = MutableStateFlow<Event<Boolean>?>(null)

    // ----------------------------------------------------------------

    private val _state = MutableStateFlow(PostAddViewState())
    val state: StateFlow<PostAddViewState>
        get() = _state

    // ----------------------------------------------------------------

    init {
        viewModelScope.launch {
            combine(
                _eventShowLoading,
                _eventShowMessage,
                _eventAddPostSuccess
            ) { showLoading, showMessage, addPostSuccess ->

                PostAddViewState(
                    loading = showLoading,
                    message = showMessage,
                    addPostSuccess = addPostSuccess
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

    fun addPost(
        userId: Int,
        title: String,
        body: String
    ) = viewModelScope.launch {
        if (!isValid(title, body)) {
            return@launch
        }

        _eventShowLoading.value = true

        try {
            repository.addPost(
                userId,
                Post(
                    id = 0, // It will be ignored in the server.
                    title = title,
                    body = body,
                    userId = userId
                )
            )

            _eventAddPostSuccess.value = Event(true)
        } catch (e: ApiException) {
            _eventShowMessage.value = Event(e.message ?: "Unknown error!")
        }

        _eventShowLoading.value = false
    }
}

data class PostAddViewState(
    val loading: Boolean = false,
    val message: Event<String>? = null,
    val addPostSuccess: Event<Boolean>? = null
)
