package org.imaginativeworld.whynotcompose.ui.screens.ui.index

import org.imaginativeworld.whynotcompose.ui.screens.UiScreen

data class Ui(
    val name: String,
    val route: UiScreen,
) {
    companion object {
        val uiList = listOf(
            Ui(
                name = "Web View",
                route = UiScreen.UiWebView,
            ),
            Ui(
                name = "Map View",
                route = UiScreen.UiMapView,
            ),
        )
    }
}