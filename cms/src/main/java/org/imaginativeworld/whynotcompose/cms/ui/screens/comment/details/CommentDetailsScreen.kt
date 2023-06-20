package org.imaginativeworld.whynotcompose.cms.ui.screens.comment.details

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
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

    LaunchedEffect(state.deleteSuccess) {
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
        onDeleteClicked = { openDeleteDialog = true },
        onEditClicked = {
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

@Preview
@Composable
fun CommentDetailsScreenSkeletonPreview() {
    CMSAppTheme {
        CommentDetailsScreenSkeleton(
            comment = MockData.dummyComment
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CommentDetailsScreenSkeletonPreviewDark() {
    CMSAppTheme {
        CommentDetailsScreenSkeleton(
            comment = MockData.dummyComment
        )
    }
}

@Composable
fun CommentDetailsScreenSkeleton(
    comment: Comment?,
    showLoading: Boolean = false,
    showMessage: Event<ActionMessage>? = null,
    goBack: () -> Unit = {},
    toggleUIMode: () -> Unit = {},
    retryDataLoad: () -> Unit = {},
    onDeleteClicked: () -> Unit = {},
    onEditClicked: () -> Unit = {},
    onCommentsClicked: () -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(showMessage) {
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
                            modifier = Modifier.weight(1f),
                            caption = "Delete",
                            icon = Icons.Rounded.Delete
                        ) {
                            onDeleteClicked()
                        }

                        Spacer(Modifier.width(16.dp))

                        GeneralOutlinedButton(
                            modifier = Modifier.weight(1f),
                            caption = "Edit",
                            icon = Icons.Rounded.Edit
                        ) {
                            onEditClicked()
                        }
                    }
                }
            }
        }

        LoadingContainer(
            show = showLoading
        )
    }
}
