package org.imaginativeworld.whynotcompose.cms.ui.screens.post.details

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.rounded.Chat
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
import org.imaginativeworld.whynotcompose.cms.models.Post
import org.imaginativeworld.whynotcompose.cms.repositories.MockData
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme
import org.imaginativeworld.whynotcompose.cms.ui.compositions.GeneralAppBar
import org.imaginativeworld.whynotcompose.cms.ui.compositions.LoadingContainer
import org.imaginativeworld.whynotcompose.cms.ui.compositions.LoadingItem
import org.imaginativeworld.whynotcompose.cms.ui.compositions.button.GeneralFilledButton
import org.imaginativeworld.whynotcompose.cms.ui.compositions.button.GeneralOutlinedButton
import org.imaginativeworld.whynotcompose.cms.ui.screens.post.add.PostAddSheet
import org.imaginativeworld.whynotcompose.cms.ui.screens.post.edit.PostEditSheet
import org.imaginativeworld.whynotcompose.cms.ui.screens.post.list.elements.PostItem

@Composable
fun PostDetailsScreen(
    viewModel: PostDetailsViewModel,
    userId: Int,
    postId: Int,
    goBack: () -> Unit,
    toggleUIMode: () -> Unit,
    onCommentsClicked: () -> Unit
) {
    val openEditPostSheet = rememberSaveable { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()

    var openDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getDetails(postId)
    }

    LaunchedEffect(state.deleteSuccess) {
        if (state.deleteSuccess?.getValueOnce() == true) {
            goBack()
        }
    }

    PostDetailsScreenSkeleton(
        post = state.post,
        showLoading = state.loading,
        showMessage = state.message,
        goBack = goBack,
        toggleUIMode = toggleUIMode,
        retryDataLoad = {
            viewModel.getDetails(postId)
        },
        onDeleteClicked = { openDeleteDialog = true },
        onEditClicked = {
            openEditPostSheet.value = !openEditPostSheet.value
        },
        onCommentsClicked = onCommentsClicked
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
                Text(text = "Are you sure you want to delete this post?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDeleteDialog = false

                        viewModel.deletePost(postId)
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

    if (openEditPostSheet.value) {
        PostEditSheet(
            showSheet = openEditPostSheet,
            userId = userId,
            postId = postId,
            onSuccess = {
                viewModel.getDetails(postId)
            }
        )
    }
}

@Preview
@Composable
fun PostDetailsScreenSkeletonPreview() {
    CMSAppTheme {
        PostDetailsScreenSkeleton(
            post = MockData.dummyPost
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PostDetailsScreenSkeletonPreviewDark() {
    CMSAppTheme {
        PostDetailsScreenSkeleton(
            post = MockData.dummyPost
        )
    }
}

@Composable
fun PostDetailsScreenSkeleton(
    post: Post?,
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
                PostDetailsViewAction.POST_LOAD_ERROR -> {
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
                subTitle = "Post Details",
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
            if (post == null) {
                LoadingItem(
                    Modifier
                        .padding(
                            top = 4.dp,
                            bottom = 16.dp
                        )
                )
            } else {
                PostItem(
                    modifier = Modifier.padding(
                        top = 4.dp
                    ),
                    title = post.title,
                    body = post.body,
                    featuredImageUrl = post.getFeaturedImageUrl(),
                    isPreview = false,
                    onClick = {}
                )
            }

            AnimatedVisibility(visible = post != null) {
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

                    Row(horizontalArrangement = Arrangement.Center) {
                        Spacer(Modifier.weight(1f))

                        GeneralFilledButton(
                            modifier = Modifier.weight(2f),
                            caption = "Comments",
                            icon = Icons.Rounded.Chat
                        ) {
                            onCommentsClicked()
                        }

                        Spacer(Modifier.weight(1f))
                    }
                }
            }
        }

        LoadingContainer(
            show = showLoading
        )
    }
}
