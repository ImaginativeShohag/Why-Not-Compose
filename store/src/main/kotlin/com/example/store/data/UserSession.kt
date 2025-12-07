package com.example.store.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object UserSession {
    private val _username = MutableStateFlow("John doe")
    val username = _username.asStateFlow()

    fun setUsername(name: String) {
        _username.value = name
    }
}
