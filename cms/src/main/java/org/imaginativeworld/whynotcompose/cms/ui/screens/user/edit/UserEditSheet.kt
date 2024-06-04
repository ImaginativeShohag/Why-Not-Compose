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

package org.imaginativeworld.whynotcompose.cms.ui.screens.user.edit

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.base.extensions.toast
import org.imaginativeworld.whynotcompose.base.models.Event
import org.imaginativeworld.whynotcompose.cms.models.user.User
import org.imaginativeworld.whynotcompose.cms.repositories.MockData
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme
import org.imaginativeworld.whynotcompose.cms.ui.compositions.GeneralSheetAppBar
import org.imaginativeworld.whynotcompose.cms.ui.compositions.LoadingContainer
import org.imaginativeworld.whynotcompose.cms.ui.compositions.button.GeneralFilledButton
import org.imaginativeworld.whynotcompose.cms.ui.compositions.button.GeneralOutlinedButton

@Composable
fun UserEditSheet(
    viewModel: UserEditViewModel = hiltViewModel(),
    userId: Int,
    showSheet: MutableState<Boolean>,
    onSuccess: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { sheetState ->
            return@rememberModalBottomSheetState sheetState != SheetValue.Hidden
        }
    )

    val state by viewModel.state.collectAsState()

    val goBack: () -> Unit = {
        scope.launch {
            bottomSheetState.hide()
        }.invokeOnCompletion {
            if (!bottomSheetState.isVisible) {
                showSheet.value = false
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getDetails(userId)
    }

    LaunchedEffect(state.updateUserSuccess) {
        state.updateUserSuccess?.getValueOnce()?.let { isAddUserSuccess ->
            if (isAddUserSuccess) {
                context.toast("User successfully updated.")

                goBack()
                onSuccess()
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = { showSheet.value = false },
        sheetState = bottomSheetState
    ) {
        UserEditSheetSkeleton(
            user = state.user,
            showLoading = state.loading,
            showMessage = state.message,
            goBack = goBack,
            updateUser = { name, email, gender, status ->
                viewModel.update(
                    userId,
                    name,
                    email,
                    gender,
                    status
                )
            }
        )
    }
}

@Preview
@Composable
fun UserEditSheetSkeletonPreview() {
    CMSAppTheme {
        UserEditSheetSkeleton(
            user = MockData.dummyUser
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UserEditSheetSkeletonPreviewDark() {
    CMSAppTheme {
        UserEditSheetSkeleton(
            user = MockData.dummyUser
        )
    }
}

@Composable
fun UserEditSheetSkeleton(
    user: User?,
    showLoading: Boolean = false,
    showMessage: Event<String>? = null,
    goBack: () -> Unit = {},
    updateUser: (
        name: String,
        email: String,
        gender: String,
        status: String
    ) -> Unit = { _, _, _, _ -> }
) {
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    var name by rememberSaveable(user) { mutableStateOf(user?.name ?: "") }
    var email by rememberSaveable(user) { mutableStateOf(user?.email ?: "") }

    val genderOptions = remember { listOf("Male", "Female") }
    var genderDropdownExpended by remember { mutableStateOf(false) }
    var selectedGenderOption by rememberSaveable(user) {
        mutableStateOf(
            user?.getGenderLabel() ?: ""
        )
    }

    val statusOptions = remember { listOf("Active", "Inactive") }
    var statusDropdownExpended by remember { mutableStateOf(false) }
    var selectedStatusOption by rememberSaveable(user) {
        mutableStateOf(
            user?.getStatusLabel() ?: ""
        )
    }

    LaunchedEffect(showMessage) {
        showMessage?.getValueOnce()?.let { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        Modifier.heightIn(max = 385.dp),
        topBar = {
            GeneralSheetAppBar(
                title = "Edit User",
                onCancelClicked = goBack
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = BottomSheetDefaults.ContainerColor,
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
    ) { innerPadding ->
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = user != null,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
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

                ExposedDropdownMenuBox(
                    expanded = genderDropdownExpended,
                    onExpandedChange = {
                        genderDropdownExpended = !genderDropdownExpended
                    }
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        readOnly = true,
                        value = selectedGenderOption,
                        onValueChange = {},
                        label = { Text("Gender") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = genderDropdownExpended
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = genderDropdownExpended,
                        onDismissRequest = { genderDropdownExpended = false }
                    ) {
                        genderOptions.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption) },
                                onClick = {
                                    selectedGenderOption = selectionOption
                                    genderDropdownExpended = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    expanded = statusDropdownExpended,
                    onExpandedChange = {
                        statusDropdownExpended = !statusDropdownExpended
                    }
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        readOnly = true,
                        value = selectedStatusOption,
                        onValueChange = {},
                        label = { Text("Status") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = statusDropdownExpended
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = statusDropdownExpended,
                        onDismissRequest = { statusDropdownExpended = false }
                    ) {
                        statusOptions.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption) },
                                onClick = {
                                    selectedStatusOption = selectionOption
                                    statusDropdownExpended = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }

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
                        caption = "Update User",
                        icon = Icons.Rounded.Check,
                        onClick = {
                            updateUser(
                                name,
                                email,
                                selectedGenderOption,
                                selectedStatusOption
                            )
                        }
                    )
                }
            }
        }

        LoadingContainer(
            show = showLoading
        )
    }
}
