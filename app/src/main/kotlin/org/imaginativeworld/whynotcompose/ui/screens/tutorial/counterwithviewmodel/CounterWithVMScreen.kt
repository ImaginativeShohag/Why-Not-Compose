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

package org.imaginativeworld.whynotcompose.ui.screens.tutorial.counterwithviewmodel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

@Composable
fun CounterWithVMScreen(
    viewModel: CounterWithVMViewModel,
    goBack: () -> Unit
) {
    val counter = viewModel.counter.collectAsState()

    CounterWithVMScreenSkeleton(
        counter = counter.value,
        goBack = goBack,
        increase = {
            viewModel.increase()
        },
        decrease = {
            viewModel.decrease()
        }
    )
}

@PreviewLightDark
@Composable
private fun CounterWithVMScreenSkeletonPreview() {
    var counter by remember { mutableIntStateOf(0) }

    AppTheme {
        CounterWithVMScreenSkeleton(
            counter = counter,
            goBack = {},
            increase = {
                counter++
            },
            decrease = {
                counter--
            }
        )
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun CounterWithVMScreenSkeleton(
    counter: Int,
    goBack: () -> Unit,
    increase: () -> Unit,
    decrease: () -> Unit
) {
    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            AppComponent.Header(
                "Counter with ViewModel",
                goBack = goBack
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = "$counter",
                fontSize = 64.sp
            )

            Row(
                Modifier
                    .padding(top = 32.dp)
                    .fillMaxWidth()
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f),
                    onClick = { increase() }
                ) {
                    Text(
                        text = "Increase"
                    )
                }

                Spacer(Modifier.width(16.dp))

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { decrease() }
                ) {
                    Text(
                        text = "Decrease"
                    )
                }
            }
        }
    }
}
