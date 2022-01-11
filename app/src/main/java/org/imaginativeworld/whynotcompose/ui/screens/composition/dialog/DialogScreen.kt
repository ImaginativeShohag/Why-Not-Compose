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

package org.imaginativeworld.whynotcompose.ui.screens.composition.dialog

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.ui.screens.composition.dialog.elements.GeneralDialog
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

@Composable
fun DialogScreen() {
    val openDefaultDialog = remember { mutableStateOf(false) }

    val openCustomDialog = remember { mutableStateOf(false) }

    DialogScreenSkeleton(
        showDefaultDialog = {
            openDefaultDialog.value = true
        },
        showCustomDialog = {
            openCustomDialog.value = true
        }
    )

    if (openDefaultDialog.value) {
        DefaultAlertDialog(
            state = openDefaultDialog
        )
    }

    if (openCustomDialog.value) {
        GeneralDialog(
            dialogState = openCustomDialog,
            title = "Are you sure?",
            message = "This cannot be undone.",
            positiveBtnText = "Yes",
            onPositiveBtnClicked = {},
            negativeBtnText = "No",
            onNegativeBtnClicked = {}
        )
    }
}

@Preview
@Composable
fun DialogScreenSkeletonPreview() {
    AppTheme {
        DialogScreenSkeleton()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DialogScreenSkeletonPreviewDark() {
    AppTheme {
        DialogScreenSkeleton()
    }
}

@Composable
fun DialogScreenSkeleton(
    showDefaultDialog: () -> Unit = {},
    showCustomDialog: () -> Unit = {},
) {
    Scaffold(
        Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AppComponent.Header("Dialog")

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 32.dp),
                onClick = {
                    showDefaultDialog()
                }
            ) {
                Text("Show Default Dialog")
            }

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    showCustomDialog()
                }
            ) {
                Text("Show Custom Dialog")
            }

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.BigSpacer()
        }
    }
}

@Composable
fun DefaultAlertDialog(
    state: MutableState<Boolean>
) {
    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest.
            state.value = false
        },
        title = {
            Text(text = "Title")
        },
        text = {
            Text(
                "This area typically contains the supportive text " +
                    "which presents the details regarding the Dialog's purpose."
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    state.value = false
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    state.value = false
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}
