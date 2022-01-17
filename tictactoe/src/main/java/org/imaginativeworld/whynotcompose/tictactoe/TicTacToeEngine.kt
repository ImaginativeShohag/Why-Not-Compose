package org.imaginativeworld.whynotcompose.tictactoe

import timber.log.Timber

object TicTacToeEngine {

    /**
     * Example of [playingMoves]:
     *
     * 1X2X3X4X5X6X7X8X9X
     */
    fun isWin(playingMoves: String): WinPosition? {
        Timber.e("Moves: $playingMoves")

        // Horizontals
        if (playingMoves.findOrNull(1) != null &&
            playingMoves.findOrNull(1) == playingMoves.findOrNull(2) &&
            playingMoves.findOrNull(2) == playingMoves.findOrNull(3)
        ) {
            return WinPosition.H1
        }
        if (playingMoves.findOrNull(4) != null &&
            playingMoves.findOrNull(4) == playingMoves.findOrNull(5) &&
            playingMoves.findOrNull(5) == playingMoves.findOrNull(6)
        ) {
            return WinPosition.H2
        }
        if (playingMoves.findOrNull(7) != null &&
            playingMoves.findOrNull(7) == playingMoves.findOrNull(8) &&
            playingMoves.findOrNull(8) == playingMoves.findOrNull(9)
        ) {
            return WinPosition.H3
        }

        // Verticals
        if (playingMoves.findOrNull(1) != null &&
            playingMoves.findOrNull(1) == playingMoves.findOrNull(4) &&
            playingMoves.findOrNull(4) == playingMoves.findOrNull(7)
        ) {
            return WinPosition.V1
        }
        if (playingMoves.findOrNull(2) != null &&
            playingMoves.findOrNull(2) == playingMoves.findOrNull(5) &&
            playingMoves.findOrNull(5) == playingMoves.findOrNull(8)
        ) {
            return WinPosition.V2
        }
        if (playingMoves.findOrNull(3) != null &&
            playingMoves.findOrNull(3) == playingMoves.findOrNull(6) &&
            playingMoves.findOrNull(6) == playingMoves.findOrNull(9)
        ) {
            return WinPosition.V3
        }

        // Diagonals
        if (playingMoves.findOrNull(1) != null &&
            playingMoves.findOrNull(1) == playingMoves.findOrNull(5) &&
            playingMoves.findOrNull(5) == playingMoves.findOrNull(9)
        ) {
            return WinPosition.D1
        }
        if (playingMoves.findOrNull(3) != null &&
            playingMoves.findOrNull(3) == playingMoves.findOrNull(5) &&
            playingMoves.findOrNull(5) == playingMoves.findOrNull(7)
        ) {
            return WinPosition.D2
        }

        return null
    }

    private fun String.findOrNull(position: Int): String? {
        val found = this.indexOf(position.toString())

        return if (found == -1) null
        else getOrNull(found + 1)?.toString()
    }

    /**
     * Example of [playingMoves]:
     *
     * 1X2X3X4X5X6X7X8X9X
     * 012345678912345678 <- index
     */
    fun whoWin(playingMoves: String, winPosition: WinPosition): Piece {
        return Piece.valueOf(
            playingMoves[playingMoves.indexOf(winPosition.places[0].toString()) + 1].toString()
        )
    }
}