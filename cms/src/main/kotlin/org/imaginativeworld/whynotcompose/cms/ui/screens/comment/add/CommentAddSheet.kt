/*
 * Copyright 2023 Md. Mahmudul Hasan Shohag
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

package org.imaginativeworld.whynotcompose.cms.ui.screens.comment.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.base.extensions.toast
import org.imaginativeworld.whynotcompose.base.models.Event
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme
import org.imaginativeworld.whynotcompose.cms.ui.compositions.GeneralSheetAppBar
import org.imaginativeworld.whynotcompose.cms.ui.compositions.LoadingContainer
import org.imaginativeworld.whynotcompose.cms.ui.compositions.button.GeneralFilledButton
import org.imaginativeworld.whynotcompose.cms.ui.compositions.button.GeneralOutlinedButton

@Suppress("ktlint:compose:mutable-state-param-check")
@Composable
fun CommentAddSheet(
    postId: Int,
    showSheet: MutableState<Boolean>,
    onSuccess: () -> Unit,
    viewModel: CommentAddViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        // Note: Remove the `remember` later. Issue: https://issuetracker.google.com/issues/340582180
        confirmValueChange = remember {
            { sheetState ->
                sheetState != SheetValue.Hidden
            }
        }
    )

    val state by viewModel.state.collectAsState()

    val goBack: () -> Unit = {
        scope.launch {
            bottomSheetState.hide()
            showSheet.value = false
        }
    }

    LaunchedEffect(state.addCommentSuccess, onSuccess) {
        state.addCommentSuccess?.getValueOnce()?.let { isAddCommentSuccess ->
            if (isAddCommentSuccess) {
                context.toast("Comment successfully added.")

                goBack()
                onSuccess()
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = { showSheet.value = false },
        sheetState = bottomSheetState
    ) {
        CommentAddSheetSkeleton(
            showLoading = state.loading,
            showMessage = state.message,
            goBack = goBack,
            addComment = { name, email, body ->
                viewModel.addComment(
                    postId,
                    name,
                    email,
                    body
                )
            }
        )
    }
}

@PreviewLightDark
@Composable
private fun CommentAddSheetSkeletonPreviewDark() {
    CMSAppTheme {
        CommentAddSheetSkeleton(
            showLoading = false,
            showMessage = null
        )
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun CommentAddSheetSkeleton(
    showLoading: Boolean,
    showMessage: Event<String>?,
    goBack: () -> Unit = {},
    addComment: (
        name: String,
        email: String,
        body: String
    ) -> Unit = { _, _, _ -> }
) {
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var body by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(showMessage) {
        showMessage?.getValueOnce()?.let { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        Modifier.heightIn(max = 417.dp),
        topBar = {
            GeneralSheetAppBar(
                title = "Add Comment",
                onCancelClick = goBack
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = BottomSheetDefaults.ContainerColor,
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                singleLine = true
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                ),
                singleLine = true
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = body,
                onValueChange = { body = it },
                label = { Text("Body") },
                minLines = 5
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                GeneralOutlinedButton(
                    caption = "Cancel",
                    onClick = goBack
                )

                Spacer(Modifier.width(8.dp))

                GeneralFilledButton(
                    caption = "Add Comment",
                    onClick = {
                        addComment(
                            name,
                            email,
                            body
                        )
                    },
                    icon = Icons.Rounded.Add
                )
            }
        }

        LoadingContainer(
            show = showLoading
        )
    }
}
