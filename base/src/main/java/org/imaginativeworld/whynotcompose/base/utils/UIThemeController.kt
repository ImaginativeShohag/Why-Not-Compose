package org.imaginativeworld.whynotcompose.base.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * This singleton is for updating and observing dark mode UI.
 */
object UIThemeController {
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean>
        get() = _isDarkMode

    fun updateUITheme(isDarkMode: Boolean) {
        _isDarkMode.value = isDarkMode
    }
}
