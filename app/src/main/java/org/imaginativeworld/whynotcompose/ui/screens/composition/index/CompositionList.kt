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
                route = CompositionsScreen.CompositionsAppBar,
            ),
            Composition(
                name = "Button",
                route = CompositionsScreen.CompositionsButton,
            ),
            Composition(
                name = "Check Box",
                route = CompositionsScreen.CompositionsCheckBox,
            ),
            Composition(
                name = "Code Field",
                route = CompositionsScreen.CompositionsCodeField,
            ),
            Composition(
                name = "Dialog",
                route = CompositionsScreen.CompositionsDialog,
            ),
            Composition(
                name = "DropDown",
                route = CompositionsScreen.CompositionsDropDown,
            ),
            Composition(
                name = "List",
                route = CompositionsScreen.CompositionsList,
            ),
            Composition(
                name = "Loading",
                route = CompositionsScreen.CompositionsLoading,
            ),
            Composition(
                name = "Radio Button",
                route = CompositionsScreen.CompositionsRadioButton,
            ),
            Composition(
                name = "Snackbar",
                route = CompositionsScreen.CompositionsSnackbar,
            ),
            Composition(
                name = "Switch",
                route = CompositionsScreen.CompositionsSwitch,
            ),
            Composition(
                name = "Text Field",
                route = CompositionsScreen.CompositionsTextField,
            ),
        )
    }
}