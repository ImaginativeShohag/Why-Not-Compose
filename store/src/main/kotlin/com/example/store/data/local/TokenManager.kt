package com.example.store.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences("store_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_TOKEN = "auth_token"
        private const val KEY_USER_FULL_NAME = "user_full_name"
    }

    fun saveToken(token: String) {
        prefs.edit { putString(KEY_TOKEN, token) }
    }

    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun saveUserFullName(name: String) {
        prefs.edit { putString(KEY_USER_FULL_NAME, name) }
    }

    fun getUserFullName(): String? = prefs.getString(KEY_USER_FULL_NAME, "John doe")

    fun clearSession() {
        prefs.edit { clear() }
    }
}
