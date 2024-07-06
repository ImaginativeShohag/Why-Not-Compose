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

package org.imaginativeworld.whynotcompose.ui.screens.composition.textfield

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.TextToolbar
import androidx.compose.ui.platform.TextToolbarStatus
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.base.extensions.isValidEmail
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.common.compose.theme.errorInputBackground
import org.imaginativeworld.whynotcompose.common.compose.theme.inputBackground
import org.imaginativeworld.whynotcompose.common.compose.theme.onInputBackground

/**
 * Official Samples Source;
 * https://cs.android.com/androidx/platform/tools/dokka-devsite-plugin/+/master:testData/compose/samples/material/samples/TextFieldSamples.kt
 */

private val ELEMENT_HEIGHT = 48.dp

@Composable
fun TextFieldScreen(
    goBack: () -> Unit
) {
    TextFieldScreenSkeleton(
        goBack = goBack
    )
}

@PreviewLightDark
@Composable
private fun TextFieldScreenSkeletonPreview() {
    AppTheme {
        TextFieldScreenSkeleton()
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun TextFieldScreenSkeleton(
    goBack: () -> Unit = {}
) {
    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            AppComponent.Header(
                "Text Field",
                goBack = goBack
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(Modifier.padding(start = 16.dp, end = 16.dp)) {
                AppComponent.SubHeader("Official Samples")

                // ----------------------------------------------------------------

                SimpleTextFieldSample()

                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()

                SimpleOutlinedTextFieldSample()

                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()

                TextFieldWithIcons()

                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()

                TextFieldWithPlaceholder()

                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()

                TextFieldWithErrorState()

                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()

                TextFieldWithHelperMessage()

                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()

                PasswordTextField()

                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()

                TextFieldSample()

                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()

                OutlinedTextFieldSample()

                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()

                TextFieldWithHideKeyboardOnImeAction()

                // ----------------------------------------------------------------
                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()
            }

            HorizontalDivider()

            Column(Modifier.padding(start = 16.dp, end = 16.dp)) {
                AppComponent.SubHeader("Cut-Copy-Paste Disabled")

                // ----------------------------------------------------------------

                TextFieldWithCutCopyPasteDisabled()

                // ----------------------------------------------------------------
                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()
            }

            HorizontalDivider()

            CustomTypeOneExample()

            HorizontalDivider()

            CustomTypeTwoExample()

            // ----------------------------------------------------------------

            AppComponent.BigSpacer()
        }
    }
}

@Composable
private fun CustomTypeOneExample(modifier: Modifier = Modifier) {
    Column(modifier.padding(start = 16.dp, end = 16.dp)) {
        AppComponent.SubHeader("Custom Type One")

        // ----------------------------------------------------------------

        val (text1Value, onText1ValueChange) = remember { mutableStateOf(TextFieldValue()) }

        TextInputFieldOne(
            value = text1Value,
            onValueChange = onText1ValueChange,
            placeholder = "I am  a placeholder",
            keyboardType = KeyboardType.Text
        )

        // ----------------------------------------------------------------

        AppComponent.MediumSpacer()

        val (text2Value, onText2ValueChange) = remember { mutableStateOf(TextFieldValue()) }

        TextInputFieldOne(
            value = text2Value,
            onValueChange = onText2ValueChange,
            placeholder = "I am  a placeholder",
            isError = true
        )

        // ----------------------------------------------------------------

        AppComponent.MediumSpacer()

        val (text3Value, onText3ValueChange) = remember { mutableStateOf(TextFieldValue("Lorem ipsum dolor sit amet")) }

        TextInputFieldOne(
            value = text3Value,
            onValueChange = onText3ValueChange,
            placeholder = "I am  a placeholder"
        )

        // ----------------------------------------------------------------

        AppComponent.MediumSpacer()

        val (text4Value, onText4ValueChange) = remember { mutableStateOf(TextFieldValue("Lorem ipsum dolor sit amet")) }

        TextInputFieldOne(
            value = text4Value,
            onValueChange = onText4ValueChange,
            isError = true
        )

        // ----------------------------------------------------------------

        AppComponent.MediumSpacer()

        val (text5Value, onText5ValueChange) = remember { mutableStateOf(TextFieldValue()) }

        TextInputFieldOne(
            value = text5Value,
            onValueChange = onText5ValueChange,
            modifier = Modifier
                .height(128.dp),
            placeholder = "I am a multi-line placeholder.\nHere is another line.",
            keyboardType = KeyboardType.Text,
            singleLine = false
        )

        // ----------------------------------------------------------------

        AppComponent.MediumSpacer()

        val (text6Value, onText6ValueChange) = remember { mutableStateOf(TextFieldValue("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")) }

        TextInputFieldOne(
            value = text6Value,
            onValueChange = onText6ValueChange,
            modifier = Modifier
                .height(128.dp),
            placeholder = "I am a multi-line placeholder.\nHere is another line.",
            keyboardType = KeyboardType.Text,
            singleLine = false
        )

        // ----------------------------------------------------------------

        AppComponent.MediumSpacer()

        val (text7Value, onText7ValueChange) = remember { mutableStateOf(TextFieldValue()) }

        PasswordInputFieldOne(
            value = text7Value,
            onValueChange = onText7ValueChange
        )

        // ----------------------------------------------------------------

        AppComponent.MediumSpacer()

        val (text8Value, onText8ValueChange) = remember { mutableStateOf(TextFieldValue()) }

        PasswordInputFieldOne(
            value = text8Value,
            onValueChange = onText8ValueChange,
            isError = true
        )

        // ----------------------------------------------------------------
        // ----------------------------------------------------------------

        AppComponent.MediumSpacer()
    }
}

@PreviewLightDark
@Composable
private fun CustomTypeOneExamplePreview() {
    AppTheme {
        Scaffold { innerPadding ->
            CustomTypeOneExample(Modifier.padding(innerPadding))
        }
    }
}

@Composable
private fun CustomTypeTwoExample(modifier: Modifier = Modifier) {
    Column(modifier.padding(start = 16.dp, end = 16.dp)) {
        AppComponent.SubHeader("Custom Type Two")

        // ----------------------------------------------------------------

        val (text9Value, onText9ValueChange) = remember { mutableStateOf(TextFieldValue()) }

        TextInputFieldTwo(
            value = text9Value,
            onValueChange = onText9ValueChange,
            placeholder = "I am  a placeholder",
            keyboardType = KeyboardType.Text
        )

        // ----------------------------------------------------------------

        AppComponent.MediumSpacer()

        val (text10Value, onText10ValueChange) = remember { mutableStateOf(TextFieldValue("Lorem ipsum dolor sit amet")) }

        TextInputFieldTwo(
            value = text10Value,
            onValueChange = onText10ValueChange,
            placeholder = "I am  a placeholder"
        )

        // ----------------------------------------------------------------

        AppComponent.MediumSpacer()

        val (text11Value, onText11ValueChange) = remember { mutableStateOf(TextFieldValue()) }

        TextInputFieldTwo(
            value = text11Value,
            onValueChange = onText11ValueChange,
            placeholder = "I am  a placeholder",
            modifier = Modifier
                .height(128.dp),
            keyboardType = KeyboardType.Text,
            singleLine = false
        )

        // ----------------------------------------------------------------

        AppComponent.MediumSpacer()

        val (text12Value, onText12ValueChange) = remember { mutableStateOf(TextFieldValue("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")) }

        TextInputFieldTwo(
            value = text12Value,
            onValueChange = onText12ValueChange,
            placeholder = "I am  a placeholder",
            modifier = Modifier
                .height(128.dp),
            keyboardType = KeyboardType.Text,
            singleLine = false
        )

        // ----------------------------------------------------------------

        AppComponent.MediumSpacer()

        val (text13Value, onText13ValueChange) = remember { mutableStateOf(TextFieldValue()) }

        PasswordInputFieldTwo(
            value = text13Value,
            onValueChange = onText13ValueChange,
            placeholder = "Password"
        )

        // ----------------------------------------------------------------

        AppComponent.MediumSpacer()

        val (text14Value, onText14ValueChange) = remember { mutableStateOf(TextFieldValue("123456")) }

        PasswordInputFieldTwo(
            value = text14Value,
            onValueChange = onText14ValueChange,
            placeholder = "Password"
        )
    }
}

@PreviewLightDark
@Composable
private fun CustomTypeTwoExamplePreview() {
    AppTheme {
        Scaffold { innerPadding ->
            CustomTypeTwoExample(Modifier.padding(innerPadding))
        }
    }
}

// ================================================================
// Custom InputField
// ================================================================

@Composable
private fun TextInputFieldOne(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    background: Color = MaterialTheme.colorScheme.inputBackground,
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    imeAction: ImeAction? = null,
    keyboardActions: KeyboardActions? = null,
    fontSize: TextUnit = 16.sp,
    height: Dp = ELEMENT_HEIGHT,
    isError: Boolean = false
) {
    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = singleLine,
        textStyle = TextStyle(
            fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
            fontSize = fontSize,
            color = MaterialTheme.colorScheme.onInputBackground
        ),
        keyboardActions = keyboardActions ?: KeyboardActions(
            onDone = { focusManager.clearFocus() },
            onNext = { focusManager.moveFocus(FocusDirection.Down) },
            onSearch = { focusManager.clearFocus() }
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction ?: if (singleLine) ImeAction.Done else ImeAction.Default
        ),
        interactionSource = interactionSource,
        modifier = modifier
            .bringIntoViewRequester(bringIntoViewRequester)
            .fillMaxWidth(),
        readOnly = readOnly,
        decorationBox = { innerTextField ->
            Box(
                Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(
                        if (isError) MaterialTheme.colorScheme.errorInputBackground else background
                    )
                    .height(height)
                    .padding(horizontal = 12.dp),
                contentAlignment = if (singleLine) Alignment.CenterStart else Alignment.TopStart
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            top = if (singleLine) 0.dp else 12.5.dp,
                            bottom = if (singleLine) 2.5.dp else 12.5.dp
                        )
                ) {
                    innerTextField()

                    if (value.text.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = MaterialTheme.colorScheme.onInputBackground.copy(.35f),
                            fontSize = fontSize,
                            maxLines = if (singleLine) 1 else Int.MAX_VALUE,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun PasswordInputFieldOne(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "●●●●●●",
    readOnly: Boolean = false,
    fontSize: TextUnit = 16.sp,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions? = null,
    height: Dp = ELEMENT_HEIGHT,
    isError: Boolean = false
) {
    val focusManager = LocalFocusManager.current
    var passwordVisibility by remember { mutableStateOf(false) }
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = value,
        singleLine = true,
        visualTransformation =
        if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation(mask = '●')
        },
        onValueChange = {
            onValueChange(it)
        },
        keyboardActions = keyboardActions ?: KeyboardActions(
            onDone = { focusManager.clearFocus() },
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = imeAction
        ),
        interactionSource = interactionSource,
        modifier = modifier
            .bringIntoViewRequester(bringIntoViewRequester)
            .fillMaxWidth(),
        readOnly = readOnly,
        textStyle = TextStyle(
            fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
            fontSize = fontSize,
            color = MaterialTheme.colorScheme.onInputBackground
        ),
        decorationBox = { innerTextField ->
            Row(
                Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(
                        if (isError) MaterialTheme.colorScheme.errorInputBackground else MaterialTheme.colorScheme.inputBackground
                    )
                    .height(height),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .weight(1f)
                        .padding(start = 12.dp, bottom = 2.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    innerTextField()

                    if (value.text.isEmpty()) {
                        Text(
                            modifier = Modifier.padding(bottom = 2.dp),
                            text = placeholder,
                            color = MaterialTheme.colorScheme.onInputBackground.copy(.35f),
                            fontSize = fontSize
                        )
                    }
                }

                Spacer(Modifier.width(16.dp))

                IconButton(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    onClick = {
                        passwordVisibility = !passwordVisibility
                    }
                ) {
                    AnimatedVisibility(
                        visible = passwordVisibility,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_hide),
                            "Show Password"
                        )
                    }

                    AnimatedVisibility(
                        visible = !passwordVisibility,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_show),
                            "Hide Password"
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun TextInputFieldTwo(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    background: Color = MaterialTheme.colorScheme.inputBackground,
    shape: Shape = MaterialTheme.shapes.medium,
    keyboardType: KeyboardType = KeyboardType.Text,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    maxLength: Int = Int.MAX_VALUE,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions? = null,
    height: Dp = ELEMENT_HEIGHT,
    @DrawableRes icon: Int? = null
) {
    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val interactionSource = remember { MutableInteractionSource() }

    val focusRequester = FocusRequester()
    val isFocused = remember { mutableStateOf(false) }

    BasicTextField(
        modifier = modifier
            .focusRequester(focusRequester)
            .bringIntoViewRequester(bringIntoViewRequester)
            .onFocusChanged {
                isFocused.value = it.isFocused
            }
            .fillMaxWidth(),
        interactionSource = interactionSource,
        value = value,
        singleLine = singleLine,
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onInputBackground
        ),
        onValueChange = {
            if (it.text.length <= maxLength) {
                onValueChange(it)
            }
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
        readOnly = readOnly,
        decorationBox = { innerTextField ->
            Box(
                Modifier
                    .clip(shape)
                    .background(background)
                    .height(height)
            ) {
                Row(
                    Modifier.fillMaxSize()
                ) {
                    icon?.let {
                        Image(
                            modifier = Modifier
                                .size(48.dp)
                                .padding(15.dp),
                            painter = painterResource(id = icon),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onInputBackground)
                        )
                    }

                    Box(
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(
                                start = if (icon == null) 15.dp else 0.dp,
                                bottom = 0.dp,
                                end = 15.dp
                            )
                    ) {
                        val hasText = value.text.isNotEmpty()

                        val animPlaceholder: Dp by animateDpAsState(
                            if (isFocused.value || hasText) 2.dp else 12.dp
                        )
                        val animPlaceHolderFontSize: Int by animateIntAsState(
                            if (isFocused.value || hasText) 12 else 14
                        )

                        Text(
                            modifier = Modifier
                                .graphicsLayer {
                                    translationY = animPlaceholder.toPx()
                                },
                            text = placeholder,
                            color = MaterialTheme.colorScheme.onInputBackground.copy(alpha = .35f),
                            fontSize = animPlaceHolderFontSize.sp,
                            fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                            maxLines = if (singleLine) 1 else Int.MAX_VALUE,
                            overflow = TextOverflow.Ellipsis
                        )

                        Box(
                            Modifier
                                .padding(top = 21.dp)
                                .fillMaxWidth()
                        ) {
                            innerTextField()
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun PasswordInputFieldTwo(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "●●●●●●",
    readOnly: Boolean = false,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions? = null,
    @DrawableRes icon: Int? = null
) {
    val focusManager = LocalFocusManager.current
    val passwordVisibility = remember { mutableStateOf(false) }
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val interactionSource = remember { MutableInteractionSource() }

    val focusRequester = FocusRequester()
    val isFocused = remember { mutableStateOf(false) }

    val fontSize = if (passwordVisibility.value) 14.sp else 18.sp

    BasicTextField(
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged {
                isFocused.value = it.isFocused
            }
            .bringIntoViewRequester(bringIntoViewRequester)
            .fillMaxWidth(),
        interactionSource = interactionSource,
        value = value,
        singleLine = true,
        visualTransformation =
        if (passwordVisibility.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation(mask = '*')
        },
        onValueChange = {
            onValueChange(it)
        },
        keyboardActions = keyboardActions ?: KeyboardActions(
            onDone = { focusManager.clearFocus() },
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        readOnly = readOnly,
        textStyle = TextStyle(
            fontSize = fontSize,
            fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onInputBackground
        ),
        decorationBox = { innerTextField ->
            Row(
                Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.inputBackground)
                    .height(ELEMENT_HEIGHT)
            ) {
                icon?.let {
                    Image(
                        modifier = Modifier
                            .size(48.dp)
                            .padding(15.dp),
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onInputBackground)
                    )
                }

                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(
                            start = if (icon == null) 15.dp else 0.dp,
                            bottom = 0.dp,
                            end = 15.dp
                        )
                ) {
                    val hasText = value.text.isNotEmpty()

                    val animPlaceholder: Dp by animateDpAsState(
                        if (isFocused.value || hasText) 2.dp else 12.dp
                    )
                    val animPlaceHolderFontSize: Int by animateIntAsState(
                        if (isFocused.value || hasText) 12 else 14
                    )

                    Text(
                        modifier = Modifier
                            .graphicsLayer {
                                translationY = animPlaceholder.toPx()
                            },
                        text = placeholder,
                        color = MaterialTheme.colorScheme.onInputBackground.copy(alpha = .35f),
                        fontSize = animPlaceHolderFontSize.sp,
                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Box(
                        Modifier
                            .padding(top = 21.dp)
                            .fillMaxWidth()
                            .height(18.dp)
                    ) {
                        innerTextField()
                    }
                }

                Spacer(Modifier.width(16.dp))

                IconButton(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                    }
                ) {
                    AnimatedVisibility(
                        visible = passwordVisibility.value,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_hide),
                            contentDescription = "Show Password"
                        )
                    }

                    AnimatedVisibility(
                        visible = !passwordVisibility.value,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_show),
                            contentDescription = "Hide Password"
                        )
                    }
                }
            }
        }
    )
}

// ================================================================
// Official Samples
// ================================================================

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun SimpleTextFieldSample() {
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = { text = it },
        label = { Text("Label") },
        singleLine = true
    )
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun SimpleOutlinedTextFieldSample() {
    var text by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = { text = it },
        label = { Text("Label") }
    )
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun TextFieldWithIcons() {
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = { text = it },
        placeholder = { Text("placeholder") },
        leadingIcon = { Icon(Icons.Filled.Favorite, contentDescription = "Localized description") },
        trailingIcon = { Icon(Icons.Filled.Info, contentDescription = "Localized description") }
    )
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun TextFieldWithPlaceholder() {
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = { text = it },
        label = { Text("Email") },
        placeholder = { Text("example@gmail.com") }
    )
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun TextFieldWithErrorState() {
    val context = LocalContext.current
    var text by rememberSaveable { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(false) }

    fun validate(text: String) {
        isError = !text.isValidEmail()
    }

    TextField(
        value = text,
        onValueChange = {
            text = it
            isError = false
        },
        singleLine = true,
        label = { Text(if (isError) "Email* (With Validation)" else "Email (With Validation)") },
        isError = isError,
        keyboardActions = KeyboardActions { validate(text) },
        modifier = Modifier
            .fillMaxWidth()
            .semantics {
                // Provide localized description of the error
                if (isError) {
                    Toast
                        .makeText(context, "Email format is invalid.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    )
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun TextFieldWithHelperMessage() {
    var text by rememberSaveable { mutableStateOf("") }

    Column {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { text = it },
            label = { Text("Label") }
        )
        Text(
            text = "Helper message",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun PasswordTextField() {
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = password,
        onValueChange = { password = it },
        label = { Text("Enter password") },
        visualTransformation =
        if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                val visibilityIcon =
                    if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                // Please provide localized description for accessibility services
                val description = if (passwordHidden) "Show password" else "Hide password"
                Icon(imageVector = visibilityIcon, contentDescription = description)
            }
        }
    )
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun TextFieldSample() {
    var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("example", TextRange(0, 7)))
    }

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = { text = it },
        label = { Text("Label") }
    )
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun OutlinedTextFieldSample() {
    var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("example", TextRange(0, 7)))
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = { text = it },
        label = { Text("Label") }
    )
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun TextFieldWithHideKeyboardOnImeAction() {
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = { text = it },
        label = { Text("Label") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                // do something here
            }
        )
    )
}

// ================================================================
// Cut-Copy-Paste disabled TextField
// Source: https://stackoverflow.com/a/72048150/2263329
// ================================================================

object EmptyTextToolbar : TextToolbar {
    override val status: TextToolbarStatus = TextToolbarStatus.Hidden

    override fun hide() {}

    override fun showMenu(
        rect: Rect,
        onCopyRequested: (() -> Unit)?,
        onPasteRequested: (() -> Unit)?,
        onCutRequested: (() -> Unit)?,
        onSelectAllRequested: (() -> Unit)?
    ) {
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun TextFieldWithCutCopyPasteDisabled() {
    var textValue by remember { mutableStateOf(TextFieldValue()) }

    CompositionLocalProvider(LocalTextToolbar provides EmptyTextToolbar) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = textValue,
            onValueChange = { newValue ->
                textValue = if (newValue.selection.length > 0) {
                    newValue.copy(selection = textValue.selection)
                } else {
                    newValue
                }
            }
        )
    }
}
