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
