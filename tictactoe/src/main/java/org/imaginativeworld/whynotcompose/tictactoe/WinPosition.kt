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

sealed class WinPosition(val places: List<Int>) {
    object H1 : WinPosition(listOf(1, 2, 3))
    object H2 : WinPosition(listOf(4, 5, 6))
    object H3 : WinPosition(listOf(7, 8, 9))

    object V1 : WinPosition(listOf(1, 4, 7))
    object V2 : WinPosition(listOf(2, 5, 8))
    object V3 : WinPosition(listOf(3, 6, 9))

    object D1 : WinPosition(listOf(1, 5, 9))
    object D2 : WinPosition(listOf(3, 5, 7))
}
