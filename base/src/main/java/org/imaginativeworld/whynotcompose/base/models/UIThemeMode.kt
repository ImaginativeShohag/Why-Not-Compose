/**
 * Copyright © 2023 Imaginative World. All rights reserved.
 */

package org.imaginativeworld.whynotcompose.base.models

enum class UIThemeMode(val value: Int) {
    AUTO(0),
    LIGHT(1),
    DARK(2);

    companion object {
        private val map = entries.associateBy(UIThemeMode::value)

        fun fromValue(value: Int): UIThemeMode? {
            return map[value]
        }
    }
}

fun UIThemeMode.humanReadable() = when (this) {
    UIThemeMode.AUTO -> "Auto"
    UIThemeMode.LIGHT -> "Light"
    UIThemeMode.DARK -> "Dark"
}

fun UIThemeMode.nextMode() = when (this) {
    UIThemeMode.AUTO -> UIThemeMode.LIGHT
    UIThemeMode.LIGHT -> UIThemeMode.DARK
    UIThemeMode.DARK -> UIThemeMode.AUTO
}
