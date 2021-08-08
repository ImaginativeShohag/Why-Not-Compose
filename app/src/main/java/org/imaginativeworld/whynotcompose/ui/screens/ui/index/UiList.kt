package org.imaginativeworld.whynotcompose.ui.screens.ui.index

import org.imaginativeworld.whynotcompose.ui.screens.UIsScreen

data class Ui(
    val name: String,
    val route: UIsScreen,
) {
    companion object {
        val uiList = listOf(
            Ui(
                name = "Web View",
                route = UIsScreen.UiWebView,
            ),
            Ui(
                name = "Map View",
                route = UIsScreen.UiMapView,
            ),
        )
    }
}