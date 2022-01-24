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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Util
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

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
            .navigationBarsWithImePadding()
            .statusBarsPadding()
    ) {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            AppComponent.Header("ExoPlayer")

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

            // ----------------------------------------------------------------

            // Content here...
            // Use `padding(start = 16.dp, end = 16.dp)` for the elements.

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
    val focusIndex by derivedStateOf { lazyListState.firstVisibleItemIndex }

    LazyColumn(
        modifier = modifier,
        state = lazyListState
    ) {
        itemsIndexed(videos) { index, video ->
            VideoItem(video, index == focusIndex)
        }
    }
}

@Composable
fun VideoItem(video: Video, focusedVideo: Boolean) {
    Surface(color = if (focusedVideo) Color.Magenta else MaterialTheme.colors.surface) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = video.description, style = MaterialTheme.typography.h6)
            Text(text = video.subtitle, style = MaterialTheme.typography.body1)

            val context = LocalContext.current
            val exoPlayer = remember { SimpleExoPlayerHolder.get(context) }
            var playerView: PlayerView? = null

            if (focusedVideo) {
                LaunchedEffect(video.url) {
                    val videoUri = Uri.parse(video.url)
                    val dataSourceFactory = DataSourceHolder.getCacheFactory(context)
                    val type = Util.inferContentType(videoUri)
                    val source = when (type) {
                        C.TYPE_DASH -> DashMediaSource.Factory(dataSourceFactory)
                            .createMediaSource(MediaItem.fromUri(videoUri))
                        C.TYPE_HLS -> HlsMediaSource.Factory(dataSourceFactory)
                            .createMediaSource(MediaItem.fromUri(videoUri))
                        else -> ProgressiveMediaSource.Factory(dataSourceFactory)
                            .createMediaSource(MediaItem.fromUri(videoUri))
                    }
                    exoPlayer.setMediaSource(source)
                    exoPlayer.prepare()
                }
            }

            AndroidView(
                modifier = Modifier.aspectRatio(video.width.toFloat() / video.height.toFloat()),
                factory = { context ->
                    val frameLayout = FrameLayout(context)
                    frameLayout.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            android.R.color.holo_blue_bright
                        )
                    )
                    frameLayout
                },
                update = { frameLayout ->
                    frameLayout.removeAllViews()
                    if (focusedVideo) {
                        playerView = PlayerViewPool.get(frameLayout.context)
                        PlayerView.switchTargetView(
                            exoPlayer,
                            PlayerViewPool.currentPlayerView,
                            playerView
                        )
                        PlayerViewPool.currentPlayerView = playerView
                        playerView!!.apply {
                            player!!.playWhenReady = true
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

            DisposableEffect(key1 = video.url) {
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
    }
}