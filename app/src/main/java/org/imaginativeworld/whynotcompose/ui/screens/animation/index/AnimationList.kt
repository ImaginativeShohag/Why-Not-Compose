package org.imaginativeworld.whynotcompose.ui.screens.animation.index

import org.imaginativeworld.whynotcompose.ui.screens.AnimationsScreen

data class Animation(
    val name: String,
    val route: AnimationsScreen,
) {
    companion object {
        val animationList = listOf(
            Animation(
                name = "Compose One",
                route = AnimationsScreen.AnimationComposeOne,
            ),
            Animation(
                name = "Emudi",
                route = AnimationsScreen.AnimationEmudi,
            ),
            Animation(
                name = "Fade-Scale",
                route = AnimationsScreen.AnimationFadeScale,
            ),
            Animation(
                name = "Running Car",
                route = AnimationsScreen.AnimationRunningCar,
            ),
        )
    }
}