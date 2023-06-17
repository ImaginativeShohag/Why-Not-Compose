package org.imaginativeworld.whynotcompose.cms.ui.user.add

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme
import org.imaginativeworld.whynotcompose.cms.ui.common.GeneralSheetAppBar
import org.imaginativeworld.whynotcompose.cms.ui.common.button.GeneralFilledButton
import org.imaginativeworld.whynotcompose.cms.ui.common.button.GeneralOutlinedButton

@Composable
fun UserAddSheet(
    goBack: () -> Unit,
    addUser: (
        name: String,
        email: String,
        gender: String,
        status: String
    ) -> Unit
) {
    UserAddSheetSkeleton(
        goBack = goBack,
        addUser = addUser
    )
}

@Preview
@Composable
fun TemplateChildScreenSkeletonPreview() {
    CMSAppTheme {
        UserAddSheetSkeleton()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TemplateChildScreenSkeletonPreviewDark() {
    CMSAppTheme {
        UserAddSheetSkeleton()
    }
}

@Composable
fun UserAddSheetSkeleton(
    goBack: () -> Unit = {},
    addUser: (
        name: String,
        email: String,
        gender: String,
        status: String
    ) -> Unit = { _, _, _, _ -> }
) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    val genderOptions = listOf("Male", "Female")
    var genderDropdownExpended by remember { mutableStateOf(false) }
    var selectedGenderOption by remember { mutableStateOf("") }

    val statusOptions = listOf("Active", "Inactive")
    var statusDropdownExpended by remember { mutableStateOf(false) }
    var selectedStatusOption by remember { mutableStateOf("") }

    Scaffold(
        Modifier,
        topBar = {
            GeneralSheetAppBar(
                title = "Users",
                subTitle = "Add"
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(16.dp)
                .padding(innerPadding)
                .fillMaxSize(),
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
                label = { Text("Email") }
            )

            ExposedDropdownMenuBox(
                expanded = genderDropdownExpended,
                onExpandedChange = {
                    genderDropdownExpended = !genderDropdownExpended
                }
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    readOnly = true,
                    value = selectedGenderOption,
                    onValueChange = {},
                    label = { Text("Gender") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = genderDropdownExpended
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
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
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
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
                    caption = "Add",
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
    }
}
