package org.imaginativeworld.whynotcompose.cms.ui.user.edit

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
import org.imaginativeworld.whynotcompose.cms.models.user.User
import org.imaginativeworld.whynotcompose.cms.repositories.UserRepository

@HiltViewModel
class UserEditViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    private val _eventShowLoading = MutableStateFlow(false)

    private val _eventShowMessage = MutableStateFlow<Event<String>?>(null)

    private val _eventUpdateUserSuccess = MutableStateFlow<Event<Boolean>?>(null)

    private val _user = MutableStateFlow<User?>(null)

    // ----------------------------------------------------------------

    private val _state = MutableStateFlow(UserEditViewState())
    val state: StateFlow<UserEditViewState>
        get() = _state

    // ----------------------------------------------------------------

    init {
        viewModelScope.launch {
            combine(
                _eventShowLoading,
                _eventShowMessage,
                _eventUpdateUserSuccess,
                _user
            ) { showLoading, showMessage, updateUserSuccess, user ->

                UserEditViewState(
                    loading = showLoading,
                    message = showMessage,
                    user = user,
                    updateUserSuccess = updateUserSuccess
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
        // Reset
        _user.value = null

        // Call API
        _eventShowLoading.value = true

        try {
            val user = repository.getUser(userId)

            _user.value = user
        } catch (e: ApiException) {
            _eventShowMessage.value = Event(e.message ?: "Unknown error!")
        }

        _eventShowLoading.value = false
    }

    // ----------------------------------------------------------------

    private fun isValid(
        name: String,
        email: String,
        gender: String,
        status: String
    ): Boolean {
        if (name.isBlank()) {
            _eventShowMessage.value = Event("Please enter your name!")
            return false
        }

        if (email.isBlank()) {
            _eventShowMessage.value = Event("Please enter your email!")
            return false
        }

        if (!email.isValidEmail()) {
            _eventShowMessage.value = Event("Please enter a valid email!")
            return false
        }

        if (gender.isBlank()) {
            _eventShowMessage.value = Event("Please select your gender!")
            return false
        }

        if (status.isBlank()) {
            _eventShowMessage.value = Event("Please select user status!")
            return false
        }

        return true
    }

    fun update(
        userId: Int,
        name: String,
        email: String,
        gender: String,
        status: String
    ) = viewModelScope.launch {
        if (!isValid(name, email, gender, status)) {
            return@launch
        }

        _eventShowLoading.value = true

        try {
            repository.updateUser(
                userId,
                User(
                    userId,
                    name,
                    email,
                    gender,
                    status
                )
            )

            _eventUpdateUserSuccess.value = Event(true)
        } catch (e: ApiException) {
            _eventShowMessage.value = Event(e.message ?: "Unknown error!")
        }

        _eventShowLoading.value = false
    }
}

data class UserEditViewState(
    val loading: Boolean = false,
    val message: Event<String>? = null,
    val user: User? = null,
    val updateUserSuccess: Event<Boolean>? = null
)
