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
    private val _eventShowLoading = MutableStateFlow(false)

    private val _eventShowMessage = MutableStateFlow<Event<String>?>(null)

    private val _eventAddTodoSuccess = MutableStateFlow<Event<Boolean>?>(null)

    // ----------------------------------------------------------------

    private val _state = MutableStateFlow(TodoAddViewState())
    val state: StateFlow<TodoAddViewState>
        get() = _state

    // ----------------------------------------------------------------

    init {
        viewModelScope.launch {
            combine(
                _eventShowLoading,
                _eventShowMessage,
                _eventAddTodoSuccess
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
            _eventShowMessage.value = Event("Please enter title!")
            return false
        }

        if (status.isBlank()) {
            _eventShowMessage.value = Event("Please select status!")
            return false
        }

        if (dueDate == null) {
            _eventShowMessage.value = Event("Please select due date!")
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

        _eventShowLoading.value = true

        try {
            repository.addTodo(
                userId,
                Todo(
                    title = title,
                    dueOn = dueDate,
                    status = status.lowercase()
                )
            )

            _eventAddTodoSuccess.value = Event(true)
        } catch (e: ApiException) {
            _eventShowMessage.value = Event(e.message ?: "Unknown error!")
        }

        _eventShowLoading.value = false
    }
}

data class TodoAddViewState(
    val loading: Boolean = false,
    val message: Event<String>? = null,
    val addTodoSuccess: Event<Boolean>? = null
)
