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

package org.imaginativeworld.whynotcompose.cms.ui.user.details

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
import androidx.compose.material.icons.rounded.Ballot
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.TextSnippet
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
import org.imaginativeworld.whynotcompose.cms.models.user.User
import org.imaginativeworld.whynotcompose.cms.repositories.MockData
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme
import org.imaginativeworld.whynotcompose.cms.ui.common.GeneralAppBar
import org.imaginativeworld.whynotcompose.cms.ui.common.LoadingContainer
import org.imaginativeworld.whynotcompose.cms.ui.common.LoadingItem
import org.imaginativeworld.whynotcompose.cms.ui.common.button.GeneralFilledButton
import org.imaginativeworld.whynotcompose.cms.ui.common.button.GeneralOutlinedButton
import org.imaginativeworld.whynotcompose.cms.ui.user.edit.UserEditSheet
import org.imaginativeworld.whynotcompose.cms.ui.user.list.elements.UserItem

@Composable
fun UserDetailsScreen(
    viewModel: UserDetailsViewModel,
    userId: Int,
    goBack: () -> Unit,
    toggleUIMode: () -> Unit,
    onTodosClicked: () -> Unit,
    onPostsClicked: () -> Unit
) {
    val openEditUserSheet = rememberSaveable { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()

    var openDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getDetails(userId)
    }

    LaunchedEffect(state.deleteSuccess) {
        if (state.deleteSuccess?.getValueOnce() == true) {
            goBack()
        }
    }

    UserDetailsScreenSkeleton(
        user = state.user,
        showLoading = state.loading,
        showMessage = state.message,
        goBack = goBack,
        toggleUIMode = toggleUIMode,
        retryDataLoad = {
            viewModel.getDetails(userId)
        },
        onDeleteClicked = { openDeleteDialog = true },
        onEditClicked = {
            openEditUserSheet.value = !openEditUserSheet.value
        },
        onTodosClicked = onTodosClicked,
        onPostsClicked = onPostsClicked
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
                Text(text = "Are you sure you want to delete this todo?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDeleteDialog = false

                        viewModel.delete(userId)
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

    if (openEditUserSheet.value) {
        UserEditSheet(
            showSheet = openEditUserSheet,
            userId = userId,
            onSuccess = {
                viewModel.getDetails(userId)
            }
        )
    }
}

@Preview
@Composable
fun UserDetailsScreenSkeletonPreview() {
    CMSAppTheme {
        UserDetailsScreenSkeleton(
            user = MockData.dummyUser
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UserDetailsScreenSkeletonPreviewDark() {
    CMSAppTheme {
        UserDetailsScreenSkeleton(
            user = MockData.dummyUser
        )
    }
}

@Composable
fun UserDetailsScreenSkeleton(
    user: User?,
    showLoading: Boolean = false,
    showMessage: Event<ActionMessage>? = null,
    goBack: () -> Unit = {},
    toggleUIMode: () -> Unit = {},
    retryDataLoad: () -> Unit = {},
    onDeleteClicked: () -> Unit = {},
    onEditClicked: () -> Unit = {},
    onTodosClicked: () -> Unit = {},
    onPostsClicked: () -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(showMessage) {
        showMessage?.getValueOnce()?.let { actionMessage ->
            when (actionMessage.action) {
                UserDetailsViewAction.USER_LOAD_ERROR -> {
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
                subTitle = "User Details",
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
            if (user == null) {
                LoadingItem(
                    Modifier
                        .padding(
                            top = 4.dp,
                            bottom = 16.dp
                        )
                )
            } else {
                UserItem(
                    modifier = Modifier.padding(
                        top = 4.dp
                    ),
                    name = user.name,
                    email = user.email,
                    gender = user.getGenderLabel(),
                    status = user.getStatusLabel(),
                    userImageUrl = user.getAvatarImageUrl(),
                    statusColor = user.getStatusColor(),
                    onClick = {
                        // open details sheet
                        // do something...
                    }
                )
            }

            AnimatedVisibility(visible = user != null) {
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

                    Row {
                        GeneralFilledButton(
                            modifier = Modifier.weight(1f),
                            caption = "Todos",
                            icon = Icons.Rounded.Ballot
                        ) {
                            onTodosClicked()
                        }

                        Spacer(Modifier.width(16.dp))

                        GeneralFilledButton(
                            modifier = Modifier.weight(1f),
                            caption = "Posts",
                            icon = Icons.Rounded.TextSnippet
                        ) {
                            onPostsClicked()
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
