package org.imaginativeworld.whynotcompose.ui.compositions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme
import org.imaginativeworld.whynotcompose.ui.theme.FlatColor
import timber.log.Timber

@Preview
@Composable
fun ErrorItemPreview() {
    AppTheme {
        ErrorItem(
            "Something went wrong!"
        ) {

        }
    }
}

@Composable
fun ErrorItem(
    errorMessage: String,
    onRetryClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp,
        backgroundColor = FlatColor.FlatRed1
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp),
                text = errorMessage,
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            DefaultTextButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp),
                text = "Retry",
                color = Color.White,
                fontSize = 16.sp,
                height = 40.dp
            ) {
                onRetryClicked()
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun EmptyViewPreview() {
    AppTheme {
        Box(Modifier.background(MaterialTheme.colors.surface)) {
            EmptyView()
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun EmptyView(
    modifier: Modifier = Modifier,
    loadState: CombinedLoadStates,
    itemCount: Int
) {
    Timber.e("itemCount: $itemCount")

    if (loadState.refresh is LoadState.NotLoading) {
        Timber.e("loadState.refresh is LoadState.NotLoading")
    }
    if (loadState.prepend is LoadState.NotLoading) {
        Timber.e("loadState.prepend is LoadState.NotLoading")
    }
    if (loadState.prepend.endOfPaginationReached) {
        Timber.e("loadState.prepend.endOfPaginationReached")
    }

    EmptyView(
        modifier = modifier,
        show = loadState.refresh is LoadState.NotLoading
                && loadState.prepend is LoadState.NotLoading
//                && loadState.prepend.endOfPaginationReached
                && itemCount < 1
    )
}

@ExperimentalAnimationApi
@Composable
fun EmptyView(
    modifier: Modifier = Modifier,
    show: Boolean = true,
) {
    AnimatedVisibility(
        visible = show,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            EmptyIcon()

        }
    }
}

@Composable
fun EmptyIcon() {
    Column(
        Modifier
            .clip(RoundedCornerShape(32.dp, 32.dp, 32.dp, 32.dp))
            .background(Color.Black.copy(alpha = .02f))
            .padding(start = 32.dp, end = 32.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            modifier = Modifier
                .width(128.dp),
            painter = painterResource(id = R.drawable.ic_spider),
            contentDescription = "Empty",
            alpha = .2f
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = "Nothing found!",
            fontSize = 18.sp,
            color = Color.Black.copy(alpha = .7f)
        )
    }
}