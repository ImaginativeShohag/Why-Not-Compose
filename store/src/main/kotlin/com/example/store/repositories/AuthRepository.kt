package com.example.store.repositories

import com.example.store.data.local.TokenManager
import com.example.store.models.auth.LoginRequest
import com.example.store.models.auth.UserResponse
import com.example.store.network.api.AuthApiInterface
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository @Inject constructor(
    private val authApi: AuthApiInterface,
    private val tokenManager: TokenManager
) {

    suspend fun loginUser(username: String, pass: String): Result<UserResponse> = withContext(Dispatchers.IO) {
        try {
            val loginResponse = authApi.login(LoginRequest(username, pass))

            if (loginResponse.isSuccessful && loginResponse.body() != null) {
                val token = loginResponse.body()!!.token
                tokenManager.saveToken(token)

                val usersResponse = authApi.getAllUsers()

                if (usersResponse.isSuccessful && usersResponse.body() != null) {
                    val currentUser = usersResponse.body()!!.find { it.username == username }

                    if (currentUser != null) {
                        val fullName = "${currentUser.name.firstname.replaceFirstChar { it.uppercase() }} ${currentUser.name.lastname.replaceFirstChar { it.uppercase() }}"

                        tokenManager.saveUserFullName(fullName)

                        return@withContext Result.success(currentUser)
                    } else {
                        return@withContext Result.failure(Exception("User details not found"))
                    }
                } else {
                    return@withContext Result.failure(Exception("Failed to fetch user profile"))
                }
            } else {
                return@withContext Result.failure(Exception("Invalid Credentials"))
            }
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }

    fun logout() {
        tokenManager.clearSession()
    }

    fun isUserLoggedIn(): Boolean = tokenManager.getToken() != null
}
