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
    private val eventShowLoading = MutableStateFlow(false)
    private val eventShowMessage = MutableStateFlow<Event<ActionMessage>?>(null)
    private val post = MutableStateFlow<Post?>(null)
    private val eventDeleteSuccess = MutableStateFlow<Event<Boolean>?>(null)

    // ----------------------------------------------------------------

    private val _state = MutableStateFlow(PostDetailsViewState())
    val state: StateFlow<PostDetailsViewState>
        get() = _state

    // ----------------------------------------------------------------

    init {
        viewModelScope.launch {
            combine(
                eventShowLoading,
                eventShowMessage,
                post,
                eventDeleteSuccess
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
        eventShowLoading.value = true

        try {
            val post = repository.getPost(PostId)

            this@PostDetailsViewModel.post.value = post
        } catch (e: ApiException) {
            eventShowMessage.value = Event(
                ActionMessage(
                    e.message ?: "Unknown error!",
                    action = PostDetailsViewAction.POST_LOAD_ERROR
                )
            )
        }

        eventShowLoading.value = false
    }

    fun deletePost(PostId: Int) = viewModelScope.launch {
        eventShowLoading.value = true

        try {
            repository.deletePost(PostId)

            eventDeleteSuccess.value = Event(true)
        } catch (e: ApiException) {
            eventShowMessage.value = Event(
                ActionMessage(e.message ?: "Unknown error!")
            )
        }

        eventShowLoading.value = false
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
