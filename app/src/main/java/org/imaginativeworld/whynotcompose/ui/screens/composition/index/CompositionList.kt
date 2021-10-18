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

package org.imaginativeworld.whynotcompose.ui.screens.composition.index

import org.imaginativeworld.whynotcompose.ui.screens.CompositionsScreen

data class Composition(
    val name: String,
    val route: CompositionsScreen,
) {
    companion object {
        val compositionList = listOf(
            Composition(
                name = "App Bar",
                route = CompositionsScreen.CompositionAppBar,
            ),
            Composition(
                name = "Button",
                route = CompositionsScreen.CompositionButton,
            ),
            Composition(
                name = "Card",
                route = CompositionsScreen.CompositionCard,
            ),
            Composition(
                name = "Check Box",
                route = CompositionsScreen.CompositionCheckBox,
            ),
            Composition(
                name = "Dialog",
                route = CompositionsScreen.CompositionDialog,
            ),
            Composition(
                name = "DropDown Menu",
                route = CompositionsScreen.CompositionDropDownMenu,
            ),
            Composition(
                name = "List",
                route = CompositionsScreen.CompositionListIndex,
            ),
            Composition(
                name = "ListItem",
                route = CompositionsScreen.CompositionListItem,
            ),
            Composition(
                name = "Loading Indicator",
                route = CompositionsScreen.CompositionLoadingIndicator,
            ),
            Composition(
                name = "Radio Button",
                route = CompositionsScreen.CompositionRadioButton,
            ),
            Composition(
                name = "Scaffold",
                route = CompositionsScreen.CompositionScaffoldIndex,
            ),
            Composition(
                name = "Snackbar",
                route = CompositionsScreen.CompositionSnackbar,
            ),
            Composition(
                name = "Switch",
                route = CompositionsScreen.CompositionSwitch,
            ),
            Composition(
                name = "Text Field",
                route = CompositionsScreen.CompositionTextField,
            ),
        )
    }
}
