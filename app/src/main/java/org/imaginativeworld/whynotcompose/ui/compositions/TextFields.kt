package org.imaginativeworld.whynotcompose.ui.compositions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.RelocationRequester
import androidx.compose.ui.layout.relocationRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.LocalWindowInsets
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.ui.theme.AppFont
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme

fun defaultTextInputShape() = RoundedCornerShape(8.dp)

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
private fun DefaultTextInputFieldPreview() {
    AppTheme {
        Column(Modifier.padding(16.dp)) {
            DefaultTextInputField(
                textFieldValue = remember { mutableStateOf(TextFieldValue()) },
                placeholder = "I am  a placeholder",
                keyboardType = KeyboardType.Text,
            )

            DefaultTextInputField(
                modifier = Modifier.padding(top = 32.dp),
                textFieldValue = remember { mutableStateOf(TextFieldValue()) },
                placeholder = "I am  a placeholder",
                isError = true,
            )

            DefaultTextInputField(
                modifier = Modifier.padding(top = 32.dp),
                textFieldValue = remember { mutableStateOf(TextFieldValue("This is a test")) },
                isError = true,
            )

            DefaultTextInputField(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .height(128.dp),
                textFieldValue = remember { mutableStateOf(TextFieldValue()) },
                placeholder = "The quick brown fox jumps over a lazy dog, and the quick black cat jumps over a lazy tiger.",
                keyboardType = KeyboardType.Text,
                singleLine = false,
            )

            DefaultPasswordInputField(
                modifier = Modifier.padding(top = 32.dp),
                textFieldValue = remember { mutableStateOf(TextFieldValue()) },
            )

            DefaultPasswordInputField(
                modifier = Modifier.padding(top = 32.dp),
                textFieldValue = remember { mutableStateOf(TextFieldValue()) },
                isError = true,
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun DefaultTextInputField(
    modifier: Modifier = Modifier,
    textFieldValue: MutableState<TextFieldValue>,
    background: Color = Color(0xFFF1F3F2),
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions? = null,
    fontSize: TextUnit = 15.sp,
    height: Dp = 48.dp,
    isError: Boolean = false,
    onValueChange: (TextFieldValue) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val relocationRequester = remember { RelocationRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val interactionSourceState = interactionSource.collectIsFocusedAsState()
    val scope = rememberCoroutineScope()
    val ime = LocalWindowInsets.current.ime

    LaunchedEffect(ime.isVisible, interactionSourceState.value) {
        if (ime.isVisible && interactionSourceState.value) {
            scope.launch {
                delay(300)
                relocationRequester.bringIntoView()
            }
        }
    }

    val rModifier = remember { modifier }
    val rBackground = remember { background }
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
        textStyle = TextStyle(
            fontSize = rFontSize,
            fontFamily = AppFont.TitilliumWeb
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
            .relocationRequester(relocationRequester)
            .fillMaxWidth(),
        readOnly = rReadOnly,
        decorationBox = { innerTextField ->
            if (rSingleLine) {
                Box(
                    Modifier
                        .clip(defaultTextInputShape())
                        .background(if (isError) Color(0xfffdedec) else rBackground)
                        .height(rHeight)
                        .padding(horizontal = 12.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 2.5.dp)
                    ) {
                        innerTextField()

                        if (textFieldValue.value.text.isEmpty()) {
                            Text(
                                text = rPlaceholder,
                                color = MaterialTheme.colors.onBackground.copy(.35f),
                                fontSize = rFontSize,
                                fontFamily = AppFont.TitilliumWeb,
                                maxLines = if (rSingleLine) 1 else Int.MAX_VALUE,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                    }
                }
            } else {
                Box(
                    Modifier
                        .clip(defaultTextInputShape())
                        .background(if (isError) Color(0xfffdedec) else rBackground)
                        .height(rHeight)
                        .padding(horizontal = 12.dp),
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
                                fontFamily = AppFont.TitilliumWeb,
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

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun DefaultPasswordInputField(
    modifier: Modifier = Modifier,
    textFieldValue: MutableState<TextFieldValue>,
    placeholder: String = "●●●●●●",
    readOnly: Boolean = false,
    fontSize: TextUnit = 15.sp,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions? = null,
    isError: Boolean = false,
    onValueChange: (TextFieldValue) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val passwordVisibility = remember { mutableStateOf(false) }
    val relocationRequester = remember { RelocationRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val interactionSourceState = interactionSource.collectIsFocusedAsState()
    val scope = rememberCoroutineScope()
    val ime = LocalWindowInsets.current.ime

    LaunchedEffect(ime.isVisible, interactionSourceState.value) {
        if (ime.isVisible && interactionSourceState.value) {
            scope.launch {
                delay(300)
                relocationRequester.bringIntoView()
            }
        }
    }

    val rModifier = remember { modifier }
    val rPlaceholder = remember { placeholder }
    val rReadOnly = remember { readOnly }
    val rFontSize = remember { fontSize }
    val rImeAction = remember { imeAction }
    val rKeyboardActions = remember { keyboardActions }
    val rOnValueChange = remember { onValueChange }

    BasicTextField(
        value = textFieldValue.value,
        singleLine = true,
        visualTransformation =
        if (passwordVisibility.value) VisualTransformation.None
        else PasswordVisualTransformation(mask = '●'),
        onValueChange = {
            textFieldValue.value = it

            rOnValueChange(it)
        },
        keyboardActions = rKeyboardActions ?: KeyboardActions(
            onDone = { focusManager.clearFocus() },
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = rImeAction
        ),
        interactionSource = interactionSource,
        modifier = rModifier
            .relocationRequester(relocationRequester)
            .fillMaxWidth(),
        readOnly = rReadOnly,
        textStyle = TextStyle(
            fontSize = rFontSize,
            fontFamily = AppFont.TitilliumWeb
        ),
        decorationBox = { innerTextField ->
            Row(
                Modifier
                    .clip(defaultTextInputShape())
                    .background(if (isError) Color(0xfffdedec) else Color(0xFFF1F3F2))
            ) {
                Box(
                    Modifier
                        .weight(1f)
                        .padding(start = 12.dp, top = 12.dp, bottom = 13.dp)
                ) {
                    innerTextField()

                    if (textFieldValue.value.text.isEmpty()) {
                        Text(
                            text = rPlaceholder,
                            color = MaterialTheme.colors.onBackground.copy(.35f),
                            fontSize = rFontSize,
                            fontFamily = AppFont.TitilliumWeb
                        )
                    }
                }

                Spacer(Modifier.width(16.dp))

                IconButton(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                    }) {
                    AnimatedVisibility(
                        visible = passwordVisibility.value,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_show),
                            "Show Password"
                        )
                    }

                    AnimatedVisibility(
                        visible = !passwordVisibility.value,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_hide),
                            "Hide Password"
                        )
                    }
                }

            }
        }
    )
}

// ================================================================
// Label
// ================================================================

@Preview
@Composable
fun LabelPreview() {
    AppTheme {
        Label(
            text = "I Am A Caption"
        )
    }
}

@Composable
fun Label(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 15.sp,
    fontWeight: FontWeight = FontWeight.Medium,
) {
    Text(
        text = text,
        fontSize = fontSize,
        modifier = modifier,
        fontWeight = fontWeight,
        lineHeight = 18.sp,
    )
}

@Preview
@Composable
fun TitlePreview() {
    AppTheme {
        Label(
            text = "I Am A Title"
        )
    }
}

@Composable
fun Title(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
) {
    Text(
        text = text,
        textAlign = textAlign,
        style = MaterialTheme.typography.h1,
        modifier = modifier
    )
}