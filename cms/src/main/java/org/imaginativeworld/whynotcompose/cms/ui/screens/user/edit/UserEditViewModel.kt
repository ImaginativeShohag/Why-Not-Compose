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

package org.imaginativeworld.whynotcompose.cms.ui.screens.user.edit

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
    private val eventShowLoading = MutableStateFlow(false)

    private val eventShowMessage = MutableStateFlow<Event<String>?>(null)

    private val eventUpdateUserSuccess = MutableStateFlow<Event<Boolean>?>(null)

    private val user = MutableStateFlow<User?>(null)

    // ----------------------------------------------------------------

    private val _state = MutableStateFlow(UserEditViewState())
    val state: StateFlow<UserEditViewState>
        get() = _state

    // ----------------------------------------------------------------

    init {
        viewModelScope.launch {
            combine(
                eventShowLoading,
                eventShowMessage,
                eventUpdateUserSuccess,
                user
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
        user.value = null

        // Call API
        eventShowLoading.value = true

        try {
            val user = repository.getUser(userId)

            this@UserEditViewModel.user.value = user
        } catch (e: ApiException) {
            eventShowMessage.value = Event(e.message ?: "Unknown error!")
        }

        eventShowLoading.value = false
    }

    // ----------------------------------------------------------------

    private fun isValid(
        name: String,
        email: String,
        gender: String,
        status: String
    ): Boolean {
        if (name.isBlank()) {
            eventShowMessage.value = Event("Please enter your name!")
            return false
        }

        if (email.isBlank()) {
            eventShowMessage.value = Event("Please enter your email!")
            return false
        }

        if (!email.isValidEmail()) {
            eventShowMessage.value = Event("Please enter a valid email!")
            return false
        }

        if (gender.isBlank()) {
            eventShowMessage.value = Event("Please select your gender!")
            return false
        }

        if (status.isBlank()) {
            eventShowMessage.value = Event("Please select user status!")
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

        eventShowLoading.value = true

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

            eventUpdateUserSuccess.value = Event(true)
        } catch (e: ApiException) {
            eventShowMessage.value = Event(e.message ?: "Unknown error!")
        }

        eventShowLoading.value = false
    }
}

data class UserEditViewState(
    val loading: Boolean = false,
    val message: Event<String>? = null,
    val user: User? = null,
    val updateUserSuccess: Event<Boolean>? = null
)
