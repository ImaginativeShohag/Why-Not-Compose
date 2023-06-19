package org.imaginativeworld.whynotcompose.cms.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme
import org.imaginativeworld.whynotcompose.cms.ui.common.button.GeneralOutlinedButton

@Composable
fun GeneralSheetAppBar(
    title: String,
    onCancelClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        actions = {
            GeneralOutlinedButton(
                caption = "Cancel"
            ) {
                onCancelClicked()
            }
        }
    )
}

@Preview
@Composable
fun GeneralSheetAppBarPreview() {
    CMSAppTheme {
        GeneralSheetAppBar(
            title = "Users",
            onCancelClicked = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GeneralSheetAppBarPreviewDark() {
    CMSAppTheme {
        GeneralSheetAppBar(
            title = "Users",
            onCancelClicked = {}
        )
    }
}