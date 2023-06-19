package org.imaginativeworld.whynotcompose.cms.ui.user.add

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import org.imaginativeworld.whynotcompose.base.extensions.toast
import org.imaginativeworld.whynotcompose.base.models.Event
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme
import org.imaginativeworld.whynotcompose.cms.ui.common.GeneralSheetAppBar
import org.imaginativeworld.whynotcompose.cms.ui.common.LoadingContainer
import org.imaginativeworld.whynotcompose.cms.ui.common.button.GeneralFilledButton
import org.imaginativeworld.whynotcompose.cms.ui.common.button.GeneralOutlinedButton

@Composable
fun UserAddSheet(
    viewModel: UserAddViewModel = hiltViewModel(),
    goBack: () -> Unit,
    onSuccess: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.addUserSuccess) {
        state.addUserSuccess?.getValueOnce()?.let { isAddUserSuccess ->
            if (isAddUserSuccess) {
                context.toast("User successfully added.")

                onSuccess()
                goBack()
            }
        }
    }

    UserAddSheetSkeleton(
        showLoading = state.loading,
        showMessage = state.message,
        goBack = goBack,
        addUser = { name, email, gender, status ->
            viewModel.addUser(
                name,
                email,
                gender,
                status
            )
        }
    )
}

@Preview
@Composable
fun UserAddSheetSkeletonPreview() {
    CMSAppTheme {
        UserAddSheetSkeleton()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UserAddSheetSkeletonPreviewDark() {
    CMSAppTheme {
        UserAddSheetSkeleton()
    }
}

@Composable
fun UserAddSheetSkeleton(
    showLoading: Boolean = false,
    showMessage: Event<String>? = null,
    goBack: () -> Unit = {},
    addUser: (
        name: String,
        email: String,
        gender: String,
        status: String
    ) -> Unit = { _, _, _, _ -> }
) {
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    val genderOptions = listOf("Male", "Female")
    var genderDropdownExpended by remember { mutableStateOf(false) }
    var selectedGenderOption by remember { mutableStateOf("") }

    val statusOptions = listOf("Active", "Inactive")
    var statusDropdownExpended by remember { mutableStateOf(false) }
    var selectedStatusOption by remember { mutableStateOf("") }

    LaunchedEffect(showMessage) {
        showMessage?.getValueOnce()?.let { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        Modifier,
        topBar = {
            GeneralSheetAppBar(
                title = "Add User",
                onCancelClicked = goBack
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
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
                label = { Text("Name") }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                )
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
                    caption = "Add User",
                    icon = Icons.Filled.Add,
                    onClick = {
                        addUser(
                            name,
                            email,
                            selectedGenderOption,
                            selectedStatusOption
                        )
                    }
                )
            }
        }

        LoadingContainer(
            show = showLoading
        )
    }
}
