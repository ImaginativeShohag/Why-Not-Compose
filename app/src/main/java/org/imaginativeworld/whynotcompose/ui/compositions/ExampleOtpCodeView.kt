package org.imaginativeworld.whynotcompose.ui.compositions

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.imaginativeworld.whynotcompose.ui.theme.FlatColor
import timber.log.Timber

@Preview
@Composable
fun OtpCodeView() {
    val code1 = remember { mutableStateOf(TextFieldValue()) }
    val code2 = remember { mutableStateOf(TextFieldValue()) }
    val code3 = remember { mutableStateOf(TextFieldValue()) }
    val code4 = remember { mutableStateOf(TextFieldValue()) }
    val code5 = remember { mutableStateOf(TextFieldValue()) }
    val code6 = remember { mutableStateOf(TextFieldValue()) }

    val focusRequester1 = FocusRequester()
    val focusRequester2 = FocusRequester()
    val focusRequester3 = FocusRequester()
    val focusRequester4 = FocusRequester()
    val focusRequester5 = FocusRequester()
    val focusRequester6 = FocusRequester()

    Row(
        Modifier
            .fillMaxWidth()
            .padding(32.dp, 16.dp, 32.dp, 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CodeTextField(
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
                .aspectRatio(1f),
            textFieldValue = code1,
            nextTextFieldValue = code2,
            focusRequester = focusRequester1,
            prevFocusRequester = null,
            nextFocusRequester = focusRequester2
        )

        CodeTextField(
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
                .aspectRatio(1f),
            textFieldValue = code2,
            nextTextFieldValue = code3,
            focusRequester = focusRequester2,
            prevFocusRequester = focusRequester1,
            nextFocusRequester = focusRequester3
        )

        CodeTextField(
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
                .aspectRatio(1f),
            textFieldValue = code3,
            nextTextFieldValue = code4,
            focusRequester = focusRequester3,
            prevFocusRequester = focusRequester2,
            nextFocusRequester = focusRequester4
        )

        CodeTextField(
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
                .aspectRatio(1f),
            textFieldValue = code4,
            nextTextFieldValue = code5,
            focusRequester = focusRequester4,
            prevFocusRequester = focusRequester3,
            nextFocusRequester = focusRequester5
        )

        CodeTextField(
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
                .aspectRatio(1f),
            textFieldValue = code5,
            nextTextFieldValue = code6,
            focusRequester = focusRequester5,
            prevFocusRequester = focusRequester4,
            nextFocusRequester = focusRequester6
        )

        CodeTextField(
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
                .aspectRatio(1f),
            textFieldValue = code6,
            nextTextFieldValue = null,
            focusRequester = focusRequester6,
            prevFocusRequester = focusRequester5,
            nextFocusRequester = null
        )
    }
}

@Composable
fun CodeTextField(
    modifier: Modifier,
    textFieldValue: MutableState<TextFieldValue>,
    nextTextFieldValue: MutableState<TextFieldValue>? = null,
    focusRequester: FocusRequester,
    prevFocusRequester: FocusRequester? = null,
    nextFocusRequester: FocusRequester? = null,
    onValueChange: (old: String, new: String, isChanged: Boolean) -> Unit = { _, _, _ -> }
) {
    val currentFocus = LocalFocusManager.current

    val buttonBackgroundColor = remember { mutableStateOf(Color.Transparent) }
    val buttonBorderColor = remember { mutableStateOf(Color(0xffdddddd)) }

    BasicTextField(
        value = textFieldValue.value,
        onValueChange = {
            val oldText = textFieldValue.value.text
            var newText = it.text

            if (newText.length > 1) {
                newText = newText.substring(1, 2)
            }

            // Validate
            if (newText.isNotEmpty() && newText[0] !in '0'..'9') {
                newText = ""
            }

            textFieldValue.value = textFieldValue.value.copy(
                text = newText,
                selection = TextRange(newText.length, newText.length)
            )

            val isChanged = oldText != it.text

            // Change focus
            if (isChanged && newText.length == 1) {
                nextFocusRequester?.requestFocus()
            } else if (isChanged && newText.isEmpty()) {
                prevFocusRequester?.requestFocus()
            }

            // Callback
            onValueChange(
                oldText,
                newText,
                isChanged
            )
        },
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged {
                when (it.isFocused) {
                    true -> {
                        buttonBackgroundColor.value = Color(0xfff5f5f5)
                        buttonBorderColor.value = FlatColor.FlatAwesomeGreen1
                    }
                    false -> {
                        buttonBackgroundColor.value = Color.Transparent
                        buttonBorderColor.value = Color(0xffdddddd)
                    }
                }
            }
            .onKeyEvent { keyEvent ->

                Timber.e("keyEvent: $keyEvent")

                false
            }
            .onPreviewKeyEvent {

                Timber.e("onPreviewKeyEvent: $it")

                false
            },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = if (nextFocusRequester != null) ImeAction.Next else ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                nextFocusRequester?.requestFocus()
            },
            onDone = {
                currentFocus.clearFocus()
            }
        ),
        textStyle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        ),
        cursorBrush = SolidColor(Color.Transparent),
        singleLine = true,
        onTextLayout = {

        },
        decorationBox = @Composable { coreTextField ->
            Row(
                modifier = Modifier
                    .background(
                        color = animateColorAsState(buttonBackgroundColor.value).value,
                        shape = CircleShape
                    )
                    .border(
                        BorderStroke(2.dp, animateColorAsState(buttonBorderColor.value).value),
                        CircleShape
                    )
                    .wrapContentSize(unbounded = true),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                coreTextField()
            }
        }
    )
}