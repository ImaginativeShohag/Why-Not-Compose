package org.imaginativeworld.whynotcompose.tictactoe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.base.extensions.combine
import org.imaginativeworld.whynotcompose.base.models.Event
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class TicTacToeViewModel @Inject constructor(
    private val sharedPref: TicTacToeSharedPref
) : ViewModel() {

    private val winPlayingMoves = mutableSetOf<String>()

    // ----------------------------------------------------------------

    private val _eventShowLoading = MutableStateFlow(false)
    private val _eventShowMessage = MutableStateFlow<Event<String>?>(null)
    private val _eventShowToast = MutableStateFlow<Event<String>?>(null)
    private val _eventPaused = MutableStateFlow(false)
    private val _userWinCount = MutableStateFlow(0)
    private val _aiWinCount = MutableStateFlow(0)
    private val _currentPlayingMoves = MutableStateFlow("")
    private val _totalNeurons = MutableStateFlow(0)
    private val _winPosition = MutableStateFlow<WinPosition?>(null)

    // ----------------------------------------------------------------

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState>
        get() = _state

    // ----------------------------------------------------------------

    init {
        viewModelScope.launch {
            combine(
                _eventShowLoading,
                _eventShowMessage,
                _eventShowToast,
                _eventPaused,
                _userWinCount,
                _aiWinCount,
                _currentPlayingMoves,
                _totalNeurons,
                _winPosition,
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
                    winPosition = winPosition,
                )
            }.catch { throwable ->
                // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                _state.value = it
            }
        }

        winPlayingMoves.addAll(sharedPref.getData())

        _totalNeurons.value = winPlayingMoves.size
    }

    fun act(position: Int) = viewModelScope.launch {
        // ----------------------------------------------------------------
        // User's Move
        // ----------------------------------------------------------------

        // Step 1: Apply user move
        _currentPlayingMoves.value += "$position${Piece.X.value}"

        // Wait for the animation to finish
        delay(300)

        // Step 2: Check winning positions for User
        TicTacToeEngine.isWin(_currentPlayingMoves.value)?.let { winPosition ->

            // Save the win moves (We will call them Neuron ;) )
            winPlayingMoves.add(_currentPlayingMoves.value)
            _totalNeurons.value = winPlayingMoves.size

            notifyWon(winPosition)

            return@launch
        }

        // Step 3: Is it a draw?
        if (_currentPlayingMoves.value.length >= 18) {

            _eventPaused.value = true
            _eventShowMessage.value = Event("It is a draw!")

            return@launch
        }

        // ----------------------------------------------------------------
        // AI's Move
        // ----------------------------------------------------------------

        // Step 4: Check database if current flow match any win move
        val matchedNeuron =
            winPlayingMoves.find { item -> item.startsWith(_currentPlayingMoves.value) }

        // - If found: send next move
        if (matchedNeuron != null &&
            // Last 2 moves remaining
            _currentPlayingMoves.value.length + 4 == matchedNeuron.length
        ) {
            _currentPlayingMoves.value +=
                matchedNeuron[_currentPlayingMoves.value.length + 2].toString() + Piece.O.value

            _eventShowToast.value = Event("Neuron used.")

            // Check if AI win?
            TicTacToeEngine.isWin(_currentPlayingMoves.value)?.let { winPosition ->
                notifyWon(winPosition)
            }

            return@launch
        }

        // - Else: send random move
        while (true) {
            val randInt = Random.nextInt(1, 10)
            if (!_currentPlayingMoves.value.contains(randInt.toString())) {
                _currentPlayingMoves.value += "$randInt${Piece.O.value}"
                break
            }
        }

        // Step 5: Check if AI win?
        TicTacToeEngine.isWin(_currentPlayingMoves.value)?.let { winPosition ->
            notifyWon(winPosition)
        }
    }

    // ----------------------------------------------------------------

    private fun notifyWon(winPosition: WinPosition) {
        _eventPaused.value = true

        val winPiece = TicTacToeEngine.whoWin(
            _currentPlayingMoves.value,
            winPosition
        )

        if (winPiece == Piece.X) {
            _eventShowMessage.value = Event("You won!")
            _userWinCount.value++
        } else {
            _eventShowMessage.value = Event("AI won!")
            _aiWinCount.value++
        }

        _winPosition.value = winPosition
    }

    // ----------------------------------------------------------------

    fun restart() = viewModelScope.launch {
        sharedPref.setData(winPlayingMoves)

        _currentPlayingMoves.value = ""
        _winPosition.value = null

        delay(300)

        _eventPaused.value = false
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
    val winPosition: WinPosition? = null,
)

