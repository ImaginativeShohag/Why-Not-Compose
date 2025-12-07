package com.example.store.ui.screen.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.store.data.UserSession
import com.example.store.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError = _loginError.asStateFlow()

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess = _loginSuccess.asStateFlow()

    fun performLogin(username: String, pass: String) {
        if (username.isBlank() || pass.isBlank()) {
            _loginError.value = "Please fill all fields"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _loginError.value = null

            val response = authRepository.loginUser(username, pass)
            Log.d("LoginViewModel", "performLogin: $response")
            Log.d("LoginViewModel", "performLogin: ${response.isSuccess}")

            response.onSuccess { user ->
                val fullName = "${user.name.firstname.replaceFirstChar { it.uppercase() }} ${user.name.lastname.replaceFirstChar { it.uppercase() }}"
                UserSession.setUsername(fullName)
                _loginSuccess.value = true
            }.onFailure { error ->
                _loginError.value = error.message ?: "Login failed"
            }

            _isLoading.value = false
        }
    }

    fun clearError() {
        _loginError.value = null
    }
}
