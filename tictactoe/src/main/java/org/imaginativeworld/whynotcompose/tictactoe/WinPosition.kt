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