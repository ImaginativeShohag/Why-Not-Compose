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

package org.imaginativeworld.whynotcompose.ui.screens.composition.dialog.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

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

@Composable
fun GeneralDialog(
    onDismissRequest: (() -> Unit)? = null,
    properties: DialogProperties = DialogProperties(),
    dialogState: MutableState<Boolean>,
    title: String,
    message: String? = null,
    positiveBtnText: String,
    onPositiveBtnClicked: () -> Unit = {},
    negativeBtnText: String? = null,
    onNegativeBtnClicked: (() -> Unit)? = null
) {
    Dialog(
        onDismissRequest = {
            dialogState.value = false

            onDismissRequest?.invoke()
        },
        properties = properties
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
            }
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
    onNegativeBtnClicked: (() -> Unit)? = null
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
                            OutlinedButton(
                                modifier = Modifier
                                    .weight(.5f),
                                onClick = {
                                    onNegativeBtnClicked?.invoke()
                                }
                            ) {
                                Text(negativeBtnText)
                            }

                            Spacer(modifier = Modifier.width(16.dp))
                        }

                        Button(
                            modifier = Modifier
                                .weight(.5f),
                            onClick = {
                                onPositiveBtnClicked()
                            }
                        ) {
                            Text(positiveBtnText)
                        }
                    }
                }
            }
        }
    }
}
