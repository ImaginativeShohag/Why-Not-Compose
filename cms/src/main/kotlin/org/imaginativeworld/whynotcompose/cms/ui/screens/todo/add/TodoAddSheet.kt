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

package org.imaginativeworld.whynotcompose.cms.ui.screens.todo.add

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.util.Calendar
import java.util.Date
import java.util.TimeZone
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.base.extensions.getYYYYMMDD
import org.imaginativeworld.whynotcompose.base.extensions.toast
import org.imaginativeworld.whynotcompose.base.models.Event
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme
import org.imaginativeworld.whynotcompose.cms.ui.compositions.GeneralSheetAppBar
import org.imaginativeworld.whynotcompose.cms.ui.compositions.LoadingContainer
import org.imaginativeworld.whynotcompose.cms.ui.compositions.button.GeneralFilledButton
import org.imaginativeworld.whynotcompose.cms.ui.compositions.button.GeneralOutlinedButton
import org.imaginativeworld.whynotcompose.cms.ui.compositions.button.GeneralTextButton

@Suppress("ktlint:compose:mutable-state-param-check")
@Composable
fun TodoAddSheet(
    userId: Int,
    showSheet: MutableState<Boolean>,
    onSuccess: () -> Unit,
    viewModel: TodoAddViewModel = hiltViewModel()
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

    LaunchedEffect(state.addTodoSuccess, onSuccess) {
        state.addTodoSuccess?.getValueOnce()?.let { isAddTodoSuccess ->
            if (isAddTodoSuccess) {
                context.toast("Todo successfully added.")

                goBack()
                onSuccess()
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = { showSheet.value = false },
        sheetState = bottomSheetState
    ) {
        TodoAddSheetSkeleton(
            showLoading = state.loading,
            showMessage = state.message,
            goBack = goBack,
            addTodo = { title, dueDate, status ->
                viewModel.addTodo(
                    userId,
                    title,
                    dueDate,
                    status
                )
            }
        )
    }
}

@PreviewLightDark
@Composable
private fun TodoAddSheetSkeletonPreview() {
    CMSAppTheme {
        TodoAddSheetSkeleton(
            showLoading = false,
            showMessage = null
        )
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun TodoAddSheetSkeleton(
    showLoading: Boolean,
    showMessage: Event<String>?,
    goBack: () -> Unit = {},
    addTodo: (
        title: String,
        dueDate: Date?,
        status: String
    ) -> Unit = { _, _, _ -> }
) {
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    var openDatePickerDialog by remember { mutableStateOf(false) }

    var title by rememberSaveable { mutableStateOf("") }
    var dueDate by rememberSaveable { mutableStateOf<Date?>(null) }

    val statusOptions = remember { listOf("Pending", "Completed") }
    var statusDropdownExpended by remember { mutableStateOf(false) }
    var selectedStatusOption by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(showMessage) {
        showMessage?.getValueOnce()?.let { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        Modifier.heightIn(max = 321.dp),
        topBar = {
            GeneralSheetAppBar(
                title = "Add Todo",
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
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                singleLine = true
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            openDatePickerDialog = true
                        }
                        .focusable(false),
                    readOnly = true,
                    value = dueDate.getYYYYMMDD(),
                    onValueChange = {},
                    label = { Text("Due Date") },
                    singleLine = true
                )

                GeneralTextButton(
                    caption = "Select",
                    {
                        openDatePickerDialog = true
                    },
                    modifier = Modifier.padding(top = 8.dp),
                    icon = Icons.Rounded.CalendarMonth
                )
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
                    caption = "Add Todo",
                    onClick = {
                        addTodo(
                            title,
                            dueDate,
                            selectedStatusOption
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

    // ----------------------------------------------------------------
    // Dialog
    // ----------------------------------------------------------------

    if (openDatePickerDialog) {
        val initialSelectedDate = remember {
            val localCalendar = Calendar.getInstance()
            val utcCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            utcCalendar.clear()
            utcCalendar.set(
                localCalendar.get(Calendar.YEAR),
                localCalendar.get(Calendar.MONTH),
                localCalendar.get(Calendar.DATE)
            )
            utcCalendar.timeInMillis
        }
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = initialSelectedDate,
            selectableDates = object : SelectableDates {
                val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

                // Blocks previous dates from being selected.
                override fun isSelectableDate(utcTimeMillis: Long): Boolean = utcTimeMillis >= calendar.timeInMillis

                // Allow selecting dates from current year forward.
                override fun isSelectableYear(year: Int): Boolean = year >= calendar.get(Calendar.YEAR)
            }
        )

        val datePickerConfirmButtonEnabled = remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }

        DatePickerDialog(
            onDismissRequest = {
                openDatePickerDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDatePickerDialog = false

                        datePickerState.selectedDateMillis?.let {
                            dueDate = Date(it)
                        }
                    },
                    enabled = datePickerConfirmButtonEnabled.value
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDatePickerDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                title = {
                    Text(
                        "Due Date",
                        Modifier.padding(start = 24.dp, end = 12.dp, top = 16.dp)
                    )
                }
            )
        }
    }
}
