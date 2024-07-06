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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.base.models.Event
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.ui.compositions.CustomSnackbarHost

@Composable
fun OtpCodeVerifyScreen(
    viewModel: OtpCodeVerifyViewModel,
    phoneNumber: String,
    goBack: () -> Unit
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
        counterValue = viewModel.resetCounterValue,
        verify = viewModel::verifyOtp,
        isValidInputs = viewModel::isValidInputs,
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

@PreviewLightDark
@Composable
private fun OtpCodeVerifyScreenSkeletonPreviewDark() {
    AppTheme {
        OtpCodeVerifyScreenSkeleton(
            phoneNumber = "+8801234567891",
            message = null,
            loading = false,
            counterValue = MutableLiveData()
        )
    }
}

@Composable
private fun OtpCodeVerifyScreenSkeleton(
    phoneNumber: String,
    message: Event<String>?,
    loading: Boolean,
    counterValue: LiveData<String>,
    verify: (phoneNumber: String, code: String) -> Unit = { _, _ -> },
    isValidInputs: (code: String) -> Boolean = { false },
    resendCode: () -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val codeState = remember { mutableStateOf("") }

    val customKeyboardVisible = remember { mutableStateOf(false) }

    val counterValueState = counterValue.observeAsState("00:00")

    LaunchedEffect(message) {
        message?.value?.let { message ->
            scope.launch {
                snackbarHostState.showSnackbar(message)
            }
        }
    }

    Scaffold(
        snackbarHost = { CustomSnackbarHost(snackbarHostState) },
        modifier = Modifier
            .navigationBarsPadding()
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
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
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "Enter the one time password sent to",
                    modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 0.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    lineHeight = 23.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.8f)
                )

                Text(
                    text = phoneNumber,
                    modifier = Modifier.padding(16.dp, 4.dp, 16.dp, 0.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.8f)
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
                        text = if (codeState.value.isNotEmpty()) {
                            codeState.value.substring(TextRange(0, 1))
                        } else {
                            ""
                        }
                    )

                    CodeField(
                        text = if (codeState.value.length > 1) {
                            codeState.value.substring(TextRange(1, 2))
                        } else {
                            ""
                        }
                    )

                    CodeField(
                        text = if (codeState.value.length > 2) {
                            codeState.value.substring(TextRange(2, 3))
                        } else {
                            ""
                        }
                    )

                    CodeField(
                        text = if (codeState.value.length > 3) {
                            codeState.value.substring(TextRange(3, 4))
                        } else {
                            ""
                        }
                    )

                    CodeField(
                        text = if (codeState.value.length > 4) {
                            codeState.value.substring(TextRange(4, 5))
                        } else {
                            ""
                        }
                    )

                    CodeField(
                        text = if (codeState.value.length > 5) {
                            codeState.value.substring(TextRange(5, 6))
                        } else {
                            ""
                        }
                    )
                }

                Text(
                    text = "Having trouble? Request a new OTP in ${counterValueState.value}",
                    modifier = Modifier.padding(16.dp, 4.dp, 16.dp, 0.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    lineHeight = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.8f)
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
                exit = slideOutVertically(targetOffsetY = { fullHeight -> fullHeight }) + fadeOut()
            ) {
                VirtualNumberKeyboard(
                    code = codeState.value,
                    onNumberClick = { number ->
                        if (codeState.value.length < 6) codeState.value += number
                    },
                    onDeleteClick = {
                        if (codeState.value.isNotEmpty()) {
                            codeState.value = codeState.value.substring(
                                TextRange(
                                    0,
                                    codeState.value.length - 1
                                )
                            )
                        }
                    },
                    isValidInputs = { isValidInputs(codeState.value) },
                    verify = {
                        verify(phoneNumber, codeState.value)
                    }
                )
            }
        }

        LoadingIndicator(
            show = loading
        )
    }
}

@PreviewLightDark
@Composable
private fun VirtualNumberKeyboardPreview() {
    AppTheme {
        VirtualNumberKeyboard(
            code = "",
            onNumberClick = {},
            onDeleteClick = {},
            isValidInputs = { true },
            verify = {}
        )
    }
}

@Composable
fun VirtualNumberKeyboard(
    code: String,
    onNumberClick: (String) -> Unit,
    onDeleteClick: () -> Unit,
    isValidInputs: () -> Boolean,
    verify: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(0.2f))
    ) {
        HorizontalDivider(
            color = DividerDefaults.color.copy(0.6f)
        )

        Row(
            Modifier.fillMaxWidth()
        ) {
            for (i in 1..3) {
                KeyboardKey(
                    modifier = Modifier.weight(1f),
                    text = "$i",
                    onClick = { onNumberClick("$i") }
                )
            }
        }
        Row(
            Modifier.fillMaxWidth()
        ) {
            for (i in 4..6) {
                KeyboardKey(
                    modifier = Modifier.weight(1f),
                    text = "$i",
                    onClick = { onNumberClick("$i") }
                )
            }
        }
        Row(
            Modifier.fillMaxWidth()
        ) {
            for (i in 7..9) {
                KeyboardKey(
                    modifier = Modifier.weight(1f),
                    text = "$i",
                    onClick = { onNumberClick("$i") }
                )
            }
        }
        Row(
            Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .height(54.dp)
                    .weight(1f)
                    .clickable(
                        onClick = onDeleteClick,
                        indication = ripple(color = MaterialTheme.colorScheme.primary),
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
                onClick = { onNumberClick("0") }
            )
            Column(
                modifier = Modifier
                    .height(54.dp)
                    .weight(1f)
                    .clickable(
                        onClick = {
                            if (isValidInputs()) {
                                verify()
                            }
                        },
                        indication = ripple(color = MaterialTheme.colorScheme.primary),
                        interactionSource = remember { MutableInteractionSource() }
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Crossfade(
                    code.length >= 6,
                    label = "Enter key"
                ) { isCompleted ->
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
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
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
            color = MaterialTheme.colorScheme.onSurface.copy(.8f)
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
                color = MaterialTheme.colorScheme.onSurface.copy(.05f),
                shape = RoundedCornerShape(6.dp)
            )
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(.1f)),
                RoundedCornerShape(6.dp)
            )
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
        if (text.isEmpty()) {
            Box(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(start = 12.dp, end = 12.dp, bottom = 13.dp)
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onSurface.copy(.15f))
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
                .clickable { /* no-op */ }
                .fillMaxSize()
                .background(Color.Black.copy(alpha = .6f))
        ) {
            Card(
                Modifier
                    .size(200.dp, 180.dp)
                    .align(Alignment.Center),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                )
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
