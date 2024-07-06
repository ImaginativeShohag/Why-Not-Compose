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

package org.imaginativeworld.whynotcompose.ui.screens.tutorial.datafetchandpaging.elements

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import org.imaginativeworld.whynotcompose.base.models.github.GithubRepo
import org.imaginativeworld.whynotcompose.common.compose.composeutils.rememberImagePainter
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.repositories.MockData

@Composable
fun GithubRepoItem(
    modifier: Modifier,
    item: GithubRepo,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = item.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Row(
                Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onSurface.copy(.15f)),
                    painter = rememberImagePainter(item.owner.avatarUrl),
                    contentDescription = "User image"
                )
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .fillMaxWidth(),
                    text = item.fullName,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (item.description != null) {
                Text(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth(),
                    text = item.description ?: "",
                    fontSize = 14.sp
                )
            }

            Row(
                Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "⭐ ${item.stargazersCount}",
                    textAlign = TextAlign.Center
                )

                Text(
                    modifier = Modifier.weight(1f),
                    text = "\uD83D\uDD76 ${item.watchersCount}",
                    textAlign = TextAlign.Center
                )

                Text(
                    modifier = Modifier.weight(1f),
                    text = "\uD83C\uDF74 ${item.forksCount}",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GithubRepoItemPreview() {
    AppTheme {
        Column {
            repeat(3) {
                GithubRepoItem(
                    modifier = Modifier.padding(
                        start = 12.dp,
                        top = 4.dp,
                        end = 12.dp,
                        bottom = 4.dp
                    ),
                    item = if (it % 2 == 0) {
                        MockData.dummyGithubRepo
                    } else {
                        MockData.dummyGithubRepo.copy(
                            description = null
                        )
                    },
                    onClick = {}
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GithubRepoItemPreviewDark() {
    AppTheme {
        Column {
            repeat(3) {
                GithubRepoItem(
                    modifier = Modifier.padding(
                        start = 12.dp,
                        top = 4.dp,
                        end = 12.dp,
                        bottom = 4.dp
                    ),
                    item = if (it % 2 == 0) {
                        MockData.dummyGithubRepo
                    } else {
                        MockData.dummyGithubRepo.copy(
                            description = null
                        )
                    },
                    onClick = {}
                )
            }
        }
    }
}

// ================================================================

@Preview(showBackground = true)
@Composable
fun LoadingGithubRepoItemPreview() {
    AppTheme {
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            repeat(3) {
                LoadingGithubRepoItem(
                    Modifier
                        .alpha(1f)
                        .padding(start = 12.dp, top = 4.dp, end = 12.dp, bottom = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoadingGithubRepoItemPreviewDark() {
    AppTheme {
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            repeat(3) {
                LoadingGithubRepoItem(
                    Modifier
                        .alpha(1f)
                        .padding(start = 12.dp, top = 4.dp, end = 12.dp, bottom = 4.dp)
                )
            }
        }
    }
}

@Composable
fun LoadingGithubRepoItem(
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
