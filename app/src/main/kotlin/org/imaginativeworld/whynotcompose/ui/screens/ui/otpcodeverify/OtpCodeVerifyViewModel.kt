/*
 * Copyright 2021 Md. Mahmudul Hasan Shohag
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

package org.imaginativeworld.whynotcompose.ui.screens.ui.otpcodeverify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlin.random.Random
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.base.models.Event

// Code resend counter interval in seconds
private const val RESEND_COUNTER_INTERVAL_SEC = 30

class OtpCodeVerifyViewModel @Inject constructor() : ViewModel() {
    private val eventShowLoading = MutableStateFlow(false)
    private val eventShowMessage = MutableStateFlow<Event<String>?>(null)
    private val eventSuccess = MutableStateFlow(false)

    // ----------------------------------------------------------------

    private val _state = MutableStateFlow(OtpCodeVerifyViewState())
    val state = _state.asStateFlow()

    // ----------------------------------------------------------------

    init {
        viewModelScope.launch {
            combine(
                eventShowLoading,
                eventShowMessage,
                eventSuccess
            ) { showLoading, showMessage, success ->

                OtpCodeVerifyViewState(
                    loading = showLoading,
                    message = showMessage,
                    success = success
                )
            }.catch { throwable ->
                // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                _state.value = it
            }
        }
    }

    // ----------------------------------------------------------------
    // ----------------------------------------------------------------

    fun verifyOtp(
        phone: String,
        code: String
    ) = viewModelScope.launch {
        eventShowLoading.value = true

        // Simulating network delay
        delay(2000)

        val random = Random.nextInt(10)

        if (random < 3) {
            eventShowMessage.value = Event("Cannot send OTP. Please try again.")
        } else {
            eventSuccess.value = true
        }

        eventShowLoading.value = false
    }

    // ----------------------------------------------------------------
    // ----------------------------------------------------------------

    fun sendOtp(
        phone: String
    ) = viewModelScope.launch {
        eventShowLoading.value = true

        // Simulating network delay
        delay(2000)

        val random = Random.nextInt(10)

        if (random < 3) {
            eventShowMessage.value = Event("Cannot send OTP to $phone. Please try again.")
        } else {
            eventShowMessage.value = Event("OTP successfully resent to $phone.")

            restartCounter()
        }

        eventShowLoading.value = false
    }

    // ----------------------------------------------------------------
    // ----------------------------------------------------------------

    private val _resetCounterValue: MutableLiveData<String> by lazy {
        MutableLiveData<String>("00:00")
    }

    val resetCounterValue: LiveData<String>
        get() = _resetCounterValue

    fun restartCounter() {
        var counterSec = RESEND_COUNTER_INTERVAL_SEC

        viewModelScope.launch {
            while (true) {
                val min = counterSec / 60
                val sec = counterSec % 60

                _resetCounterValue.value = "%02d:%02d".format(min, sec)

                counterSec--

                if (counterSec < 0) {
                    break
                }

                delay(1000)
            }
        }
    }

    // ----------------------------------------------------------------
    // ----------------------------------------------------------------

    fun isValidInputs(code: String): Boolean {
        if (code.isBlank()) {
            eventShowMessage.value = Event("Please enter the OTP first!")

            return false
        }

        if (code.length < 6) {
            eventShowMessage.value = Event("Please enter the whole OTP!")

            return false
        }

        return true
    }
}

data class OtpCodeVerifyViewState(
    val loading: Boolean = false,
    val message: Event<String>? = null,
    val success: Boolean = false
)
