/*
 * Copyright 2025 Md. Mahmudul Hasan Shohag
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

package org.imaginativeworld.whynotcompose.ui.screens.home.index.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.OpenInNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.base.extensions.longToast
import org.imaginativeworld.whynotcompose.base.extensions.openUrl
import org.imaginativeworld.whynotcompose.base.extensions.openUrlElseToast
import org.imaginativeworld.whynotcompose.base.extensions.shadow
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.common.compose.theme.AppleSystemColor

@Composable
fun BookKeeperButton(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Button(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                spread = 8.dp,
                alpha = .25f,
                color = AppleSystemColor.Pink,
                radius = 8.dp
            ),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = AppleSystemColor.Pink
        ),
        onClick = {
            val appPackageName = "org.imaginativeworld.bookkeeper"

            // Try to open the app if installed.
            val launchIntent = context.packageManager.getLaunchIntentForPackage(appPackageName)
            if (launchIntent != null) {
                try {
                    context.startActivity(launchIntent)
                } catch (_: Exception) {
                    context.longToast("Cannot open the app!")
                }
            } else {
                // Else open store to download the app.
                try {
                    context.openUrl("market://details?id=$appPackageName")
                } catch (_: Exception) {
                    context.openUrlElseToast("https://play.google.com/store/apps/details?id=$appPackageName")
                }
            }
        },
        contentPadding = PaddingValues(8.dp),
        elevation = null
    ) {
        val image = ImageBitmap.imageResource(R.drawable.pattern_memphis)
        val brush = remember(image) {
            ShaderBrush(
                ImageShader(
                    image,
                    TileMode.Repeated,
                    TileMode.Repeated
                )
            )
        }

        Box(
            Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Box(
                Modifier
                    .alpha(0.05f)
                    .background(brush)
                    .fillMaxSize()
            )

            Image(
                Icons.AutoMirrored.Rounded.OpenInNew,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.TopEnd),
                colorFilter = ColorFilter.tint(AppleSystemColor.Gray)
            )

            Row(
                Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier.size(80.dp),
                    painter = painterResource(id = R.drawable.ic_book_keeper),
                    contentDescription = "Book Keeper Icon"
                )

                Spacer(Modifier.width(8.dp))

                Column(Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.padding(end = 24.dp),
                        text = "Book Keeper",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    AppleSystemColor.Purple,
                                    AppleSystemColor.Pink
                                )
                            )
                        ),
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )

                    Text(
                        text = "The Perfect Book Companion.",
                        color = MaterialTheme.colorScheme.onBackground.copy(.75f),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun BookKeeperButtonPreview() {
    AppTheme {
        BookKeeperButton()
    }
}
