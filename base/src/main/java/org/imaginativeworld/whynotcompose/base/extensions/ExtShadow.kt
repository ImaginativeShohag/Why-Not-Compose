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

package org.imaginativeworld.whynotcompose.base.extensions

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
    spread: Dp = 8.dp,
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
