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

package org.imaginativeworld.whynotcompose.base.models

/**
 * The observer/collector of LiveData, Channel, Flow etc. ignored consequent identical values.
 * So, before we send an event we update the `id` with an unique value. Which makes it
 * non-identical.
 *
 * @param value T The target value.
 * @param id Int The unique event id. It will be auto generated.
 */
data class Event<out T>(
    val value: T,
    private val id: Int = if (lastId == Int.MAX_VALUE) {
        lastId = Int.MIN_VALUE
        Int.MAX_VALUE
    } else {
        lastId++
    }
) {
    companion object {
        private var lastId = Int.MAX_VALUE
    }

    private var valueSent = false

    /**
     * Get the [value] only once.
     */
    fun getValueOnce(): T? {
        return if (!valueSent) {
            valueSent = true

            value
        } else {
            null
        }
    }
}
