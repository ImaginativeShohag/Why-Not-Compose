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

package org.imaginativeworld.whynotcompose.tictactoe

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

@Composable
fun TicTacToeScreen(
    viewModel: TicTacToeViewModel
) {

    val currentPlayingMoves by viewModel.currentPlayingMoves.collectAsState()
    val userWinCount by viewModel.userWinCount.collectAsState()
    val aiWinCount by viewModel.aiWinCount.collectAsState()

    TicTacToeScreenSkeleton(
        userWinCount = userWinCount,
        aiWinCount = aiWinCount,
        currentPlayingMoves = currentPlayingMoves,
        onBoxClicked = { position ->
            viewModel.act(
                position = position,
            )
        }
    )
}

@Preview
@Composable
fun TicTacToeScreenSkeletonPreview() {
    AppTheme {
        TicTacToeScreenSkeleton(
            currentPlayingMoves = "1⭕3⭕5❌7❌9⭕2❌4❌6❌8⭕"
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TicTacToeScreenSkeletonPreviewDark() {
    AppTheme {
        TicTacToeScreenSkeleton(
            currentPlayingMoves = "1⭕3⭕5❌7❌9⭕2❌4❌6❌8⭕"
        )
    }
}

@Composable
fun TicTacToeScreenSkeleton(
    userWinCount: Int = 0,
    aiWinCount: Int = 0,
    currentPlayingMoves: String = "",
    onBoxClicked: (position: Int) -> Unit = { }
) {
    Scaffold(
        Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AppComponent.Header("Tic Tac Toe")

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            Row(Modifier.padding(start = 16.dp, end = 16.dp)) {
                Column(
                    Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "You",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "$userWinCount",
                        fontSize = 32.sp,
                    )
                }

                Column(
                    Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "AI",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "$aiWinCount",
                        fontSize = 32.sp,
                    )
                }
            }

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            Column(
                Modifier
                    .padding(start = 32.dp, end = 32.dp)
                    .fillMaxWidth()
            ) {
                Row(Modifier.fillMaxWidth()) {
                    Block(
                        position = 1,
                        currentPlayingMoves = currentPlayingMoves,
                        onClick = {
                            onBoxClicked(1)
                        }
                    )
                    Block(
                        position = 2,
                        currentPlayingMoves = currentPlayingMoves,
                        onClick = {
                            onBoxClicked(2)
                        }
                    )
                    Block(
                        position = 3,
                        currentPlayingMoves = currentPlayingMoves,
                        onClick = {
                            onBoxClicked(3)
                        }
                    )
                }
                Row(Modifier.fillMaxWidth()) {
                    Block(
                        position = 4,
                        currentPlayingMoves = currentPlayingMoves,
                        onClick = {
                            onBoxClicked(4)
                        }
                    )
                    Block(
                        position = 5,
                        currentPlayingMoves = currentPlayingMoves,
                        onClick = {
                            onBoxClicked(5)
                        }
                    )
                    Block(
                        position = 6,
                        currentPlayingMoves = currentPlayingMoves,
                        onClick = {
                            onBoxClicked(6)
                        }
                    )
                }
                Row(Modifier.fillMaxWidth()) {
                    Block(
                        position = 7,
                        currentPlayingMoves = currentPlayingMoves,
                        onClick = {
                            onBoxClicked(7)
                        }
                    )
                    Block(
                        position = 8,
                        currentPlayingMoves = currentPlayingMoves,
                        onClick = {
                            onBoxClicked(8)
                        }
                    )
                    Block(
                        position = 9,
                        currentPlayingMoves = currentPlayingMoves,
                        onClick = {
                            onBoxClicked(9)
                        }
                    )
                }
            }

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.BigSpacer()
        }
    }
}

@Composable
fun RowScope.Block(
    position: Int,
    currentPlayingMoves: String,
    onClick: () -> Unit = {},
) {
    var currentPiece by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(currentPlayingMoves) {
        currentPiece = getPiece(position, currentPlayingMoves)
    }

    Box(
        Modifier
            .weight(1f)
            .aspectRatio(1f)
            .border(1.dp, MaterialTheme.colors.onBackground.copy(.15f))
            .clickable(
                indication = if (currentPiece == null) LocalIndication.current else null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                if (currentPiece == null)
                    onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = currentPiece ?: "",
            fontSize = 32.sp,
        )
    }
}

/**
 * Example [currentPlayingMoves]:
 *
 * 1⭕3⭕5❌7❌9⭕2❌4❌6❌8⭕
 */
private fun getPiece(position: Int, currentPlayingMoves: String): String? {
    val targetPosition = currentPlayingMoves.indexOf(position.toString())

    return if (targetPosition == -1) {
        null
    } else {
        currentPlayingMoves.getOrNull(targetPosition + 1)?.toString()
    }
}