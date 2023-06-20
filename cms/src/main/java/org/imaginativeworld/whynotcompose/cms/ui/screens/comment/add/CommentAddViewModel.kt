package org.imaginativeworld.whynotcompose.cms.ui.screens.comment.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.base.extensions.isValidEmail
import org.imaginativeworld.whynotcompose.base.models.Event
import org.imaginativeworld.whynotcompose.base.network.ApiException
import org.imaginativeworld.whynotcompose.cms.models.Comment
import org.imaginativeworld.whynotcompose.cms.repositories.CommentRepository

@HiltViewModel
class CommentAddViewModel @Inject constructor(
    private val repository: CommentRepository
) : ViewModel() {
    private val _eventShowLoading = MutableStateFlow(false)

    private val _eventShowMessage = MutableStateFlow<Event<String>?>(null)

    private val _eventAddCommentSuccess = MutableStateFlow<Event<Boolean>?>(null)

    // ----------------------------------------------------------------

    private val _state = MutableStateFlow(CommentAddViewState())
    val state: StateFlow<CommentAddViewState>
        get() = _state

    // ----------------------------------------------------------------

    init {
        viewModelScope.launch {
            combine(
                _eventShowLoading,
                _eventShowMessage,
                _eventAddCommentSuccess
            ) { showLoading, showMessage, addCommentSuccess ->

                CommentAddViewState(
                    loading = showLoading,
                    message = showMessage,
                    addCommentSuccess = addCommentSuccess
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
        name: String,
        email: String,
        body: String
    ): Boolean {
        if (name.isBlank()) {
            _eventShowMessage.value = Event("Please enter name!")
            return false
        }

        if (email.isBlank()) {
            _eventShowMessage.value = Event("Please enter email!")
            return false
        }

        if (!email.isValidEmail()) {
            _eventShowMessage.value = Event("Please enter a valid email!")
            return false
        }

        if (body.isBlank()) {
            _eventShowMessage.value = Event("Please enter body!")
            return false
        }

        return true
    }

    fun addComment(
        postId: Int,
        name: String,
        email: String,
        body: String
    ) = viewModelScope.launch {
        if (!isValid(name, email, body)) {
            return@launch
        }

        _eventShowLoading.value = true

        try {
            repository.addComment(
                postId,
                Comment(
                    id = 0, // It will be ignored in the server.
                    name = name,
                    email = email,
                    body = body,
                    postId = postId
                )
            )

            _eventAddCommentSuccess.value = Event(true)
        } catch (e: ApiException) {
            _eventShowMessage.value = Event(e.message ?: "Unknown error!")
        }

        _eventShowLoading.value = false
    }
}

data class CommentAddViewState(
    val loading: Boolean = false,
    val message: Event<String>? = null,
    val addCommentSuccess: Event<Boolean>? = null
)
