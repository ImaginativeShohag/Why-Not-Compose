/*
 * Copyright 2023 Md. Mahmudul Hasan Shohag
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

package org.imaginativeworld.whynotcompose.cms.ui.compositions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme

@Composable
fun LoadingItem(
    modifier: Modifier = Modifier
) {
    val onBackgroundColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.15f)

    Column(
        modifier
            .padding()
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(8.dp)
    ) {
        Box(
            Modifier
                .clip(CircleShape)
                .fillMaxWidth()
                .height(16.dp)
                .placeholder(
                    visible = true,
                    color = onBackgroundColor,
                    highlight = PlaceholderHighlight.shimmer()
                )
        )

        Row(
            Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .placeholder(
                        visible = true,
                        color = onBackgroundColor,
                        highlight = PlaceholderHighlight.shimmer()
                    )
            )

            Box(
                Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = 64.dp)
                    .clip(CircleShape)
                    .height(10.dp)
                    .placeholder(
                        visible = true,
                        color = onBackgroundColor,
                        highlight = PlaceholderHighlight.shimmer()
                    )
            )
        }

        Box(
            Modifier
                .padding(top = 8.dp, end = 32.dp)
                .clip(CircleShape)
                .fillMaxWidth()
                .height(10.dp)
                .placeholder(
                    visible = true,
                    color = onBackgroundColor,
                    highlight = PlaceholderHighlight.shimmer()
                )
        )

        Box(
            Modifier
                .padding(top = 4.dp)
                .clip(CircleShape)
                .fillMaxWidth()
                .height(10.dp)
                .placeholder(
                    visible = true,
                    color = onBackgroundColor,
                    highlight = PlaceholderHighlight.shimmer()
                )
        )

        Box(
            Modifier
                .padding(top = 4.dp, end = 48.dp)
                .clip(CircleShape)
                .fillMaxWidth()
                .height(10.dp)
                .placeholder(
                    visible = true,
                    color = onBackgroundColor,
                    highlight = PlaceholderHighlight.shimmer()
                )
        )

        Row(
            Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        ) {
            repeat(3) {
                Box(
                    Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        Modifier
                            .width(64.dp)
                            .clip(CircleShape)
                            .fillMaxWidth()
                            .height(16.dp)
                            .placeholder(
                                visible = true,
                                color = onBackgroundColor,
                                highlight = PlaceholderHighlight.shimmer()
                            )
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun LoadingItemPreview() {
    CMSAppTheme {
        Surface(
            Modifier
                .fillMaxWidth()
        ) {
            Column {
                repeat(5) {
                    LoadingItem(
                        Modifier
                            .alpha(1f)
                            .padding(start = 12.dp, top = 4.dp, end = 12.dp, bottom = 4.dp)
                    )
                }
            }
        }
    }
}
