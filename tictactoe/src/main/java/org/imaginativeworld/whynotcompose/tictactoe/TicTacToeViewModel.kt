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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.base.extensions.combine
import org.imaginativeworld.whynotcompose.base.models.Event

@HiltViewModel
class TicTacToeViewModel @Inject constructor(
    private val sharedPref: TicTacToeSharedPref
) : ViewModel() {

    private val winPlayingMoves = mutableSetOf<String>()

    // ----------------------------------------------------------------

    private val eventShowLoading = MutableStateFlow(false)
    private val eventShowMessage = MutableStateFlow<Event<String>?>(null)
    private val eventShowToast = MutableStateFlow<Event<String>?>(null)
    private val eventPaused = MutableStateFlow(false)
    private val userWinCount = MutableStateFlow(0)
    private val aiWinCount = MutableStateFlow(0)
    private val currentPlayingMoves = MutableStateFlow("")
    private val totalNeurons = MutableStateFlow(0)
    private val winPosition = MutableStateFlow<WinPosition?>(null)

    // ----------------------------------------------------------------

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState>
        get() = _state

    // ----------------------------------------------------------------

    init {
        viewModelScope.launch {
            combine(
                eventShowLoading,
                eventShowMessage,
                eventShowToast,
                eventPaused,
                userWinCount,
                aiWinCount,
                currentPlayingMoves,
                totalNeurons,
                winPosition
            ) { showLoading, showMessage, showToast,
                paused, userWinCount, aiWinCount,
                currentPlayingMoves, totalNeurons, winPosition ->
                UiState(
                    loading = showLoading,
                    message = showMessage,
                    toast = showToast,
                    paused = paused,
                    userWinCount = userWinCount,
                    aiWinCount = aiWinCount,
                    currentPlayingMoves = currentPlayingMoves,
                    totalNeurons = totalNeurons,
                    winPosition = winPosition
                )
            }.catch { throwable ->
                // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                _state.value = it
            }
        }

        winPlayingMoves.addAll(sharedPref.getData())

        totalNeurons.value = winPlayingMoves.size
    }

    fun act(position: Int) = viewModelScope.launch {
        // ----------------------------------------------------------------
        // User's Move
        // ----------------------------------------------------------------

        // Step 1: Apply user move
        currentPlayingMoves.value += "$position${Piece.X.value}"

        // Wait for the animation to finish
        delay(300)

        // Step 2: Check winning positions for User
        TicTacToeEngine.isWin(currentPlayingMoves.value)?.let { winPosition ->

            // Save the win moves (We will call them Neuron ;) )
            winPlayingMoves.add(currentPlayingMoves.value)
            totalNeurons.value = winPlayingMoves.size

            notifyWon(winPosition)

            return@launch
        }

        // Step 3: Is it a draw?
        if (currentPlayingMoves.value.length >= 18) {
            eventPaused.value = true
            eventShowMessage.value = Event("It is a draw!")

            return@launch
        }

        // ----------------------------------------------------------------
        // AI's Move
        // ----------------------------------------------------------------

        // Step 4: Check database if current flow match any win move
        val matchedNeuron =
            winPlayingMoves.find { item -> item.startsWith(currentPlayingMoves.value) }

        // - If found: send next move
        if (matchedNeuron != null &&
            // Last 2 moves remaining
            currentPlayingMoves.value.length + 4 == matchedNeuron.length
        ) {
            currentPlayingMoves.value +=
                matchedNeuron[currentPlayingMoves.value.length + 2].toString() + Piece.O.value

            eventShowToast.value = Event("Neuron used.")

            // Check if AI win?
            TicTacToeEngine.isWin(currentPlayingMoves.value)?.let { winPosition ->
                notifyWon(winPosition)
            }

            return@launch
        }

        // - Else: send random move
        while (true) {
            val randInt = Random.nextInt(1, 10)
            if (!currentPlayingMoves.value.contains(randInt.toString())) {
                currentPlayingMoves.value += "$randInt${Piece.O.value}"
                break
            }
        }

        // Step 5: Check if AI win?
        TicTacToeEngine.isWin(currentPlayingMoves.value)?.let { winPosition ->
            notifyWon(winPosition)
        }
    }

    // ----------------------------------------------------------------

    private fun notifyWon(winPosition: WinPosition) {
        eventPaused.value = true

        val winPiece = TicTacToeEngine.whoWin(
            currentPlayingMoves.value,
            winPosition
        )

        if (winPiece == Piece.X) {
            eventShowMessage.value = Event("You won!")
            userWinCount.value++
        } else {
            eventShowMessage.value = Event("AI won!")
            aiWinCount.value++
        }

        this.winPosition.value = winPosition
    }

    // ----------------------------------------------------------------

    fun restart() = viewModelScope.launch {
        sharedPref.setData(winPlayingMoves)

        currentPlayingMoves.value = ""
        winPosition.value = null

        delay(300)

        eventPaused.value = false
    }
}

data class UiState(
    val loading: Boolean = false,
    val message: Event<String>? = null,
    val toast: Event<String>? = null,
    val paused: Boolean = true,
    val userWinCount: Int = 0,
    val aiWinCount: Int = 0,
    val currentPlayingMoves: String = "",
    val totalNeurons: Int = 0,
    val winPosition: WinPosition? = null
)
