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

package org.imaginativeworld.whynotcompose.ui.screens.home.splash

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.ui.screens.animation.composeone.dotBackground
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme
import kotlin.math.min
import kotlin.random.Random

@Composable
fun SplashScreen(
    gotoHomeIndex: () -> Unit = {},
) {
    val density = LocalDensity.current
    val dotBackground = MaterialTheme.colors.dotBackground

    LaunchedEffect(Unit) {
        delay(3000)

        gotoHomeIndex()
    }

    Scaffold {

        BoxWithConstraints(Modifier.fillMaxSize()) {
            with(density) {
                val maxWidth = maxWidth
                val maxHeight = maxHeight

                for (i in 0..50) {
                    var state by remember { mutableStateOf(false) }

                    LaunchedEffect(Unit) {
                        delay(Random.nextLong(300, 5000))
                        state = true
                    }

                    val animScale by animateFloatAsState(
                        targetValue = if (state) 1f else .75f,
                        animationSpec = tween(
                            durationMillis = 12000,
                            easing = LinearEasing,
                        )
                    )

                    val animCenterX by animateFloatAsState(
                        targetValue = if (state) .8f else 1f,
                        animationSpec = tween(
                            durationMillis = 9000,
                            easing = FastOutSlowInEasing,
                        )
                    )

                    val animCenterY by animateFloatAsState(
                        targetValue = if (state) .8f else 1f,
                        animationSpec = tween(
                            durationMillis = 9000,
                            easing = FastOutSlowInEasing,
                        )
                    )

                    val centerX = remember {
                        Random.nextInt(0, maxWidth.toPx().toInt()).toFloat()
                    }
                    val centerY = remember {
                        Random.nextInt(0, maxHeight.toPx().toInt()).toFloat()
                    }
                    val radius = remember {
                        Random.nextInt(16, min(maxWidth.toPx(), minHeight.toPx()).toInt() / 14)
                            .toFloat()
                    }
                    val alpha = remember { (Random.nextInt(10, 85) / 100f) }

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawCircle(
                            color = dotBackground,
                            center = Offset(
                                x = if (i % 2 != 0) centerX * animCenterX else centerX,
                                y = if (i % 2 == 0) centerY * animCenterY else centerY
                            ),
                            radius = radius * animScale,
                            alpha = alpha
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            var state by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                val startDelay = Random.nextLong(300, 900)
                delay(startDelay)
                state = true
            }

            val animAlpha by animateFloatAsState(
                targetValue = if (state) 1f else 0f,
                animationSpec = tween(
                    durationMillis = 900,
                    easing = FastOutSlowInEasing,
                )
            )

            val animRotation by animateFloatAsState(
                targetValue = if (state) 360f else 0f,
                animationSpec = tween(
                    durationMillis = 900,
                    easing = FastOutSlowInEasing,
                )
            )

            val animScale by animateFloatAsState(
                targetValue = if (state) 1f else 0f,
                animationSpec = tween(
                    durationMillis = 900,
                    easing = FastOutSlowInEasing,
                )
            )

            Image(
                modifier = Modifier
                    .size(128.dp)
                    .graphicsLayer {
                        alpha = animAlpha
                        rotationX = animRotation
                        rotationY = animRotation
                        rotationZ = animRotation
                        scaleX = animScale
                        scaleY = animScale
                    },
                painter = painterResource(id = R.drawable.ic_jetpack_compose_logo),
                contentDescription = stringResource(id = R.string.app_name)
            )

            AnimatedText(
                text = stringResource(id = R.string.app_name),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            )
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    AppTheme {
        SplashScreen()
    }
}

@Composable
fun AnimatedText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        text.forEach { char ->

            var state by remember { mutableStateOf(false) }

            LaunchedEffect(char) {
                val startDelay = Random.nextLong(300, 900)
                delay(startDelay)
                state = true
            }

            val animAlpha by animateFloatAsState(
                targetValue = if (state) 1f else 0f,
                animationSpec = tween(
                    durationMillis = 900,
                    easing = FastOutSlowInEasing,
                )
            )

            Text(
                text = char.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.graphicsLayer {
                    alpha = animAlpha
                }
            )
        }
    }
}
