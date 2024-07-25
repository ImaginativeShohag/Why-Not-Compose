/*
 * Copyright 2022 Md. Mahmudul Hasan Shohag
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

package org.imaginativeworld.whynotcompose.tictactoe

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TicTacToeSharedPref @Inject constructor(
    @ApplicationContext context: Context
) {

    companion object {
        private const val PREF_DATA = "data"
    }

    private val context: Context = context.applicationContext

    @Volatile
    private var sharedPref: SharedPreferences? = null

    private fun getSharedPerf(): SharedPreferences = sharedPref ?: synchronized(this) {
        context.getSharedPreferences(
            "${BuildConfig.LIBRARY_PACKAGE_NAME}.main",
            Context.MODE_PRIVATE
        )
    }

    // ----------------------------------------------------------------

    fun reset() {
        getSharedPerf().edit().clear().apply()
    }

    // ----------------------------------------------------------------

    fun setData(data: Set<String>) {
        getSharedPerf()
            .edit()
            .apply {
                putStringSet(PREF_DATA, data)
                apply()
            }
    }

    fun getData(): Set<String> = getSharedPerf().getStringSet(PREF_DATA, emptySet()) ?: emptySet()
}
