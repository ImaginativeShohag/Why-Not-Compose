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

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.common.compose.theme.errorInputBackground

@Composable
fun SearchTextInputField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    background: Color = MaterialTheme.colorScheme.surface,
    errorBackground: Color = MaterialTheme.colorScheme.errorInputBackground,
    shape: Shape = RoundedCornerShape(8.dp),
    keyboardType: KeyboardType = KeyboardType.Text,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions? = null,
    fontSize: TextUnit = 16.sp,
    height: Dp = 48.dp,
    horizontalPadding: Dp = 12.dp,
    isError: Boolean = false
) {
    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = value,
        singleLine = singleLine,
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            fontSize = fontSize,
            color = MaterialTheme.colorScheme.onSurface
        ),
        onValueChange = {
            onValueChange(it)
        },
        keyboardActions = keyboardActions ?: KeyboardActions(
            onDone = { focusManager.clearFocus() },
            onNext = { focusManager.moveFocus(FocusDirection.Down) },
            onSearch = { focusManager.clearFocus() }
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        interactionSource = interactionSource,
        modifier = modifier
            .bringIntoViewRequester(bringIntoViewRequester)
            .fillMaxWidth(),
        readOnly = readOnly,
        decorationBox = { innerTextField ->
            if (singleLine) {
                Box(
                    Modifier
                        .clip(shape)
                        .background(if (isError) errorBackground else background)
                        .height(height)
                        .padding(horizontal = horizontalPadding),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 0.0.dp)
                    ) {
                        innerTextField()

                        if (value.text.isEmpty() && placeholder != null) {
                            Text(
                                text = placeholder,
                                color = MaterialTheme.colorScheme.onSurface.copy(.35f),
                                fontSize = fontSize,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            } else {
                Box(
                    Modifier
                        .clip(shape)
                        .background(if (isError) errorBackground else background)
                        .height(height)
                        .padding(horizontal = horizontalPadding)
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 12.5.dp, bottom = 12.5.dp)
                    ) {
                        innerTextField()

                        if (value.text.isEmpty() && placeholder != null) {
                            Text(
                                text = placeholder,
                                color = MaterialTheme.colorScheme.onBackground.copy(.35f),
                                fontSize = fontSize,
                                maxLines = Int.MAX_VALUE,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun SearchTextInputFieldPreview() {
    AppTheme {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ) { innerPadding ->
            Column(
                Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                val (value1, onChange1) = remember { mutableStateOf(TextFieldValue()) }

                SearchTextInputField(
                    value = value1,
                    onValueChange = onChange1,
                    placeholder = "I am  a placeholder",
                    keyboardType = KeyboardType.Text
                )

                val (value2, onChange2) = remember { mutableStateOf(TextFieldValue("I am an input.")) }

                SearchTextInputField(
                    value = value2,
                    onValueChange = onChange2,
                    modifier = Modifier.padding(top = 32.dp),
                    placeholder = "I am  a placeholder."
                )

                val (value3, onChange3) = remember { mutableStateOf(TextFieldValue()) }

                SearchTextInputField(
                    value = value3,
                    onValueChange = onChange3,
                    modifier = Modifier.padding(top = 32.dp),
                    placeholder = "I am  a placeholder",
                    isError = true
                )

                val (value4, onChange4) = remember { mutableStateOf(TextFieldValue("I am an input.")) }

                SearchTextInputField(
                    value = value4,
                    onValueChange = onChange4,
                    modifier = Modifier.padding(top = 32.dp),
                    placeholder = "I am  a placeholder",
                    isError = true
                )

                val (value5, onChange5) = remember { mutableStateOf(TextFieldValue()) }

                SearchTextInputField(
                    value = value5,
                    onValueChange = onChange5,
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .height(128.dp),
                    placeholder = "The quick brown fox jumps over a lazy dog, and the quick black cat jumps over a lazy tiger.",
                    keyboardType = KeyboardType.Text,
                    singleLine = false
                )
            }
        }
    }
}
