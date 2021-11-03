package org.imaginativeworld.whynotcompose.ui.screens.tutorial.counterwithviewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CounterWithVMViewModel : ViewModel() {

    private val _counter = MutableStateFlow(0)
    val counter: StateFlow<Int>
        get() = _counter

    fun increase() {
        _counter.value += 1
    }

    fun decrease() {
        _counter.value -= 1
    }
}
