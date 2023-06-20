/*
 * Copyright 2023 Md. Mahmudul Hasan Shohag
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

package org.imaginativeworld.whynotcompose.cms.ui.screens.comment.edit

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
class CommentEditViewModel @Inject constructor(
    private val repository: CommentRepository
) : ViewModel() {
    private val _eventShowLoading = MutableStateFlow(false)

    private val _eventShowMessage = MutableStateFlow<Event<String>?>(null)

    private val _eventUpdateCommentSuccess = MutableStateFlow<Event<Boolean>?>(null)

    private val _comment = MutableStateFlow<Comment?>(null)

    // ----------------------------------------------------------------

    private val _state = MutableStateFlow(CommentEditViewState())
    val state: StateFlow<CommentEditViewState>
        get() = _state

    // ----------------------------------------------------------------

    init {
        viewModelScope.launch {
            combine(
                _eventShowLoading,
                _eventShowMessage,
                _eventUpdateCommentSuccess,
                _comment
            ) { showLoading, showMessage, updateCommentSuccess, comment ->

                CommentEditViewState(
                    loading = showLoading,
                    message = showMessage,
                    comment = comment,
                    updateCommentSuccess = updateCommentSuccess
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

    fun getDetails(commentId: Int) = viewModelScope.launch {
        _eventShowLoading.value = true

        try {
            val comment = repository.getComment(commentId)

            _comment.value = comment
        } catch (e: ApiException) {
            _eventShowMessage.value = Event(e.message ?: "Unknown error!")
        }

        _eventShowLoading.value = false
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

    fun updateComment(
        postId: Int,
        commentId: Int,
        name: String,
        email: String,
        body: String
    ) = viewModelScope.launch {
        if (!isValid(name, email, body)) {
            return@launch
        }

        _eventShowLoading.value = true

        try {
            repository.updateComment(
                commentId,
                Comment(
                    id = commentId,
                    postId = postId,
                    name = name,
                    email = email,
                    body = body
                )
            )

            _eventUpdateCommentSuccess.value = Event(true)
        } catch (e: ApiException) {
            _eventShowMessage.value = Event(e.message ?: "Unknown error!")
        }

        _eventShowLoading.value = false
    }
}

data class CommentEditViewState(
    val loading: Boolean = false,
    val message: Event<String>? = null,
    val comment: Comment? = null,
    val updateCommentSuccess: Event<Boolean>? = null
)
