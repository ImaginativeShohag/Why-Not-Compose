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
import org.imaginativeworld.whynotcompose.cms.ui.screens.user.details.UserDetailsViewAction

@HiltViewModel
class TodoDetailsViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {
    private val _eventShowLoading = MutableStateFlow(false)

    private val _eventShowMessage = MutableStateFlow<Event<ActionMessage>?>(null)

    private val _todo = MutableStateFlow<Todo?>(null)

    private val _eventDeleteSuccess = MutableStateFlow<Event<Boolean>?>(null)

    // ----------------------------------------------------------------

    private val _state = MutableStateFlow(TodoDetailsViewState())
    val state: StateFlow<TodoDetailsViewState>
        get() = _state

    // ----------------------------------------------------------------

    init {
        viewModelScope.launch {
            combine(
                _eventShowLoading,
                _eventShowMessage,
                _todo,
                _eventDeleteSuccess
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
        _eventShowLoading.value = true

        try {
            val todo = repository.getTodo(todoId)

            _todo.value = todo
        } catch (e: ApiException) {
            _eventShowMessage.value = Event(
                ActionMessage(
                    e.message ?: "Unknown error!",
                    action = UserDetailsViewAction.USER_LOAD_ERROR
                )
            )
        }

        _eventShowLoading.value = false
    }

    fun deleteTodo(todoId: Int) = viewModelScope.launch {
        _eventShowLoading.value = true

        try {
            repository.deleteTodo(todoId)

            _eventDeleteSuccess.value = Event(true)
        } catch (e: ApiException) {
            _eventShowMessage.value = Event(
                ActionMessage(
                    e.message ?: "Unknown error!"
                )
            )
        }

        _eventShowLoading.value = false
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
