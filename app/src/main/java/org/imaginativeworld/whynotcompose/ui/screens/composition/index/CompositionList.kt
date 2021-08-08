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
                name = "Code Field",
                route = CompositionsScreen.CompositionCodeField,
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
                route = CompositionsScreen.CompositionList,
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