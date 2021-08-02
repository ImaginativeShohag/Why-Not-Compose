package org.imaginativeworld.whynotcompose.ui.compositions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme


@Preview
@Composable
fun DropDownSpinnerPreview() {
    val countryList = listOf(
        "Bangladesh",
        "Pakistan",
        "Palestine",
        "Malaysia",
    )
    val selectedItem1 = remember { mutableStateOf("") }
    val selectedItem2 = remember { mutableStateOf("Bangladesh") }

    AppTheme {
        Column {
            DropDownSpinner(
                modifier = Modifier.padding(10.dp),
                defaultText = "Select Category...",
                selectedItem = selectedItem1.value,
                onItemSelected = { index, item ->

                },
                itemList = countryList
            )

            DropDownSpinner(
                modifier = Modifier.padding(10.dp),
                defaultText = "Select Category...",
                selectedItem = selectedItem2.value,
                onItemSelected = { index, item ->

                },
                itemList = countryList
            )
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
    val isOpen = remember { mutableStateOf(false) }

    Box(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFF1F3F2))
            .height(48.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        if (selectedItem == null || selectedItem.toString().isEmpty()) {
            Text(
                text = defaultText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 3.dp),
                color = MaterialTheme.colors.onBackground.copy(.5f)
            )
        }

        Text(
            text = selectedItem?.toString() ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 32.dp, bottom = 3.dp),
        )

        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 32.dp),
            expanded = isOpen.value,
            onDismissRequest = {
                isOpen.value = false
            },
        ) {
            itemList?.forEachIndexed { index, item ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        isOpen.value = false
                        onItemSelected(index, item)
                    }
                ) {
                    Text(item.toString(), modifier = Modifier)
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
                    onClick = { isOpen.value = true }
                )
        )
    }
}