package org.imaginativeworld.whynotcompose.utils.composeutils


import androidx.compose.runtime.Composable
import coil.compose.rememberImagePainter

@Composable
fun rememberImagePainterFade(data: Any?) = rememberImagePainter(
    data = data,
    builder = {
        crossfade(true)
    }
)
