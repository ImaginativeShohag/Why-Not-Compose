/*
 * Copyright 2022 Md. Mahmudul Hasan Shohag
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

package org.imaginativeworld.whynotcompose.tictactoe

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.imaginativeworld.whynotcompose.base.extensions.toast
import org.imaginativeworld.whynotcompose.base.models.Event
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

@Composable
fun TicTacToeScreen(
    viewModel: TicTacToeViewModel,
    goBack: () -> Unit
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.toast) {
        state.toast?.getValueOnce()?.let { message ->
            context.toast(message)
        }
    }

    TicTacToeScreenSkeleton(
        loading = state.loading,
        message = state.message,
        paused = state.paused,
        userWinCount = state.userWinCount,
        aiWinCount = state.aiWinCount,
        currentPlayingMoves = state.currentPlayingMoves,
        totalNeurons = state.totalNeurons,
        winPosition = state.winPosition,
        goBack = goBack,
        onBoxClick = { position ->
            viewModel.act(
                position = position
            )
        },
        onRestartClick = {
            viewModel.restart()
        }
    )
}

@PreviewLightDark
@Composable
private fun TicTacToeScreenSkeletonPreviewDark() {
    AppTheme {
        TicTacToeScreenSkeleton(
            currentPlayingMoves = "1O3O5X7X9O2X4X6X8O"
        )
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun TicTacToeScreenSkeleton(
    loading: Boolean = false,
    message: Event<String>? = null,
    paused: Boolean = false,
    userWinCount: Int = 0,
    aiWinCount: Int = 0,
    currentPlayingMoves: String = "",
    totalNeurons: Int = 0,
    winPosition: WinPosition? = null,
    goBack: () -> Unit = {},
    onBoxClick: (position: Int) -> Unit = {},
    onRestartClick: () -> Unit = {}
) {
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(message, onRestartClick) {
        message?.getValueOnce()?.let { value ->
            val result = snackbarHostState.showSnackbar(
                message = value,
                actionLabel = "Play Again",
                duration = SnackbarDuration.Indefinite
            )

            if (result == SnackbarResult.ActionPerformed) {
                onRestartClick()
            }
        }
    }

    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            AppComponent.Header(
                "Tic-Tac-Toe",
                goBack = goBack
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        Column(
            Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            AppComponent.MediumSpacer()

            Row(Modifier.padding(start = 16.dp, end = 16.dp)) {
                Column(
                    Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "You",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "$userWinCount",
                        fontSize = 32.sp
                    )
                }

                Column(
                    Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "AI",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "$aiWinCount",
                        fontSize = 32.sp
                    )
                }
            }

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.padding(start = 32.dp, end = 32.dp)
            ) {
                items((1..9).toList()) { i ->
                    Block(
                        position = i,
                        currentPlayingMoves = currentPlayingMoves,
                        enabled = !paused,
                        isMarked = winPosition != null && i in winPosition.places,
                        onClick = {
                            onBoxClick(i)
                        }
                    )
                }
            }

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            Text(
                modifier = Modifier
                    .padding(start = 32.dp, end = 32.dp)
                    .fillMaxWidth(),
                text = buildAnnotatedString {
                    append("Total Neurons: ")

                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("$totalNeurons")
                    }
                },
                textAlign = TextAlign.Center
            )

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.BigSpacer()
        }

        AnimatedVisibility(
            visible = loading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.onBackground.copy(.5f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Working...",
                    color = MaterialTheme.colorScheme.background
                )
            }
        }
    }
}

@Composable
private fun Block(
    position: Int,
    currentPlayingMoves: String,
    enabled: Boolean,
    isMarked: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    var currentPiece by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(currentPlayingMoves) {
        currentPiece = getPiece(position, currentPlayingMoves)
    }

    val backgroundColor by animateColorAsState(
        targetValue = if (isMarked) {
            MaterialTheme.colorScheme.error
        } else {
            MaterialTheme.colorScheme.background
        },
        label = "background color"
    )

    val iconColor by animateColorAsState(
        targetValue = if (isMarked) {
            MaterialTheme.colorScheme.onError
        } else {
            MaterialTheme.colorScheme.onBackground
        },
        label = "icon color"
    )

    Box(
        modifier
            .padding(4.dp)
            .clip(CircleShape)
            .fillMaxWidth()
            .aspectRatio(1f)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(.15f),
                shape = CircleShape
            )
            .background(
                color = backgroundColor,
                shape = CircleShape
            )
            .clickable(
                indication = if (currentPiece == null) LocalIndication.current else null,
                interactionSource = remember { MutableInteractionSource() },
                enabled = enabled
            ) {
                if (currentPiece == null) {
                    onClick()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = currentPiece,
            label = "block icon"
        ) { targetCurrentPiece ->
            Image(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                painter = painterResource(
                    id = when (targetCurrentPiece) {
                        Piece.X.value -> R.drawable.ic_x
                        Piece.O.value -> R.drawable.ic_o
                        else -> R.drawable.ic_blank
                    }
                ),
                contentDescription = targetCurrentPiece,
                colorFilter = ColorFilter.tint(iconColor)
            )
        }
    }
}

/**
 * Example of [currentPlayingMoves]:
 *
 * 1O3O5X7X9O2X4X6X8O
 */
private fun getPiece(position: Int, currentPlayingMoves: String): String? {
    val targetPosition = currentPlayingMoves.indexOf(position.toString())

    return if (targetPosition == -1) {
        null
    } else {
        currentPlayingMoves.getOrNull(targetPosition + 1)?.toString()
    }
}
