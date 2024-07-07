/*
 * Copyright 2024 Md. Mahmudul Hasan Shohag
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

package org.imaginativeworld.whynotcompose.base.extensions

import android.content.SharedPreferences
import java.util.Date

// ----------------------------------------------------------------
// String
// ----------------------------------------------------------------

fun SharedPreferences.set(key: String, value: String?) {
    edit()
        .apply {
            if (value == null) {
                remove(key)
            } else {
                putString(key, value)
            }
            apply()
        }
}

fun SharedPreferences.get(key: String, default: String?): String? = getString(key, default)

// ----------------------------------------------------------------
// String Set
// ----------------------------------------------------------------

fun SharedPreferences.set(key: String, value: Set<String>?) {
    edit()
        .apply {
            if (value == null) {
                remove(key)
            } else {
                putStringSet(key, value)
            }
            apply()
        }
}

fun SharedPreferences.get(key: String, default: Set<String>?): Set<String>? =
    getStringSet(key, default)

// ----------------------------------------------------------------
// Boolean
// ----------------------------------------------------------------

fun SharedPreferences.set(key: String, value: Boolean?) {
    edit()
        .apply {
            if (value == null) {
                remove(key)
            } else {
                putBoolean(key, value)
            }
            apply()
        }
}

fun SharedPreferences.get(key: String, default: Boolean = false): Boolean = getBoolean(key, default)

// ----------------------------------------------------------------
// Int
// ----------------------------------------------------------------

fun SharedPreferences.set(key: String, value: Int?) {
    edit()
        .apply {
            if (value == null) {
                remove(key)
            } else {
                putInt(key, value)
            }
            apply()
        }
}

fun SharedPreferences.get(key: String, default: Int): Int = getInt(key, default)

// ----------------------------------------------------------------
// Long
// ----------------------------------------------------------------

fun SharedPreferences.set(key: String, value: Long?) {
    edit()
        .apply {
            if (value == null) {
                remove(key)
            } else {
                putLong(key, value)
            }
            apply()
        }
}

fun SharedPreferences.get(key: String, default: Long): Long = getLong(key, default)

// ----------------------------------------------------------------
// Float
// ----------------------------------------------------------------

fun SharedPreferences.set(key: String, value: Float?) {
    edit()
        .apply {
            if (value == null) {
                remove(key)
            } else {
                putFloat(key, value)
            }
            apply()
        }
}

fun SharedPreferences.get(key: String, default: Float): Float = getFloat(key, default)

// ----------------------------------------------------------------
// Date
// ----------------------------------------------------------------

fun SharedPreferences.set(key: String, value: Date?) {
    edit()
        .apply {
            if (value == null) {
                remove(key)
            } else {
                putLong(key, value.time)
            }
            apply()
        }
}

fun SharedPreferences.getDate(key: String, default: Date? = null): Date? {
    getLong(key, -1).let { value ->
        return if (value >= 0) {
            Date(value)
        } else {
            default
        }
    }
}
