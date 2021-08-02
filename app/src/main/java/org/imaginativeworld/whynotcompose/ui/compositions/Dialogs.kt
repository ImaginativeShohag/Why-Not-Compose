package org.imaginativeworld.whynotcompose.ui.compositions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.imaginativeworld.whynotcompose.ui.theme.AppFont
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme


@ExperimentalComposeUiApi
@Preview
@Composable
fun GeneralDialogPreview() {
    AppTheme {
        GeneralDialog(
            dialogState = remember { mutableStateOf(true) },
            title = "Are you sure?",
            message = "This cannot be undone.",
            positiveBtnText = "Yes",
            onPositiveBtnClicked = {},
            negativeBtnText = "No",
            onNegativeBtnClicked = {}
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun GeneralDialog(
    onDismissRequest: (() -> Unit)? = null,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
    dialogState: MutableState<Boolean>,
    title: String,
    message: String? = null,
    positiveBtnText: String,
    onPositiveBtnClicked: () -> Unit = {},
    negativeBtnText: String? = null,
    onNegativeBtnClicked: (() -> Unit)? = null,
) {
    Dialog(
        onDismissRequest = {
            dialogState.value = false

            onDismissRequest?.invoke()
        },
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside,
            usePlatformDefaultWidth = true
        ),
    ) {
        GeneralDialogSkeleton(
            title = title,
            message = message,
            positiveBtnText = positiveBtnText,
            onPositiveBtnClicked = {
                dialogState.value = false

                onPositiveBtnClicked()
            },
            negativeBtnText = negativeBtnText,
            onNegativeBtnClicked = {
                dialogState.value = false

                onNegativeBtnClicked?.invoke()
            },
        )
    }

}

@Preview
@Composable
fun GeneralDialogSkeletonPreview() {
    AppTheme {
        GeneralDialogSkeleton(
            title = "Are you sure?",
            message = "This cannot be undone.",
            positiveBtnText = "Yes",
            onPositiveBtnClicked = {},
            negativeBtnText = "No",
            onNegativeBtnClicked = {}
        )
    }
}

@Composable
fun GeneralDialogSkeleton(
    title: String,
    message: String? = null,
    positiveBtnText: String,
    onPositiveBtnClicked: () -> Unit = {},
    negativeBtnText: String? = null,
    onNegativeBtnClicked: (() -> Unit)? = null,
) {
    Box(Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontFamily = AppFont.TitilliumWeb,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )

                if (message != null) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
                        text = message,
                        fontSize = 15.sp,
                        color = Color(0xFF677987),
                        textAlign = TextAlign.Center,
                        lineHeight = 23.sp
                    )
                }

                Row {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (negativeBtnText != null) {
                            DefaultBorderedButton(
                                text = negativeBtnText,
                                modifier = Modifier
                                    .weight(.5f),
                                horizontalPadding = 8.dp,
                                fontSize = 16.sp,
                                onClick = {
                                    onNegativeBtnClicked?.invoke()
                                }
                            )

                            Spacer(modifier = Modifier.width(16.dp))
                        }

                        DefaultButton(
                            text = positiveBtnText,
                            modifier = Modifier
                                .weight(.5f),
                            horizontalPadding = 8.dp,
                            fontSize = 16.sp,
                            onClick = {
                                onPositiveBtnClicked()
                            }
                        )
                    }
                }
            }

        }
    }
}
