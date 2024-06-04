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
    private val eventShowLoading = MutableStateFlow(false)
    private val eventShowMessage = MutableStateFlow<Event<String>?>(null)
    private val eventAddCommentSuccess = MutableStateFlow<Event<Boolean>?>(null)

    // ----------------------------------------------------------------

    private val _state = MutableStateFlow(CommentAddViewState())
    val state: StateFlow<CommentAddViewState>
        get() = _state

    // ----------------------------------------------------------------

    init {
        viewModelScope.launch {
            combine(
                eventShowLoading,
                eventShowMessage,
                eventAddCommentSuccess
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
            eventShowMessage.value = Event("Please enter name!")
            return false
        }

        if (email.isBlank()) {
            eventShowMessage.value = Event("Please enter email!")
            return false
        }

        if (!email.isValidEmail()) {
            eventShowMessage.value = Event("Please enter a valid email!")
            return false
        }

        if (body.isBlank()) {
            eventShowMessage.value = Event("Please enter body!")
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

        eventShowLoading.value = true

        try {
            // Note: The value of `id` will be ignored in the server.
            val comment = Comment(
                id = 0,
                name = name,
                email = email,
                body = body,
                postId = postId
            )

            repository.addComment(
                postId,
                comment
            )

            eventAddCommentSuccess.value = Event(true)
        } catch (e: ApiException) {
            eventShowMessage.value = Event(e.message ?: "Unknown error!")
        }

        eventShowLoading.value = false
    }
}

data class CommentAddViewState(
    val loading: Boolean = false,
    val message: Event<String>? = null,
    val addCommentSuccess: Event<Boolean>? = null
)
