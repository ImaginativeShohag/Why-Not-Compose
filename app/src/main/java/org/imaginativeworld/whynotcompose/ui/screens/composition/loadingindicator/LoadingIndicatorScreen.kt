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

package org.imaginativeworld.whynotcompose.ui.screens.composition.loadingindicator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

@Composable
fun LoadingIndicatorScreen(
    goBack: () -> Unit
) {
    LoadingIndicatorScreenSkeleton(
        goBack = goBack
    )
}

@PreviewLightDark
@Composable
private fun LoadingIndicatorScreenSkeletonPreview() {
    AppTheme {
        LoadingIndicatorScreenSkeleton()
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun LoadingIndicatorScreenSkeleton(
    goBack: () -> Unit = {}
) {
    var showLoading by remember { mutableStateOf(false) }

    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            AppComponent.Header(
                "Loading Indicator",
                goBack = goBack
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                Modifier.padding(start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppComponent.SubHeader("Linear Progress Indicator")

                // ----------------------------------------------------------------

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LinearProgressIndicator()
                }

                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()

                var progress1 by remember { mutableFloatStateOf(0.1f) }
                val animatedProgress1 by animateFloatAsState(
                    targetValue = progress1,
                    animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LinearProgressIndicator(
                        progress = { animatedProgress1 }
                    )

                    Spacer(Modifier.requiredHeight(32.dp))

                    OutlinedButton(
                        onClick = {
                            if (progress1 < 1f) progress1 += 0.1f
                        }
                    ) {
                        Text("Increase")
                    }
                }

                // ----------------------------------------------------------------
                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()
            }

            HorizontalDivider()

            Column(
                Modifier.padding(start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppComponent.SubHeader("Rounded Linear Progress Indicator")

                // ----------------------------------------------------------------

                RoundedLinearProgressIndicator()

                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()

                var progress3 by remember { mutableFloatStateOf(0.1f) }
                val animatedProgress3 by animateFloatAsState(
                    targetValue = progress3,
                    animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RoundedLinearProgressIndicator(progress = animatedProgress3)

                    Spacer(Modifier.requiredHeight(32.dp))

                    OutlinedButton(
                        onClick = {
                            if (progress3 < 1f) progress3 += 0.1f
                        }
                    ) {
                        Text("Increase")
                    }
                }

                // ----------------------------------------------------------------
                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()
            }

            HorizontalDivider()

            Column(
                Modifier.padding(start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppComponent.SubHeader("Circular Progress Indicator")

                // ----------------------------------------------------------------

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }

                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()

                var progress2 by remember { mutableFloatStateOf(0.1f) }
                val animatedProgress2 by animateFloatAsState(
                    targetValue = progress2,
                    animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        progress = { animatedProgress2 }
                    )

                    Spacer(Modifier.requiredHeight(32.dp))

                    OutlinedButton(
                        onClick = {
                            if (progress2 < 1f) progress2 += 0.1f
                        }
                    ) {
                        Text("Increase")
                    }
                }

                // ----------------------------------------------------------------
                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()
            }

            HorizontalDivider()

            Column(
                Modifier.padding(start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppComponent.SubHeader("Capsule Loading Indicator")

                // ----------------------------------------------------------------

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CapsuleLoadingIndicator(
                        show = true
                    )
                }

                // ----------------------------------------------------------------
                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()
            }

            HorizontalDivider()

            Column(
                Modifier.padding(start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppComponent.SubHeader("Loading Indicator")

                // ----------------------------------------------------------------

                AppComponent.MediumSpacer()

                // Auto hide the loading.
                LaunchedEffect(showLoading) {
                    if (showLoading) {
                        delay(3000)

                        showLoading = false
                    }
                }

                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        showLoading = true
                    }
                ) {
                    Text("Show Loading")
                }
            }

            // ----------------------------------------------------------------

            AppComponent.BigSpacer()
        }

        FullscreenLoadingIndicator(
            show = showLoading
        )
    }
}

@Preview
@Composable
private fun FullscreenLoadingIndicatorPreview() {
    AppTheme {
        FullscreenLoadingIndicator(
            show = true
        )
    }
}

@Preview
@Composable
private fun FullscreenLoadingIndicatorPreviewDark() {
    AppTheme(
        darkTheme = true
    ) {
        FullscreenLoadingIndicator(
            show = true
        )
    }
}

@Composable
fun FullscreenLoadingIndicator(
    modifier: Modifier = Modifier,
    show: Boolean = true
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = show,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    // This will block all touch events.
                    // So, no click will work through the loading.
                }
                .fillMaxSize()
                .background(Color.Black.copy(alpha = .6f))
        ) {
            Card(
                Modifier
                    .size(200.dp, 180.dp)
                    .align(Alignment.Center),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                )
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        Modifier.size(76.dp),
                        strokeWidth = 8.dp
                    )

                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = "Loading...",
                        fontSize = 26.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun RoundedLinearProgressIndicator(
    /*@FloatRange(from = 0.0, to = 1.0)*/
    progress: Float,
    modifier: Modifier = Modifier,
    height: Dp = 8.dp,
    color: Color = ProgressIndicatorDefaults.linearColor,
    backgroundColor: Color = ProgressIndicatorDefaults.linearTrackColor
) {
    val infiniteTransition = rememberInfiniteTransition()
    val animatedColor by infiniteTransition.animateColor(
        initialValue = color,
        targetValue = color.copy(alpha = .75f),
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    Box(
        modifier
            .progressSemantics(progress)
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(height)
                .padding(start = 32.dp, end = 32.dp)
                .background(backgroundColor, CircleShape)
        ) {
            BoxWithConstraints {
                Box(
                    Modifier
                        .fillMaxHeight()
                        .clip(CircleShape)
                        .animateContentSize()
                        .width(maxWidth * animatedProgress)
                        .background(animatedColor, CircleShape)
                )
            }
        }
    }
}

@Composable
private fun RoundedLinearProgressIndicator(
    modifier: Modifier = Modifier,
    height: Dp = 8.dp,
    color: Color = ProgressIndicatorDefaults.linearColor,
    backgroundColor: Color = ProgressIndicatorDefaults.linearTrackColor
) {
    val infiniteTransition = rememberInfiniteTransition()
    val animatedColor by infiniteTransition.animateColor(
        initialValue = color,
        targetValue = color.copy(alpha = .5f),
        animationSpec = infiniteRepeatable(
            animation = tween(768, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val animTranslationX by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = .75f,
        animationSpec = infiniteRepeatable(
            animation = tween(1024, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(height)
                .padding(start = 32.dp, end = 32.dp)
                .background(backgroundColor, CircleShape)
        ) {
            BoxWithConstraints {
                Box(
                    Modifier
                        .graphicsLayer {
                            translationX = (maxWidth * animTranslationX).toPx()
                        }
                        .fillMaxHeight()
                        .clip(CircleShape)
                        .width(maxWidth * .25f)
                        .background(animatedColor, CircleShape)
                )
            }
        }
    }
}

@Composable
private fun CapsuleLoadingIndicator(
    show: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = show,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Row(
            Modifier
                .shadow(2.dp, CircleShape)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(0.dp, 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .size(16.dp),
                strokeWidth = 2.dp
            )
            Text(
                modifier = Modifier.padding(end = 12.dp),
                text = "Loading...",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
