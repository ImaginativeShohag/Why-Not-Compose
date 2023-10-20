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

package org.imaginativeworld.whynotcompose.cms.ui.screens.todo.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.base.models.Event
import org.imaginativeworld.whynotcompose.base.network.ApiException
import org.imaginativeworld.whynotcompose.cms.models.todo.Todo
import org.imaginativeworld.whynotcompose.cms.repositories.TodoRepository

@HiltViewModel
class TodoEditViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    private val eventShowLoading = MutableStateFlow(false)

    private val eventShowMessage = MutableStateFlow<Event<String>?>(null)

    private val eventUpdateTodoSuccess = MutableStateFlow<Event<Boolean>?>(null)

    private val todo = MutableStateFlow<Todo?>(null)

    // ----------------------------------------------------------------

    private val _state = MutableStateFlow(TodoEditViewState())
    val state: StateFlow<TodoEditViewState>
        get() = _state

    // ----------------------------------------------------------------

    init {
        viewModelScope.launch {
            combine(
                eventShowLoading,
                eventShowMessage,
                eventUpdateTodoSuccess,
                todo
            ) { showLoading, showMessage, updateTodoSuccess, todo ->

                TodoEditViewState(
                    loading = showLoading,
                    message = showMessage,
                    todo = todo,
                    updateTodoSuccess = updateTodoSuccess
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

    fun getDetails(todoId: Int) = viewModelScope.launch {
        eventShowLoading.value = true

        try {
            val todo = repository.getTodo(todoId)

            this@TodoEditViewModel.todo.value = todo
        } catch (e: ApiException) {
            eventShowMessage.value = Event(e.message ?: "Unknown error!")
        }

        eventShowLoading.value = false
    }

    // ----------------------------------------------------------------

    private fun isValid(
        title: String,
        dueDate: Date?,
        status: String
    ): Boolean {
        if (title.isBlank()) {
            eventShowMessage.value = Event("Please enter title!")
            return false
        }

        if (status.isBlank()) {
            eventShowMessage.value = Event("Please select status!")
            return false
        }

        if (dueDate == null) {
            eventShowMessage.value = Event("Please select due date!")
            return false
        }

        return true
    }

    fun update(
        userId: Int,
        todoId: Int,
        title: String,
        dueDate: Date?,
        status: String
    ) = viewModelScope.launch {
        if (!isValid(title, dueDate, status)) {
            return@launch
        }

        eventShowLoading.value = true

        try {
            repository.updateTodo(
                todoId,
                Todo(
                    userId = userId,
                    title = title,
                    dueOn = dueDate,
                    status = status.lowercase()
                )
            )

            eventUpdateTodoSuccess.value = Event(true)
        } catch (e: ApiException) {
            eventShowMessage.value = Event(e.message ?: "Unknown error!")
        }

        eventShowLoading.value = false
    }
}

data class TodoEditViewState(
    val loading: Boolean = false,
    val message: Event<String>? = null,
    val todo: Todo? = null,
    val updateTodoSuccess: Event<Boolean>? = null
)
