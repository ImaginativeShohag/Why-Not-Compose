package org.imaginativeworld.whynotcompose.ui.screens.composition.loadingindicator

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.delay
import org.imaginativeworld.whynotcompose.ui.screens.AppComponent
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme

@Composable
fun LoadingIndicatorScreen() {
    LoadingIndicatorScreenSkeleton()
}

@Preview
@Composable
fun LoadingIndicatorScreenSkeletonPreview() {
    AppTheme {
        LoadingIndicatorScreenSkeleton()
    }
}

@Composable
fun LoadingIndicatorScreenSkeleton() {

    var showLoading by remember { mutableStateOf(false) }

    Scaffold(
        Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 16.dp, end = 16.dp)
        ) {
            AppComponent.Header("Loading Indicator")

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

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

            var progress1 by remember { mutableStateOf(0.1f) }
            val animatedProgress1 by animateFloatAsState(
                targetValue = progress1,
                animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LinearProgressIndicator(progress = animatedProgress1)

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

            Divider()

            AppComponent.SubHeader("Rounded Linear Progress Indicator")

            // ----------------------------------------------------------------

            RoundedLinearProgressIndicator()

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            var progress3 by remember { mutableStateOf(0.1f) }
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

            Divider()

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

            var progress2 by remember { mutableStateOf(0.1f) }
            val animatedProgress2 by animateFloatAsState(
                targetValue = progress2,
                animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(progress = animatedProgress2)

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

            Divider()

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
                }) {
                Text("Show Loading")
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
fun LoadingIndicatorPreview() {
    AppTheme {
        FullscreenLoadingIndicator(
            show = true
        )
    }
}

@Composable
fun FullscreenLoadingIndicator(
    show: Boolean = true
) {
    AnimatedVisibility(
        visible = show,
        enter = fadeIn(),
        exit = fadeOut()
    ) {

        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = .6f))
        ) {
            Card(
                Modifier
                    .size(200.dp, 180.dp)
                    .align(Alignment.Center),
                shape = RoundedCornerShape(8.dp),
                elevation = 8.dp
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
    color: Color = MaterialTheme.colors.primary,
    backgroundColor: Color = color.copy(alpha = ProgressIndicatorDefaults.IndicatorBackgroundOpacity)
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
    color: Color = MaterialTheme.colors.primary,
    backgroundColor: Color = color.copy(alpha = ProgressIndicatorDefaults.IndicatorBackgroundOpacity)
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