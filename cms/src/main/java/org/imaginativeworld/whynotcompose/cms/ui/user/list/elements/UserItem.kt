package org.imaginativeworld.whynotcompose.cms.ui.user.list.elements

import android.R
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme
import org.imaginativeworld.whynotcompose.common.compose.theme.TailwindCSSColor
import org.imaginativeworld.whynotcompose.common.compose.R as CommonR

@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    name: String,
    email: String,
    gender: String,
    status: String,
    statusColor: Color,
    userImageUrl: String,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(Modifier.padding(8.dp)) {
            Row(
                Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(24.dp)
                        .clip(CircleShape),
                    model = userImageUrl,
                    contentDescription = "User Image",
                    placeholder = painterResource(id = CommonR.drawable.default_placeholder)
                )

                Text(
                    name,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                email,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row {
                Text(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .weight(1f),
                    text = gender,
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
fun UserItemPreview() {
    CMSAppTheme {
        UserItem(
            name = "Mahmudul Hasan Shohag Mahmudul Hasan Shohag",
            email = "imaginativeshohag@gmail.com",
            gender = "male",
            status = "Active",
            statusColor = TailwindCSSColor.Red500,
            userImageUrl = "https://picsum.photos/seed/u1/200/200"
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UserItemPreviewDark() {
    CMSAppTheme {
        UserItem(
            name = "Mahmudul Hasan Shohag",
            email = "imaginativeshohag@gmail.com",
            gender = "male",
            status = "Active",
            statusColor = TailwindCSSColor.Red500,
            userImageUrl = "https://picsum.photos/seed/u1/200/200"
        )
    }
}
