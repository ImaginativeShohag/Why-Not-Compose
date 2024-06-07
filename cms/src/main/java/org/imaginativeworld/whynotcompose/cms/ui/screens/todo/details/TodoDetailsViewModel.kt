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

package org.imaginativeworld.whynotcompose.cms.ui.screens.todo.details

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
import org.imaginativeworld.whynotcompose.cms.models.ViewAction
import org.imaginativeworld.whynotcompose.cms.models.todo.Todo
import org.imaginativeworld.whynotcompose.cms.repositories.TodoRepository

@HiltViewModel
class TodoDetailsViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {
    private val eventShowLoading = MutableStateFlow(false)
    private val eventShowMessage = MutableStateFlow<Event<ActionMessage>?>(null)
    private val todo = MutableStateFlow<Todo?>(null)
    private val eventDeleteSuccess = MutableStateFlow<Event<Boolean>?>(null)

    // ----------------------------------------------------------------

    private val _state = MutableStateFlow(TodoDetailsViewState())
    val state: StateFlow<TodoDetailsViewState>
        get() = _state

    // ----------------------------------------------------------------

    init {
        viewModelScope.launch {
            combine(
                eventShowLoading,
                eventShowMessage,
                todo,
                eventDeleteSuccess
            ) { showLoading, showMessage, todo, deleteSuccess ->

                TodoDetailsViewState(
                    loading = showLoading,
                    message = showMessage,
                    todo = todo,
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
        todoId: Int
    ) = viewModelScope.launch {
        eventShowLoading.value = true

        try {
            val todo = repository.getTodo(todoId)

            this@TodoDetailsViewModel.todo.value = todo
        } catch (e: ApiException) {
            eventShowMessage.value = Event(
                ActionMessage(
                    e.message ?: "Unknown error!",
                    action = TodoDetailsViewAction.TODO_LOAD_ERROR
                )
            )
        }

        eventShowLoading.value = false
    }

    fun deleteTodo(todoId: Int) = viewModelScope.launch {
        eventShowLoading.value = true

        try {
            repository.deleteTodo(todoId)

            eventDeleteSuccess.value = Event(true)
        } catch (e: ApiException) {
            eventShowMessage.value = Event(
                ActionMessage(
                    e.message ?: "Unknown error!"
                )
            )
        }

        eventShowLoading.value = false
    }
}

data class TodoDetailsViewState(
    val loading: Boolean = false,
    val message: Event<ActionMessage>? = null,
    val todo: Todo? = null,
    val deleteSuccess: Event<Boolean>? = null
)

enum class TodoDetailsViewAction : ViewAction {
    TODO_LOAD_ERROR
}
