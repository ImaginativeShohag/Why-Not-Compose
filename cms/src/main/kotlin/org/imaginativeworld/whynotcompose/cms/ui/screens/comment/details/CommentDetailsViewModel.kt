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

package org.imaginativeworld.whynotcompose.cms.ui.screens.comment.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.base.models.Event
import org.imaginativeworld.whynotcompose.base.network.ApiException
import org.imaginativeworld.whynotcompose.cms.models.ActionMessage
import org.imaginativeworld.whynotcompose.cms.models.Comment
import org.imaginativeworld.whynotcompose.cms.models.ViewAction
import org.imaginativeworld.whynotcompose.cms.repositories.CommentRepository

@HiltViewModel
class CommentDetailsViewModel @Inject constructor(
    private val repository: CommentRepository
) : ViewModel() {
    private val eventShowLoading = MutableStateFlow(false)
    private val eventShowMessage = MutableStateFlow<Event<ActionMessage>?>(null)
    private val comment = MutableStateFlow<Comment?>(null)
    private val eventDeleteSuccess = MutableStateFlow<Event<Boolean>?>(null)

    // ----------------------------------------------------------------

    private val _state = MutableStateFlow(CommentDetailsViewState())
    val state = _state.asStateFlow()

    // ----------------------------------------------------------------

    init {
        viewModelScope.launch {
            combine(
                eventShowLoading,
                eventShowMessage,
                comment,
                eventDeleteSuccess
            ) { showLoading, showMessage, comment, deleteSuccess ->

                CommentDetailsViewState(
                    loading = showLoading,
                    message = showMessage,
                    comment = comment,
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
        commentId: Int
    ) = viewModelScope.launch {
        eventShowLoading.value = true

        try {
            val comment = repository.getComment(commentId)

            this@CommentDetailsViewModel.comment.value = (comment)
        } catch (e: ApiException) {
            eventShowMessage.value = Event(
                ActionMessage(
                    e.message ?: "Unknown error!",
                    action = CommentDetailsViewAction.COMMENT_LOAD_ERROR
                )
            )
        }

        eventShowLoading.value = false
    }

    fun deleteComment(commentId: Int) = viewModelScope.launch {
        eventShowLoading.value = true

        try {
            repository.deleteComment(commentId)

            eventDeleteSuccess.value = Event(true)
        } catch (e: ApiException) {
            eventShowMessage.value = Event(
                ActionMessage(e.message ?: "Unknown error!")
            )
        }

        eventShowLoading.value = false
    }
}

data class CommentDetailsViewState(
    val loading: Boolean = false,
    val message: Event<ActionMessage>? = null,
    val comment: Comment? = null,
    val deleteSuccess: Event<Boolean>? = null
)

enum class CommentDetailsViewAction : ViewAction {
    COMMENT_LOAD_ERROR
}
