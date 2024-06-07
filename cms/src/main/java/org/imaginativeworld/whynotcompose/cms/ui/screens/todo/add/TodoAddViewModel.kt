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

package org.imaginativeworld.whynotcompose.cms.ui.screens.todo.add

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
class TodoAddViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {
    private val eventShowLoading = MutableStateFlow(false)
    private val eventShowMessage = MutableStateFlow<Event<String>?>(null)
    private val eventAddTodoSuccess = MutableStateFlow<Event<Boolean>?>(null)

    // ----------------------------------------------------------------

    private val _state = MutableStateFlow(TodoAddViewState())
    val state: StateFlow<TodoAddViewState>
        get() = _state

    // ----------------------------------------------------------------

    init {
        viewModelScope.launch {
            combine(
                eventShowLoading,
                eventShowMessage,
                eventAddTodoSuccess
            ) { showLoading, showMessage, addTodoSuccess ->

                TodoAddViewState(
                    loading = showLoading,
                    message = showMessage,
                    addTodoSuccess = addTodoSuccess
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

    fun addTodo(
        userId: Int,
        title: String,
        dueDate: Date?,
        status: String
    ) = viewModelScope.launch {
        if (!isValid(title, dueDate, status)) {
            return@launch
        }

        eventShowLoading.value = true

        try {
            repository.addTodo(
                userId,
                Todo(
                    title = title,
                    dueOn = dueDate,
                    status = status.lowercase(),
                    userId = userId
                )
            )

            eventAddTodoSuccess.value = Event(true)
        } catch (e: ApiException) {
            eventShowMessage.value = Event(e.message ?: "Unknown error!")
        }

        eventShowLoading.value = false
    }
}

data class TodoAddViewState(
    val loading: Boolean = false,
    val message: Event<String>? = null,
    val addTodoSuccess: Event<Boolean>? = null
)
