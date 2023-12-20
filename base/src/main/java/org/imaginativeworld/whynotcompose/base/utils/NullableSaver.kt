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

package org.imaginativeworld.whynotcompose.base.utils

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable

/**
 * Saver to store nullable value in [rememberSaveable].
 *
 * @param T value data type
 */
fun <T> nullableSaver() = Saver<NullableSaverWrapper<T>, Any>(
    save = { state -> state.value ?: "null" },
    restore = { value ->
        @Suppress("UNCHECKED_CAST")
        NullableSaverWrapper((if (value == "null") null else value) as T)
    }
)

/**
 * This class is used to wrap nullable value to save using [rememberSaveable].
 *
 * @param T value data type
 * @property value the value
 */
data class NullableSaverWrapper<T>(
    val value: T?
)
