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

package org.imaginativeworld.whynotcompose.ui.screens.tutorial.datafetchandpaging.elements

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.common.compose.theme.errorInputBackground

@Composable
fun SearchTextInputField(
    modifier: Modifier = Modifier,
    textFieldValue: MutableState<TextFieldValue>,
    background: Color = MaterialTheme.colors.surface,
    errorBackground: Color = MaterialTheme.colors.errorInputBackground,
    shape: Shape = RoundedCornerShape(8.dp),
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions? = null,
    fontSize: TextUnit = 16.sp,
    height: Dp = 48.dp,
    horizontalPadding: Dp = 12.dp,
    isError: Boolean = false,
    onValueChange: (TextFieldValue) -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val interactionSourceState = interactionSource.collectIsFocusedAsState()
    val scope = rememberCoroutineScope()
    val isImeVisible = WindowInsets.isImeVisible

    LaunchedEffect(isImeVisible, interactionSourceState.value) {
        if (isImeVisible && interactionSourceState.value) {
            scope.launch {
                delay(300)
                bringIntoViewRequester.bringIntoView()
            }
        }
    }

    val rModifier = remember { modifier }
    val rBackground = remember { background }
    val rErrorBackground = remember { errorBackground }
    val rPlaceholder = remember { placeholder }
    val rKeyboardType = remember { keyboardType }
    val rReadOnly = remember { readOnly }
    val rSingleLine = remember { singleLine }
    val rImeAction = remember { imeAction }
    val rKeyboardActions = remember { keyboardActions }
    val rFontSize = remember { fontSize }
    val rHeight = remember { height }
    val rOnValueChange = remember { onValueChange }

    BasicTextField(
        value = textFieldValue.value,
        singleLine = rSingleLine,
        textStyle = MaterialTheme.typography.body1.copy(
            fontSize = rFontSize,
            color = MaterialTheme.colors.onSurface
        ),
        onValueChange = {
            textFieldValue.value = it

            rOnValueChange(it)
        },
        keyboardActions = rKeyboardActions ?: KeyboardActions(
            onDone = { focusManager.clearFocus() },
            onNext = { focusManager.moveFocus(FocusDirection.Down) },
            onSearch = { focusManager.clearFocus() }
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = rKeyboardType,
            imeAction = rImeAction
        ),
        interactionSource = interactionSource,
        modifier = rModifier
            .bringIntoViewRequester(bringIntoViewRequester)
            .fillMaxWidth(),
        readOnly = rReadOnly,
        decorationBox = { innerTextField ->
            if (rSingleLine) {
                Box(
                    Modifier
                        .clip(shape)
                        .background(if (isError) rErrorBackground else rBackground)
                        .height(rHeight)
                        .padding(horizontal = horizontalPadding),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 0.0.dp)
                    ) {
                        innerTextField()

                        if (textFieldValue.value.text.isEmpty()) {
                            Text(
                                text = rPlaceholder,
                                color = MaterialTheme.colors.onSurface.copy(.35f),
                                fontSize = rFontSize,
                                maxLines = if (rSingleLine) 1 else Int.MAX_VALUE,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            } else {
                Box(
                    Modifier
                        .clip(shape)
                        .background(if (isError) rErrorBackground else rBackground)
                        .height(rHeight)
                        .padding(horizontal = horizontalPadding)
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 12.5.dp, bottom = 12.5.dp)
                    ) {
                        innerTextField()

                        if (textFieldValue.value.text.isEmpty()) {
                            Text(
                                text = rPlaceholder,
                                color = MaterialTheme.colors.onBackground.copy(.35f),
                                fontSize = rFontSize,
                                maxLines = if (rSingleLine) 1 else Int.MAX_VALUE,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SearchTextInputFieldPreview() {
    AppTheme {
        Column(Modifier.fillMaxWidth()) {
            SearchTextInputField(
                textFieldValue = remember { mutableStateOf(TextFieldValue()) },
                placeholder = "I am  a placeholder",
                keyboardType = KeyboardType.Text
            )

            SearchTextInputField(
                modifier = Modifier.padding(top = 32.dp),
                textFieldValue = remember { mutableStateOf(TextFieldValue("I am an input.")) },
                placeholder = "I am  a placeholder"
            )

            SearchTextInputField(
                modifier = Modifier.padding(top = 32.dp),
                textFieldValue = remember { mutableStateOf(TextFieldValue()) },
                placeholder = "I am  a placeholder",
                isError = true
            )

            SearchTextInputField(
                modifier = Modifier.padding(top = 32.dp),
                textFieldValue = remember { mutableStateOf(TextFieldValue("I am an input.")) },
                placeholder = "I am  a placeholder",
                isError = true
            )

            SearchTextInputField(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .height(128.dp),
                textFieldValue = remember { mutableStateOf(TextFieldValue()) },
                placeholder = "The quick brown fox jumps over a lazy dog, and the quick black cat jumps over a lazy tiger.",
                keyboardType = KeyboardType.Text,
                singleLine = false
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchTextInputFieldPreviewDark() {
    AppTheme {
        Column(Modifier.fillMaxWidth()) {
            SearchTextInputField(
                textFieldValue = remember { mutableStateOf(TextFieldValue()) },
                placeholder = "I am  a placeholder",
                keyboardType = KeyboardType.Text
            )

            SearchTextInputField(
                modifier = Modifier.padding(top = 32.dp),
                textFieldValue = remember { mutableStateOf(TextFieldValue("I am an input.")) },
                placeholder = "I am  a placeholder"
            )

            SearchTextInputField(
                modifier = Modifier.padding(top = 32.dp),
                textFieldValue = remember { mutableStateOf(TextFieldValue()) },
                placeholder = "I am  a placeholder",
                isError = true
            )

            SearchTextInputField(
                modifier = Modifier.padding(top = 32.dp),
                textFieldValue = remember { mutableStateOf(TextFieldValue("I am an input.")) },
                placeholder = "I am  a placeholder",
                isError = true
            )

            SearchTextInputField(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .height(128.dp),
                textFieldValue = remember { mutableStateOf(TextFieldValue()) },
                placeholder = "The quick brown fox jumps over a lazy dog, and the quick black cat jumps over a lazy tiger.",
                keyboardType = KeyboardType.Text,
                singleLine = false
            )
        }
    }
}
