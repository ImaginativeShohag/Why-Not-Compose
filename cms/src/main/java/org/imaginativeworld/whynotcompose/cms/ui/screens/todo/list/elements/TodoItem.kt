package org.imaginativeworld.whynotcompose.cms.ui.screens.todo.list.elements

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme
import org.imaginativeworld.whynotcompose.common.compose.theme.TailwindCSSColor

@Composable
fun TodoItem(
    modifier: Modifier = Modifier,
    title: String,
    dueDate: String,
    status: String,
    statusColor: Color,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                title,
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis
            )

            Row {
                Text(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .weight(1f),
                    text = "Due: $dueDate",
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    status,
                    style = MaterialTheme.typography.labelLarge,
                    color = statusColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoItemPreview() {
    CMSAppTheme {
        TodoItem(
            title = "The quick brown fox jumps over the lazy dog.",
            dueDate = "Tue, 20 Jun 2023",
            status = "Completed",
            statusColor = TailwindCSSColor.Red500
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TodoItemPreviewDark() {
    CMSAppTheme {
        TodoItem(
            title = "The quick brown fox jumps over the lazy dog.",
            dueDate = "Tue, 20 Jun 2023",
            status = "Completed",
            statusColor = TailwindCSSColor.Red500
        )
    }
}
