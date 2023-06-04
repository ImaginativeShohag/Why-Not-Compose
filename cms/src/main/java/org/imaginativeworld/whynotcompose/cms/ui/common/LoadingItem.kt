package org.imaginativeworld.whynotcompose.cms.ui.common

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

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

@Preview(showBackground = true, wallpaper = Wallpapers.NONE)
@Composable
fun LoadingItemPreview() {
    CMSAppTheme {
        Surface(
            Modifier
                .fillMaxWidth()
        ) {
            Column {
                repeat(3) {
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

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoadingItemPreviewDark() {
    CMSAppTheme {
        Surface(
            Modifier
                .fillMaxWidth()
        ) {
            Column {
                repeat(3) {
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
