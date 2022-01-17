package org.imaginativeworld.whynotcompose.tictactoe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.base.extensions.combine
import org.imaginativeworld.whynotcompose.base.models.Event
import timber.log.Timber
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class TicTacToeViewModel @Inject constructor(
    private val sharedPref: TicTacToeSharedPref
) : ViewModel() {

    private val winPlayingMoves = mutableSetOf<String>()

    private val _eventShowLoading = MutableStateFlow(false)
    private val _eventShowMessage = MutableStateFlow<Event<String>?>(null)
    private val _eventPaused = MutableStateFlow(false)
    private val _userWinCount = MutableStateFlow(0)
    private val _aiWinCount = MutableStateFlow(0)
    private val _currentPlayingMoves = MutableStateFlow("")
    private val _totalNeurons = MutableStateFlow(0)

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
                _eventPaused,
                _userWinCount,
                _aiWinCount,
                _currentPlayingMoves,
                _totalNeurons,
            ) { showLoading, showMessage, paused, userWinCount, aiWinCount, currentPlayingMoves, totalNeurons ->
                UiState(
                    loading = showLoading,
                    message = showMessage,
                    paused = paused,
                    userWinCount = userWinCount,
                    aiWinCount = aiWinCount,
                    currentPlayingMoves = currentPlayingMoves,
                    totalNeurons = totalNeurons,
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

    /**
     *  1  2  3
     *  4  5  6
     *  7  8  9
     */
    fun act(position: Int) = viewModelScope.launch {
        _currentPlayingMoves.value += "$position${Piece.X.value}"

        // Check winning positions
        val isWin = isWin(_currentPlayingMoves.value)

        // - If user win: save and notify
        if (isWin) {
            // User Win!!!!

            winPlayingMoves.add(_currentPlayingMoves.value)
            _totalNeurons.value = winPlayingMoves.size

            _userWinCount.value++

            _eventPaused.value = true
            _eventShowMessage.value = Event("Win: User")

            return@launch
        }

        // Is it draw?
        if (_currentPlayingMoves.value.length >= 18) {

            _eventPaused.value = true
            _eventShowMessage.value = Event("Win: Draw")

            return@launch
        }

        // - If ai win: save and notify

        // Check database if current flow match any win move
        val isAnyMatch =
            winPlayingMoves.find { item -> item.startsWith(_currentPlayingMoves.value) }

        // - If found: send next move
        if (isAnyMatch != null &&
            // Last 2 moves remaining
            _currentPlayingMoves.value.length + 4 == isAnyMatch.length
        ) {
            _currentPlayingMoves.value +=
                isAnyMatch[_currentPlayingMoves.value.length + 2].toString() + Piece.O.value

            // Check if AI win?
            if (isWin(_currentPlayingMoves.value)) {

                _aiWinCount.value++

                _eventPaused.value = true
                _eventShowMessage.value = Event("Win: AI")
            }

            return@launch
        }

        // - else: send random move
        while (true) {
            val randInt = Random.nextInt(1, 10)
            if (!_currentPlayingMoves.value.contains(randInt.toString())) {
                _currentPlayingMoves.value += "$randInt${Piece.O.value}"
                break
            }
        }

        // Check if AI win?
        if (isWin(_currentPlayingMoves.value)) {

            _aiWinCount.value++

            _eventPaused.value = true
            _eventShowMessage.value = Event("Win: AI")
        }
    }

    /**
     * 1X2X3X4X5X6X7X8X9X
     */
    private fun isWin(playingMoves: String): Boolean {
        Timber.e("Moves: $playingMoves")

        // Horizontals
        if (playingMoves.findOrNull(1) != null &&
            playingMoves.findOrNull(1) == playingMoves.findOrNull(2) &&
            playingMoves.findOrNull(2) == playingMoves.findOrNull(3)
        ) {
            return true
        }
        if (playingMoves.findOrNull(4) != null &&
            playingMoves.findOrNull(4) == playingMoves.findOrNull(5) &&
            playingMoves.findOrNull(5) == playingMoves.findOrNull(6)
        ) {
            return true
        }
        if (playingMoves.findOrNull(7) != null &&
            playingMoves.findOrNull(7) == playingMoves.findOrNull(8) &&
            playingMoves.findOrNull(8) == playingMoves.findOrNull(9)
        ) {
            return true
        }

        // Verticals
        if (playingMoves.findOrNull(1) != null &&
            playingMoves.findOrNull(1) == playingMoves.findOrNull(4) &&
            playingMoves.findOrNull(4) == playingMoves.findOrNull(7)
        ) {
            return true
        }
        if (playingMoves.findOrNull(2) != null &&
            playingMoves.findOrNull(2) == playingMoves.findOrNull(5) &&
            playingMoves.findOrNull(5) == playingMoves.findOrNull(8)
        ) {
            return true
        }
        if (playingMoves.findOrNull(3) != null &&
            playingMoves.findOrNull(3) == playingMoves.findOrNull(6) &&
            playingMoves.findOrNull(6) == playingMoves.findOrNull(9)
        ) {
            return true
        }

        // Diagonals
        if (playingMoves.findOrNull(1) != null &&
            playingMoves.findOrNull(1) == playingMoves.findOrNull(5) &&
            playingMoves.findOrNull(5) == playingMoves.findOrNull(9)
        ) {
            return true
        }
        if (playingMoves.findOrNull(3) != null &&
            playingMoves.findOrNull(3) == playingMoves.findOrNull(5) &&
            playingMoves.findOrNull(5) == playingMoves.findOrNull(7)
        ) {
            return true
        }

        return false
    }

    private fun String.findOrNull(position: Int): String? {
        val found = this.indexOf(position.toString())

        return if (found == -1) null
        else getOrNull(found + 1)?.toString()
    }

    // ----------------------------------------------------------------

    fun restart() {
        _eventPaused.value = false
        _currentPlayingMoves.value = ""
    }

    // ----------------------------------------------------------------

    override fun onCleared() {
        sharedPref.setData(winPlayingMoves)
    }

}

data class UiState(
    val loading: Boolean = false,
    val message: Event<String>? = null,
    val paused: Boolean = true,
    val userWinCount: Int = 0,
    val aiWinCount: Int = 0,
    val currentPlayingMoves: String = "",
    val totalNeurons: Int = 0,
)