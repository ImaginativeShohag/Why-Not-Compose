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

package org.imaginativeworld.whynotcompose.ui.screens.tutorial.counter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

@Composable
fun CounterScreen() {
    CounterScreenSkeleton()
}

@Preview
@Composable
fun CounterScreenSkeletonPreview() {
    AppTheme {
        CounterScreenSkeleton()
    }
}

@Preview
@Composable
fun CounterScreenSkeletonPreviewDark() {
    AppTheme(darkTheme = true) {
        CounterScreenSkeleton()
    }
}

@Composable
fun CounterScreenSkeleton() {
    Scaffold(
        Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding()
    ) {
        Column(Modifier.fillMaxSize()) {
            AppComponent.Header("Counter")

            Divider()

            Column(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {

                val counter = remember { mutableStateOf(0) }

                Text(
                    modifier = Modifier.padding(top = 32.dp),
                    text = "${counter.value}",
                    fontSize = 64.sp,
                )

                Row(
                    Modifier
                        .padding(top = 32.dp)
                        .fillMaxWidth()
                ) {
                    Button(
                        modifier = Modifier
                            .weight(1f),
                        onClick = { counter.value += 1 }
                    ) {
                        Text(
                            text = "Increase",
                        )
                    }

                    Spacer(Modifier.width(16.dp))

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { counter.value -= 1 }
                    ) {
                        Text(
                            text = "Decrease",
                        )
                    }
                }
            }
        }
    }
}
