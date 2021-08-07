package org.imaginativeworld.whynotcompose.ui.screens.composition.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.ui.screens.AppComponent
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme

private val ELEMENT_HEIGHT = 48.dp

@Composable
fun DropDownMenuScreen() {
    DropDownMenuScreenSkeleton()
}

@Preview
@Composable
fun DropDownMenuScreenSkeletonPreview() {
    AppTheme {
        DropDownMenuScreenSkeleton()
    }
}

@Preview
@Composable
fun DropDownMenuScreenSkeletonPreviewDark() {
    AppTheme(darkTheme = true) {
        DropDownMenuScreenSkeleton()
    }
}

@Composable
fun DropDownMenuScreenSkeleton() {
    Scaffold(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            AppComponent.Header("DropDown Menu")

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

            AppComponent.SubHeader("Menu")

            // ----------------------------------------------------------------

            var expanded by remember { mutableStateOf(false) }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.TopCenter),
            ) {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Localized description")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(onClick = {
                        expanded = false

                        /* Handle refresh! */
                    }) {
                        Text("Refresh")
                    }

                    DropdownMenuItem(onClick = {
                        expanded = false

                        /* Handle settings! */
                    }) {
                        Text("Settings")
                    }

                    Divider()

                    DropdownMenuItem(onClick = {
                        expanded = false

                        /* Handle send feedback! */
                    }) {
                        Text("Send Feedback")
                    }
                }
            }

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

            AppComponent.SubHeader("Spinner")

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            val countryList = listOf(
                "Bangladesh",
                "Pakistan",
                "Palestine",
                "Malaysia",
            )
            var selectedItem1 by remember { mutableStateOf("") }
            var selectedItem2 by remember { mutableStateOf("Bangladesh") }

            DropDownSpinner(
                modifier = Modifier.padding(10.dp),
                defaultText = "Select Country...",
                selectedItem = selectedItem1,
                onItemSelected = { index, item ->
                    selectedItem1 = item
                },
                itemList = countryList
            )

            DropDownSpinner(
                modifier = Modifier.padding(10.dp),
                defaultText = "Select Country...",
                selectedItem = selectedItem2,
                onItemSelected = { index, item ->
                    selectedItem2 = item
                },
                itemList = countryList
            )

            // ----------------------------------------------------------------

            AppComponent.BigSpacer()
        }
    }
}

@Composable
fun <E> DropDownSpinner(
    modifier: Modifier = Modifier,
    defaultText: String = "Select...",
    selectedItem: E,
    onItemSelected: (Int, E) -> Unit,
    itemList: List<E>?
) {
    var isOpen by remember { mutableStateOf(false) }

    Box(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colors.surface)
            .height(ELEMENT_HEIGHT),
        contentAlignment = Alignment.CenterStart
    ) {
        if (selectedItem == null || selectedItem.toString().isEmpty()) {
            Text(
                text = defaultText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 3.dp),
                color = MaterialTheme.colors.onSurface.copy(.45f)
            )
        }

        Text(
            text = selectedItem?.toString() ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 32.dp, bottom = 3.dp),
            color = MaterialTheme.colors.onSurface
        )

        DropdownMenu(
            modifier = Modifier.fillMaxWidth(.85f),
            expanded = isOpen,
            onDismissRequest = {
                isOpen = false
            },
        ) {
            itemList?.forEachIndexed { index, item ->
                DropdownMenuItem(
                    onClick = {
                        isOpen = false
                        onItemSelected(index, item)
                    }
                ) {
                    Text(item.toString())
                }
            }
        }

        Icon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp)
                .size(24.dp),
            painter = painterResource(id = R.drawable.ic_arrow_down),
            contentDescription = "Dropdown"
        )

        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .clickable(
                    onClick = { isOpen = true }
                )
        )
    }
}
