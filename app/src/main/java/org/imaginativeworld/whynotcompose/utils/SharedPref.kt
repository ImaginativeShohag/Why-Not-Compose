/*
 * Copyright 2021 Md. Mahmudul Hasan Shohag
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

package org.imaginativeworld.whynotcompose.utils

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import org.imaginativeworld.whynotcompose.BuildConfig
import org.imaginativeworld.whynotcompose.models.User
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPref @Inject constructor(
    @ApplicationContext context: Context
) {

    companion object {
        private const val PREF_TOKEN = "token"
        private const val PREF_USER = "user"
        private const val PREF_DARK_MODE = "dark_mode"
    }

    private val context: Context = context.applicationContext

    @Volatile
    private var sharedPref: SharedPreferences? = null

    @Volatile
    private var user: User? = null

    private fun getSharedPerf(): SharedPreferences {
        return sharedPref ?: synchronized(this) {
            context.getSharedPreferences(
                "${BuildConfig.APPLICATION_ID}.main",
                Context.MODE_PRIVATE
            )
        }
    }

    fun reset() {
        getSharedPerf().edit().clear().apply()

        user = null
    }

    // ----------------------------------------------------------------

    fun setToken(token: String) {
        getSharedPerf()
            .edit()
            .apply {
                putString(PREF_TOKEN, token)
                apply()
            }
    }

    fun getToken(): String? {
        return getSharedPerf().getString(PREF_TOKEN, null)
    }

    // ----------------------------------------------------------------

    fun setUser(user: User) {
        getSharedPerf()
            .edit()
            .apply {
                val moshi = Moshi.Builder().build()
                val jsonAdapter = moshi.adapter(User::class.java)

                putString(PREF_USER, jsonAdapter.toJson(user))
                apply()
            }

        // Reset data if exist.
        this.user = null
    }

    fun getUser(): User? {
        return user ?: synchronized(this) {
            getSharedPerf()
                .let {
                    val moshi = Moshi.Builder().build()
                    val jsonAdapter = moshi.adapter(User::class.java)

                    val userJson = it.getString(PREF_USER, null)

                    user = if (userJson == null) {
                        null
                    } else {
                        jsonAdapter.fromJson(userJson)
                    }

                    Timber.d("user: $user")

                    user
                }
        }
    }

    // ----------------------------------------------------------------

    fun isUserLoggedIn(): Boolean {
        return getUser() != null
    }

    // ----------------------------------------------------------------

    fun setDarkMode(isDark: Boolean) {
        getSharedPerf()
            .edit()
            .apply {
                putBoolean(PREF_DARK_MODE, isDark)
                apply()
            }
    }

    fun getDarkMode(): Boolean {
        return getSharedPerf().getBoolean(PREF_DARK_MODE, false)
    }

}