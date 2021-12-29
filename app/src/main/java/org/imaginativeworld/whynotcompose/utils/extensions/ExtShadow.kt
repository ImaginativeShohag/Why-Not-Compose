package org.imaginativeworld.whynotcompose.utils.extensions

import androidx.annotation.FloatRange
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A workaround for custom colored shadow.
 */
fun Modifier.shadow(
    spread: Dp = 4.dp,
    @FloatRange(from = 0.0, to = 1.0) alpha: Float = .25f,
    color: Color = Color.Gray,
    radius: Dp = 8.dp,
): Modifier {
    val spreadLayer = spread.value.toInt()

    var modifier = this

    for (x in spreadLayer downTo 1) {
        modifier = modifier
            .border(
                width = 1.dp,
                color = color.copy(alpha / x),
                shape = RoundedCornerShape(radius + x.dp),
            )
            .padding(1.dp)
    }

    return modifier
}