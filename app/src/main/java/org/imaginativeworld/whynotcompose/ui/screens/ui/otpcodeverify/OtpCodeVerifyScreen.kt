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

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.substring
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.accompanist.insets.navigationBarsPadding
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.base.models.Event
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.ui.compositions.CustomSnackbarHost

@Composable
fun OtpCodeVerifyScreen(
    viewModel: OtpCodeVerifyViewModel,
    phoneNumber: String,
    goBack: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        // Start code resent counter.
        viewModel.restartCounter()
    }

    OtpCodeVerifyScreenSkeleton(
        phoneNumber = phoneNumber,
        message = state.message,
        loading = state.loading,
        verify = viewModel::verifyOtp,
        isValidInputs = viewModel::isValidInputs,
        counterValue = viewModel.resetCounterValue,
        resendCode = {
            viewModel.sendOtp(phoneNumber)
        }
    )

    if (state.success) {
        AlertDialog(
            onDismissRequest = {
                goBack()
            },
            title = { Text("Code successfully verified! Enjoy.") },
            confirmButton = {
                TextButton(onClick = { goBack() }) {
                    Text("Ok")
                }
            }
        )
    }
}

@Preview
@Composable
fun OtpCodeVerifyScreenSkeletonPreview() {
    AppTheme {
        OtpCodeVerifyScreenSkeleton(
            phoneNumber = "+8801234567891",
            counterValue = MutableLiveData(),
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun OtpCodeVerifyScreenSkeletonPreviewDark() {
    AppTheme {
        OtpCodeVerifyScreenSkeleton(
            phoneNumber = "+8801234567891",
            counterValue = MutableLiveData(),
        )
    }
}

@Composable
private fun OtpCodeVerifyScreenSkeleton(
    phoneNumber: String,
    message: Event<String>? = null,
    loading: Boolean = false,
    verify: (phoneNumber: String, code: String) -> Unit = { _, _ -> },
    isValidInputs: (code: String) -> Boolean = { false },
    counterValue: LiveData<String>,
    resendCode: () -> Unit = {},
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val codeState = remember { mutableStateOf("") }

    val customKeyboardVisible = remember { mutableStateOf(false) }

    val counterValueState = counterValue.observeAsState("00:00")

    LaunchedEffect(message) {
        message?.value?.let { message ->
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(message)
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { CustomSnackbarHost(it) },
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_jetpack_compose_logo),
                    contentDescription = "illustration",
                    modifier = Modifier
                        .padding(top = 64.dp)
                        .size(128.dp)
                )

                Text(
                    text = "Verify Mobile Number",
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h1,
                )

                Text(
                    text = "Enter the one time password sent to",
                    modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 0.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    lineHeight = 23.sp,
                    color = Color(0xFF677987)
                )

                Text(
                    text = phoneNumber,
                    modifier = Modifier.padding(16.dp, 4.dp, 16.dp, 0.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = Color(0xFF677987)
                )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(32.dp, 16.dp, 32.dp, 16.dp)
                        .clickable(
                            onClick = {
                                customKeyboardVisible.value = !customKeyboardVisible.value
                            },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    CodeField(
                        text = if (codeState.value.isNotEmpty())
                            codeState.value.substring(TextRange(0, 1))
                        else ""
                    )

                    CodeField(
                        text = if (codeState.value.length > 1)
                            codeState.value.substring(TextRange(1, 2))
                        else ""
                    )

                    CodeField(
                        text = if (codeState.value.length > 2)
                            codeState.value.substring(TextRange(2, 3))
                        else ""
                    )

                    CodeField(
                        text = if (codeState.value.length > 3)
                            codeState.value.substring(TextRange(3, 4))
                        else ""
                    )

                    CodeField(
                        text = if (codeState.value.length > 4)
                            codeState.value.substring(TextRange(4, 5))
                        else ""
                    )

                    CodeField(
                        text = if (codeState.value.length > 5)
                            codeState.value.substring(TextRange(5, 6))
                        else ""
                    )
                }

                Text(
                    text = "Having trouble? Request a new OTP in ${counterValueState.value}",
                    modifier = Modifier.padding(16.dp, 4.dp, 16.dp, 0.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    lineHeight = 16.sp,
                    color = Color(0xFF677987)
                )

                TextButton(
                    modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
                    onClick = {
                        resendCode()
                    },
                    enabled = counterValueState.value == "00:00"
                ) {
                    Text("Resend Code")
                }
            }

            AnimatedVisibility(
                visible = customKeyboardVisible.value,
                enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { fullHeight -> fullHeight }) + fadeOut(),
            ) {
                VirtualNumberKeyboard(
                    codeState = codeState,
                    isValidInputs = isValidInputs,
                    verify = { code ->
                        verify(phoneNumber, code)
                    }
                )
            }
        }

        LoadingIndicator(
            show = loading
        )
    }
}

@Preview
@Composable
fun VirtualNumberKeyboardPreview() {
    AppTheme {
        VirtualNumberKeyboard(
            codeState = remember { mutableStateOf("") },
            isValidInputs = { true },
            verify = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun VirtualNumberKeyboardPreviewDark() {
    AppTheme {
        VirtualNumberKeyboard(
            codeState = remember { mutableStateOf("") },
            isValidInputs = { true },
            verify = {}
        )
    }
}

@Composable
fun VirtualNumberKeyboard(
    modifier: Modifier = Modifier,
    codeState: MutableState<String>,
    isValidInputs: (code: String) -> Boolean,
    verify: (code: String) -> Unit
) {
    Column(
        modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .background(MaterialTheme.colors.onSurface.copy(.1f))
    ) {
        Row(
            Modifier.fillMaxWidth()
        ) {
            KeyboardKey(
                modifier = Modifier.weight(1f),
                text = "1",
                onClick = { if (codeState.value.length < 6) codeState.value += "1" }
            )
            KeyboardKey(
                modifier = Modifier.weight(1f),
                text = "2",
                onClick = { if (codeState.value.length < 6) codeState.value += "2" }
            )
            KeyboardKey(
                modifier = Modifier.weight(1f),
                text = "3",
                onClick = { if (codeState.value.length < 6) codeState.value += "3" }
            )
        }
        Row(
            Modifier.fillMaxWidth()
        ) {
            KeyboardKey(
                modifier = Modifier.weight(1f),
                text = "4",
                onClick = { if (codeState.value.length < 6) codeState.value += "4" }
            )
            KeyboardKey(
                modifier = Modifier.weight(1f),
                text = "5",
                onClick = { if (codeState.value.length < 6) codeState.value += "5" }
            )
            KeyboardKey(
                modifier = Modifier.weight(1f),
                text = "6",
                onClick = { if (codeState.value.length < 6) codeState.value += "6" }
            )
        }
        Row(
            Modifier.fillMaxWidth()
        ) {
            KeyboardKey(
                modifier = Modifier.weight(1f),
                text = "7",
                onClick = { if (codeState.value.length < 6) codeState.value += "7" }
            )
            KeyboardKey(
                modifier = Modifier.weight(1f),
                text = "8",
                onClick = { if (codeState.value.length < 6) codeState.value += "8" }
            )
            KeyboardKey(
                modifier = Modifier.weight(1f),
                text = "9",
                onClick = { if (codeState.value.length < 6) codeState.value += "9" }
            )
        }
        Row(
            Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .height(54.dp)
                    .weight(1f)
                    .clickable(
                        onClick = {
                            if (codeState.value.isNotEmpty()) {
                                codeState.value = codeState.value.substring(
                                    TextRange(
                                        0,
                                        codeState.value.length - 1
                                    )
                                )
                            }
                        },
                        indication = rememberRipple(color = MaterialTheme.colors.primary),
                        interactionSource = remember { MutableInteractionSource() }
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier.size(38.dp),
                    painter = painterResource(id = R.drawable.ic_keyboard_delete),
                    contentDescription = "Delete"
                )
            }
            KeyboardKey(
                modifier = Modifier.weight(1f),
                text = "0",
                onClick = { if (codeState.value.length < 6) codeState.value += "0" }
            )
            Column(
                modifier = Modifier
                    .height(54.dp)
                    .weight(1f)
                    .clickable(
                        onClick = {
                            if (isValidInputs(codeState.value)) {
                                verify(codeState.value)
                            }
                        },
                        indication = rememberRipple(color = MaterialTheme.colors.primary),
                        interactionSource = remember { MutableInteractionSource() }
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Crossfade(codeState.value.length >= 6) { isCompleted ->
                    when (isCompleted) {
                        true -> Image(
                            modifier = Modifier.size(38.dp),
                            painter = painterResource(id = R.drawable.ic_keyboard_enter_enabled),
                            contentDescription = "Enter"
                        )
                        false -> Image(
                            modifier = Modifier.size(38.dp),
                            painter = painterResource(id = R.drawable.ic_keyboard_enter),
                            contentDescription = "Enter"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun KeyboardKey(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier.height(54.dp),
        onClick = onClick,
        shape = RoundedCornerShape(0.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = text,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSurface.copy(.8f)
        )
    }
}

@Composable
private fun CodeField(
    text: String
) {
    Box(
        Modifier
            .padding(start = 4.dp, end = 4.dp)
            .size(40.dp, 45.dp)
            .background(
                color = MaterialTheme.colors.onSurface.copy(.05f),
                shape = RoundedCornerShape(6.dp)
            )
            .border(
                BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(.1f)),
                RoundedCornerShape(6.dp)
            )
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colors.onSurface
        )
        if (text.isEmpty()) {
            Box(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(start = 12.dp, end = 12.dp, bottom = 13.dp)
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.onSurface.copy(.15f))
            )
        }
    }
}

@Composable
private fun LoadingIndicator(
    show: Boolean = true
) {
    AnimatedVisibility(
        visible = show,
        enter = fadeIn(),
        exit = fadeOut()
    ) {

        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = .6f))
        ) {
            Card(
                Modifier
                    .size(200.dp, 180.dp)
                    .align(Alignment.Center),
                shape = RoundedCornerShape(8.dp),
                elevation = 8.dp
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        Modifier.size(76.dp),
                        strokeWidth = 8.dp
                    )

                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = "Loading...",
                        fontSize = 26.sp
                    )
                }
            }
        }
    }
}
