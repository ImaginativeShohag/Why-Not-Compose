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

package org.imaginativeworld.whynotcompose.cms.ui.screens.comment.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.base.models.Event
import org.imaginativeworld.whynotcompose.cms.models.ActionMessage
import org.imaginativeworld.whynotcompose.cms.models.Comment
import org.imaginativeworld.whynotcompose.cms.repositories.MockData
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme
import org.imaginativeworld.whynotcompose.cms.ui.compositions.GeneralAppBar
import org.imaginativeworld.whynotcompose.cms.ui.compositions.LoadingContainer
import org.imaginativeworld.whynotcompose.cms.ui.compositions.LoadingItem
import org.imaginativeworld.whynotcompose.cms.ui.compositions.button.GeneralOutlinedButton
import org.imaginativeworld.whynotcompose.cms.ui.screens.comment.edit.CommentEditSheet
import org.imaginativeworld.whynotcompose.cms.ui.screens.comment.list.elements.CommentItem

@Composable
fun CommentDetailsScreen(
    viewModel: CommentDetailsViewModel,
    postId: Int,
    commentId: Int,
    goBack: () -> Unit,
    toggleUIMode: () -> Unit
) {
    val openEditCommentSheet = rememberSaveable { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()

    var openDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getDetails(commentId)
    }

    LaunchedEffect(state.deleteSuccess, goBack) {
        if (state.deleteSuccess?.getValueOnce() == true) {
            goBack()
        }
    }

    CommentDetailsScreenSkeleton(
        comment = state.comment,
        showLoading = state.loading,
        showMessage = state.message,
        goBack = goBack,
        toggleUIMode = toggleUIMode,
        retryDataLoad = {
            viewModel.getDetails(commentId)
        },
        onDeleteClick = { openDeleteDialog = true },
        onEditClick = {
            openEditCommentSheet.value = !openEditCommentSheet.value
        }
    )

    // ----------------------------------------------------------------
    // Alerts
    // ----------------------------------------------------------------

    if (openDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                openDeleteDialog = false
            },
            title = {
                Text(text = "Delete")
            },
            text = {
                Text(text = "Are you sure you want to delete this comment?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDeleteDialog = false

                        viewModel.deleteComment(commentId)
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDeleteDialog = false
                    }
                ) {
                    Text("No")
                }
            }
        )
    }

    // ----------------------------------------------------------------
    // Bottom Sheet
    // ----------------------------------------------------------------

    if (openEditCommentSheet.value) {
        CommentEditSheet(
            showSheet = openEditCommentSheet,
            postId = postId,
            commentId = commentId,
            onSuccess = {
                viewModel.getDetails(commentId)
            }
        )
    }
}

@PreviewLightDark
@Composable
private fun CommentDetailsScreenSkeletonPreviewDark() {
    CMSAppTheme {
        CommentDetailsScreenSkeleton(
            showLoading = false,
            showMessage = null,
            comment = MockData.dummyComment
        )
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun CommentDetailsScreenSkeleton(
    showLoading: Boolean,
    showMessage: Event<ActionMessage>?,
    comment: Comment?,
    goBack: () -> Unit = {},
    toggleUIMode: () -> Unit = {},
    retryDataLoad: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onEditClick: () -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(showMessage, retryDataLoad) {
        showMessage?.getValueOnce()?.let { actionMessage ->
            when (actionMessage.action) {
                CommentDetailsViewAction.COMMENT_LOAD_ERROR -> {
                    val result = snackbarHostState.showSnackbar(
                        actionMessage.message,
                        actionLabel = "Retry"
                    )

                    if (result == SnackbarResult.ActionPerformed) {
                        retryDataLoad()
                    }
                }

                else -> {
                    snackbarHostState.showSnackbar(actionMessage.message)
                }
            }
        }
    }

    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            GeneralAppBar(
                subTitle = "Comment Details",
                goBack = goBack,
                toggleUIMode = toggleUIMode
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            if (comment == null) {
                LoadingItem(
                    Modifier
                        .padding(
                            top = 4.dp,
                            bottom = 16.dp
                        )
                )
            } else {
                CommentItem(
                    modifier = Modifier.padding(
                        top = 4.dp
                    ),
                    name = comment.name,
                    email = comment.email,
                    body = comment.body,
                    isPreview = false,
                    userImageUrl = comment.getAvatarImageUrl(),
                    onClick = {}
                )
            }

            AnimatedVisibility(visible = comment != null) {
                Column {
                    Spacer(Modifier.height(16.dp))

                    Row {
                        GeneralOutlinedButton(
                            caption = "Delete",
                            {
                                onDeleteClick()
                            },
                            modifier = Modifier.weight(1f),
                            icon = Icons.Rounded.Delete
                        )

                        Spacer(Modifier.width(16.dp))

                        GeneralOutlinedButton(
                            caption = "Edit",
                            {
                                onEditClick()
                            },
                            modifier = Modifier.weight(1f),
                            icon = Icons.Rounded.Edit
                        )
                    }
                }
            }
        }

        LoadingContainer(
            show = showLoading
        )
    }
}
