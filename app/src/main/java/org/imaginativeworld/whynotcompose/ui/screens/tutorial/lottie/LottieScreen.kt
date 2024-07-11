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

package org.imaginativeworld.whynotcompose.ui.screens.tutorial.lottie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.resetToBeginning
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

@Composable
fun LottieScreen(
    goBack: () -> Unit
) {
    LottieScreenSkeleton(
        goBack = goBack
    )
}

@PreviewLightDark
@Composable
private fun LottieScreenSkeletonPreviewDark() {
    AppTheme {
        LottieScreenSkeleton()
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun LottieScreenSkeleton(
    goBack: () -> Unit = {}
) {
    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            AppComponent.Header(
                "Lottie",
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
            Box(
                Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                )
            ) {
                Card(
                    Modifier
                        .fillMaxWidth()
                        .height(256.dp)
                ) {
                    Column(Modifier.fillMaxSize()) {
                        val composition by rememberLottieComposition(
                            LottieCompositionSpec.RawRes(R.raw.hubit_bicycle)
                        )

                        LottieAnimation(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            composition = composition,
                            iterations = LottieConstants.IterateForever
                        )

                        // ----------------------------------------------------------------

                        HorizontalDivider()

                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                        )
                    }
                }

                Row(
                    Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .weight(1f),
                        text = "Let's Ride a Bike",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(.75f)
                    )

                    Box(Modifier.size(48.dp)) {
                        val scope = rememberCoroutineScope()
                        var isFavorite by remember { mutableStateOf(false) }
                        val compositionHeart by rememberLottieComposition(
                            LottieCompositionSpec.RawRes(
                                R.raw.heart
                            )
                        )
                        val heartAnimatable = rememberLottieAnimatable()

                        LottieAnimation(
                            modifier = Modifier
                                .requiredSize(96.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = {
                                        compositionHeart ?: return@clickable

                                        scope.launch {
                                            if (isFavorite) {
                                                heartAnimatable.resetToBeginning()
                                            } else {
                                                heartAnimatable.animate(
                                                    compositionHeart,
                                                    continueFromPreviousAnimate = false
                                                )
                                            }

                                            isFavorite = !isFavorite
                                        }
                                    }
                                ),
                            composition = compositionHeart,
                            progress = { heartAnimatable.progress }
                        )
                    }
                }
            }

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.BigSpacer()
        }
    }
}
