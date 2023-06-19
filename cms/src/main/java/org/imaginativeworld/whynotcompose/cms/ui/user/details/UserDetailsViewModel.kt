package org.imaginativeworld.whynotcompose.cms.ui.user.details

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
import org.imaginativeworld.whynotcompose.cms.models.user.User
import org.imaginativeworld.whynotcompose.cms.repositories.UserRepository

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    private val _eventShowLoading = MutableStateFlow(false)

    private val _eventShowMessage = MutableStateFlow<Event<ActionMessage>?>(null)

    private val _user = MutableStateFlow<User?>(null)

    private val _eventDeleteSuccess = MutableStateFlow<Event<Boolean>?>(null)

    // ----------------------------------------------------------------

    private val _state = MutableStateFlow(UserDetailsViewState())
    val state: StateFlow<UserDetailsViewState>
        get() = _state

    // ----------------------------------------------------------------

    init {
        viewModelScope.launch {
            combine(
                _eventShowLoading,
                _eventShowMessage,
                _user,
                _eventDeleteSuccess
            ) { showLoading, showMessage, user, deleteSuccess ->

                UserDetailsViewState(
                    loading = showLoading,
                    message = showMessage,
                    user = user,
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
        userId: Int
    ) = viewModelScope.launch {
        _eventShowLoading.value = true

        try {
            val user = repository.getUser(userId)

            _user.value = user
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

    fun delete(userId: Int) = viewModelScope.launch {
        _eventShowLoading.value = true

        try {
            repository.deleteUser(userId)

            _eventDeleteSuccess.value = Event(true)
        } catch (e: ApiException) {
            _eventShowMessage.value = Event(
                ActionMessage(
                    e.message ?: "Unknown error!",
                    action = UserDetailsViewAction.USER_DELETE_ERROR
                )
            )
        }

        _eventShowLoading.value = false
    }
}

data class UserDetailsViewState(
    val loading: Boolean = false,
    val message: Event<ActionMessage>? = null,
    val user: User? = null,
    val deleteSuccess: Event<Boolean>? = null
)

enum class UserDetailsViewAction : ViewAction {
    USER_LOAD_ERROR,
    USER_DELETE_ERROR
}
