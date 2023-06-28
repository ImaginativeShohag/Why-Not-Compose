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

package org.imaginativeworld.whynotcompose.ui.screens.composition.list

import org.imaginativeworld.whynotcompose.ui.screens.CompositionsScreen

data class ListComposition(
    val name: String,
    val route: CompositionsScreen
) {
    companion object {
        val listCompositionList = listOf(
            ListComposition(
                name = "List with Column",
                route = CompositionsScreen.CompositionListColumn
            ),
            ListComposition(
                name = "List with Row",
                route = CompositionsScreen.CompositionListRow
            ),
            ListComposition(
                name = "List with LazyColumn",
                route = CompositionsScreen.CompositionListLazyColumnIndex
            ),
            ListComposition(
                name = "List with LazyRow",
                route = CompositionsScreen.CompositionListLazyRow
            ),
            ListComposition(
                name = "Grid with LazyVerticalGrid",
                route = CompositionsScreen.CompositionListGridVertical
            )
        )
    }
}
