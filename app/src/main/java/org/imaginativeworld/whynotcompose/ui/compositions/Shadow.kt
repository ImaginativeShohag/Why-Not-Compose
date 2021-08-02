package org.imaginativeworld.whynotcompose.ui.compositions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme


@Preview
@Composable
fun TopBottomShadowPreview() {
    AppTheme {
        Column {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            )

            TopBottomShadow()
        }
    }
}

@Composable
fun TopBottomShadow(
    modifier: Modifier = Modifier,
    alpha: Float = 1f
) {
    Box(
        modifier
            .alpha(alpha)
            .fillMaxWidth()
            .height(4.dp)
            .background(
                brush = Brush.verticalGradient(
                    0.0f to Color.Black.copy(.1f),
                    1.0f to Color.Transparent
                )
            )
    )
}

@Preview
@Composable
fun BottomTopShadowPreview() {
    AppTheme {
        Column {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            )

            BottomTopShadow()
        }
    }
}

@Composable
fun BottomTopShadow(
    modifier: Modifier = Modifier,
    alpha: Float = 1f
) {
    Box(
        modifier
            .alpha(alpha)
            .fillMaxWidth()
            .height(4.dp)
            .background(
                brush = Brush.verticalGradient(
                    0.0f to Color.Transparent,
                    1.0f to Color.Black.copy(.1f)
                )
            )
    )
}