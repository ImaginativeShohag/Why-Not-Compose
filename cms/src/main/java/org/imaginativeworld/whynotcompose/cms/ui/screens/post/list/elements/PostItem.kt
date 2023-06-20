package org.imaginativeworld.whynotcompose.cms.ui.screens.post.list.elements

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.imaginativeworld.whynotcompose.cms.repositories.MockData
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme
import org.imaginativeworld.whynotcompose.common.compose.R

@Composable
fun PostItem(
    modifier: Modifier = Modifier,
    title: String,
    body: String,
    featuredImageUrl: String,
    isPreview: Boolean,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.7f),
                model = featuredImageUrl,
                contentDescription = "Featured Image",
                placeholder = painterResource(id = R.drawable.default_placeholder),
                contentScale = ContentScale.Crop
            )

            Column(Modifier.padding(16.dp)) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = if (isPreview) 1 else Int.MAX_VALUE
                )

                Text(
                    body,
                    style = MaterialTheme.typography.labelLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = if (isPreview) 1 else Int.MAX_VALUE
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostItemPreview() {
    CMSAppTheme {
        Surface {
            Column(Modifier.padding(16.dp)) {
                PostItem(
                    title = MockData.dummyPost.title,
                    body = MockData.dummyPost.body,
                    isPreview = true,
                    featuredImageUrl = MockData.dummyPost.getFeaturedImageUrl()
                )

                Spacer(Modifier.height(16.dp))

                PostItem(
                    title = MockData.dummyPost.title,
                    body = MockData.dummyPost.body,
                    isPreview = false,
                    featuredImageUrl = MockData.dummyPost.getFeaturedImageUrl()
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PostItemPreviewDark() {
    CMSAppTheme {
        Surface {
            Column(Modifier.padding(16.dp)) {
                PostItem(
                    title = MockData.dummyPost.title,
                    body = MockData.dummyPost.body,
                    isPreview = true,
                    featuredImageUrl = MockData.dummyPost.getFeaturedImageUrl()
                )

                Spacer(Modifier.height(16.dp))

                PostItem(
                    title = MockData.dummyPost.title,
                    body = MockData.dummyPost.body,
                    isPreview = false,
                    featuredImageUrl = MockData.dummyPost.getFeaturedImageUrl()
                )
            }
        }
    }
}
