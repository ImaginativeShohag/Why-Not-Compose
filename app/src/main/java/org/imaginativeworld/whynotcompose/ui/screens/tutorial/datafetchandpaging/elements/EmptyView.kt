package org.imaginativeworld.whynotcompose.ui.screens.tutorial.datafetchandpaging.elements

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.ui.theme.AppColor
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme
import timber.log.Timber

@Composable
fun EmptyView(
    modifier: Modifier = Modifier,
    loadState: CombinedLoadStates,
    itemCount: Int,
    title: String = "Nothing here!",
    message: String? = "No repository found.",
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
        show = loadState.refresh !is LoadState.Loading
                && loadState.prepend !is LoadState.Loading
                && loadState.append !is LoadState.Loading
                && loadState.prepend.endOfPaginationReached
                && itemCount == 0,
        title = title,
        message = message,
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyViewPreview() {
    AppTheme {
        EmptyView(
            modifier = Modifier,
            show = true,
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun EmptyViewPreviewDark() {
    AppTheme(darkTheme = true) {
        EmptyView(
            modifier = Modifier,
            show = true,
        )
    }
}

@Composable
fun EmptyView(
    modifier: Modifier = Modifier,
    show: Boolean,
    title: String = "Nothing here!",
    message: String? = "No repository found!",
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
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    modifier = Modifier
                        .size(145.dp),
                    painter = painterResource(id = R.drawable.ic_spider),
                    contentDescription = "Empty",
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground.copy(.5f))
                )

                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 22.dp, end = 16.dp),
                    text = title,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colors.onBackground.copy(.75f),
                )

                if (message != null) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                        text = message,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colors.onBackground.copy(.75f),
                    )
                }
            }
        }
    }
}