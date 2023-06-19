package org.imaginativeworld.whynotcompose.cms.ui.common

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme
import org.imaginativeworld.whynotcompose.common.compose.R as ComposeR
import timber.log.Timber

@Composable
fun EmptyView(
    modifier: Modifier = Modifier,
    loadState: CombinedLoadStates,
    itemCount: Int,
    title: String = "Nothing here!",
    message: String? = "No repository found."
) {
    Timber.e("itemCount: $itemCount")

    if (loadState.refresh !is LoadState.Loading) {
        Timber.e("loadState.refresh !is LoadState.Loading")
    }
    if (loadState.prepend !is LoadState.Loading) {
        Timber.e("loadState.prepend !is LoadState.Loading")
    }
    if (loadState.append !is LoadState.Loading) {
        Timber.e("loadState.append !is LoadState.Loading")
    }
    if (loadState.prepend.endOfPaginationReached) {
        Timber.e("loadState.prepend.endOfPaginationReached")
    }

    EmptyView(
        modifier = modifier,
        show = loadState.refresh !is LoadState.Loading &&
            loadState.prepend !is LoadState.Loading &&
            loadState.append !is LoadState.Loading &&
            loadState.prepend.endOfPaginationReached &&
            itemCount == 0,
        title = title,
        message = message
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyViewPreview() {
    CMSAppTheme {
        Surface {
            EmptyView(
                modifier = Modifier,
                show = true
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun EmptyViewPreviewDark() {
    CMSAppTheme {
        Surface {
            EmptyView(
                modifier = Modifier,
                show = true
            )
        }
    }
}

@Composable
fun EmptyView(
    modifier: Modifier = Modifier,
    show: Boolean,
    title: String = "Nothing here!",
    message: String? = "No item found!"
) {
    AnimatedVisibility(
        visible = show,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {},
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                Modifier
                    .padding(start = 32.dp, end = 32.dp, bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier
                        .size(145.dp),
                    painter = painterResource(id = ComposeR.drawable.ic_spider),
                    contentDescription = "Empty",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground.copy(.5f))
                )

                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 22.dp, end = 16.dp),
                    text = title,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground.copy(.75f)
                )

                if (message != null) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                        text = message,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground.copy(.75f)
                    )
                }
            }
        }
    }
}