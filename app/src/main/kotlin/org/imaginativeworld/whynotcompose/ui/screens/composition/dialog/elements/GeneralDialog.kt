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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

@PreviewLightDark
@Composable
private fun GeneralDialogPreview() {
    AppTheme {
        GeneralDialog(
            onDismiss = {},
            title = "Are you sure?",
            message = "This cannot be undone.",
            positiveBtnText = "Yes",
            onPositiveBtnClick = {},
            negativeBtnText = "No",
            onNegativeBtnClick = {}
        )
    }
}

@Composable
fun GeneralDialog(
    onDismiss: () -> Unit,
    title: String,
    positiveBtnText: String,
    onPositiveBtnClick: () -> Unit,
    modifier: Modifier = Modifier,
    message: String? = null,
    negativeBtnText: String? = null,
    onNegativeBtnClick: (() -> Unit)? = null,
    properties: DialogProperties = DialogProperties()
) {
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = properties
    ) {
        GeneralDialogSkeleton(
            modifier = modifier,
            title = title,
            message = message,
            positiveBtnText = positiveBtnText,
            onPositiveBtnClick = {
                onDismiss()

                onPositiveBtnClick()
            },
            negativeBtnText = negativeBtnText,
            onNegativeBtnClick = {
                onDismiss()

                onNegativeBtnClick?.invoke()
            }
        )
    }
}

@PreviewLightDark
@Composable
private fun GeneralDialogSkeletonPreview() {
    AppTheme {
        GeneralDialogSkeleton(
            title = "Are you sure?",
            message = "This cannot be undone.",
            positiveBtnText = "Yes",
            onPositiveBtnClick = {},
            negativeBtnText = "No",
            onNegativeBtnClick = {}
        )
    }
}

@Composable
fun GeneralDialogSkeleton(
    title: String,
    positiveBtnText: String,
    onPositiveBtnClick: () -> Unit,
    modifier: Modifier = Modifier,
    message: String? = null,
    negativeBtnText: String? = null,
    onNegativeBtnClick: (() -> Unit)? = null
) {
    Box(modifier.fillMaxSize()) {
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
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )

                if (message != null) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
                        text = message,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
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
                                    onNegativeBtnClick?.invoke()
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
                                onPositiveBtnClick()
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
