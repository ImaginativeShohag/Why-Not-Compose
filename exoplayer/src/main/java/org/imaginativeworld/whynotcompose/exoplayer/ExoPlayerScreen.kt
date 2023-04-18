/*
 * Copyright 2021 Md. Mahmudul Hasan Shohag
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

package org.imaginativeworld.whynotcompose.exoplayer

import android.content.res.Configuration
import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.util.Util
import org.imaginativeworld.whynotcompose.common.compose.composeutils.rememberImagePainter
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.common.compose.theme.TailwindCSSColor

@Composable
fun ExoPlayerScreen() {
    ExoPlayerScreenSkeleton()
}

@Preview
@Composable
fun ExoPlayerScreenSkeletonPreview() {
    AppTheme {
        ExoPlayerScreenSkeleton()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExoPlayerScreenSkeletonPreviewDark() {
    AppTheme {
        ExoPlayerScreenSkeleton()
    }
}

@Composable
fun ExoPlayerScreenSkeleton() {
    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding()
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            AppComponent.Header("ExoPlayer")

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

            // ----------------------------------------------------------------

            VideoList(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                videos = videos
            )
        }
    }
}

@Composable
private fun VideoList(
    modifier: Modifier,
    videos: List<Video>
) {
    val lazyListState = rememberLazyListState()
    // play the video on the first visible item in the list
    val focusIndex by remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }
    val focusIndexOffset by remember { derivedStateOf { lazyListState.firstVisibleItemScrollOffset } }

    val density = LocalDensity.current

    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        contentPadding = PaddingValues(top = 4.dp, bottom = 4.dp)
    ) {
        items(count = Int.MAX_VALUE) { index ->
            val videoIndex = index % videos.size

            VideoItem(
                video = videos[videoIndex],
                focusedVideo = (index == 0 && focusIndexOffset <= with(density) { 48.dp.toPx() }) ||
                    (index == focusIndex + 1 && focusIndexOffset > with(density) { 48.dp.toPx() })
            )
        }
    }
}

@Composable
fun VideoItem(video: Video, focusedVideo: Boolean) {
    val animateBackground by animateColorAsState(
        targetValue = if (focusedVideo) TailwindCSSColor.Red500 else MaterialTheme.colors.surface
    )

    Card(
        modifier = Modifier.padding(horizontal = 16.dp, 6.dp),
        elevation = 2.dp,
        backgroundColor = animateBackground
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .aspectRatio(video.width.toFloat() / video.height.toFloat())
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.onSurface.copy(.1f)),
                    painter = rememberImagePainter(data = video.thumb),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Player(
                    modifier = Modifier.fillMaxSize(),
                    video = video,
                    focusedVideo = focusedVideo
                )

                androidx.compose.animation.AnimatedVisibility(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 8.dp, end = 8.dp),
                    visible = focusedVideo,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Image(
                        modifier = Modifier
                            .size(32.dp),
                        painter = painterResource(R.drawable.ic_round_play_arrow),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(TailwindCSSColor.Red500)
                    )
                }
            }

            Text(
                modifier = Modifier.padding(start = 8.dp, top = 2.dp, end = 8.dp, bottom = 0.dp),
                text = video.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Text(
                modifier = Modifier.padding(start = 8.dp, top = 2.dp, end = 8.dp, bottom = 6.dp),
                text = video.subtitle,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = LocalContentColor.current.copy(.6f)
            )
        }
    }
}

@Composable
private fun Player(modifier: Modifier = Modifier, video: Video, focusedVideo: Boolean) {
    val context = LocalContext.current
    val exoPlayer = remember { SimpleExoPlayerHolder.get(context) }
    var playerView: StyledPlayerView? = null

    if (focusedVideo) {
        LaunchedEffect(video.url) {
            // Start playing current video.
            val videoUri = Uri.parse(video.url)
            val dataSourceFactory = DataSourceHolder.getCacheFactory(context)
            val type = Util.inferContentType(videoUri)
            val source = when (type) {
                C.CONTENT_TYPE_DASH -> DashMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(videoUri))

                C.CONTENT_TYPE_HLS -> HlsMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(videoUri))

                else -> ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(videoUri))
            }
            exoPlayer.setMediaSource(source)
            exoPlayer.prepare()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            val frameLayout = FrameLayout(ctx)
            frameLayout
        },
        update = { frameLayout ->
            frameLayout.removeAllViews()
            if (focusedVideo) {
                playerView = PlayerViewPool.get(frameLayout.context)
                StyledPlayerView.switchTargetView(
                    exoPlayer,
                    PlayerViewPool.currentPlayerView,
                    playerView
                )
                PlayerViewPool.currentPlayerView = playerView
                playerView?.apply {
                    player?.playWhenReady = true
                }

                playerView?.apply {
                    (parent as? ViewGroup)?.removeView(this)
                }
                frameLayout.addView(
                    playerView,
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
            } else if (playerView != null) {
                playerView?.apply {
                    (parent as? ViewGroup)?.removeView(this)
                    PlayerViewPool.release(this)
                }
                playerView = null
            }
        }
    )

    DisposableEffect(video.url) {
        onDispose {
            if (focusedVideo) {
                playerView?.apply {
                    (parent as? ViewGroup)?.removeView(this)
                }
                exoPlayer.stop()
                playerView?.let {
                    PlayerViewPool.release(it)
                }
                playerView = null
            }
        }
    }
}
