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
