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

package org.imaginativeworld.whynotcompose.cms.ui.screens.user.add

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
class UserAddViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    private val _eventShowLoading = MutableStateFlow(false)

    private val _eventShowMessage = MutableStateFlow<Event<String>?>(null)

    private val _eventAddUserSuccess = MutableStateFlow<Event<Boolean>?>(null)

    // ----------------------------------------------------------------

    private val _state = MutableStateFlow(UserAddViewState())
    val state: StateFlow<UserAddViewState>
        get() = _state

    // ----------------------------------------------------------------

    init {
        viewModelScope.launch {
            combine(
                _eventShowLoading,
                _eventShowMessage,
                _eventAddUserSuccess
            ) { showLoading, showMessage, addUserSuccess ->

                UserAddViewState(
                    loading = showLoading,
                    message = showMessage,
                    addUserSuccess = addUserSuccess
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
        name: String,
        email: String,
        gender: String
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

        return true
    }

    fun addUser(
        name: String,
        email: String,
        gender: String,
        status: String
    ) = viewModelScope.launch {
        if (!isValid(name, email, gender)) {
            return@launch
        }

        _eventShowLoading.value = true

        try {
            val newUser = repository.signIn(
                User(
                    0, // It will be ignored in the server.
                    name,
                    email,
                    gender,
                    status
                )
            )

            if (newUser != null) {
                _eventAddUserSuccess.value = Event(true)
            } else {
                _eventShowMessage.value = Event("Failed to add user!")
            }
        } catch (e: ApiException) {
            _eventShowMessage.value = Event(e.message ?: "Unknown error!")
        }

        _eventShowLoading.value = false
    }
}

data class UserAddViewState(
    val loading: Boolean = false,
    val message: Event<String>? = null,
    val addUserSuccess: Event<Boolean>? = null
)
