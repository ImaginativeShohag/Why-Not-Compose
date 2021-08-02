package org.imaginativeworld.whynotcompose.ui.compositions

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme

@Composable
fun defaultButtonBackgroundBrush(
    alpha: Float = 1f
) = Brush.verticalGradient(
    0.0f to MaterialTheme.colors.primary.copy(alpha),
    1.0f to MaterialTheme.colors.primaryVariant.copy(alpha)
)

@Composable
fun defaultButtonShape() = RoundedCornerShape(8.dp)


@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes iconDrawableId: Int? = null,
    height: Dp = 48.dp,
    fontSize: TextUnit = 16.sp,
    horizontalPadding: Dp = 16.dp,
    onClick: () -> Unit
) {
    val currentFocus = LocalFocusManager.current

    TextButton(
        modifier = modifier
            .background(
                brush = defaultButtonBackgroundBrush(),
                shape = defaultButtonShape()
            )
            .height(height),
        shape = defaultButtonShape(),
        colors = ButtonDefaults.textButtonColors(
            contentColor = Color.White
        ),
        onClick = {
            currentFocus.clearFocus()

            onClick()
        }) {
        if (iconDrawableId != null) {
            Icon(
                modifier = Modifier.padding(horizontalPadding, 0.dp, 0.dp, 0.dp),
                painter = painterResource(id = iconDrawableId),
                contentDescription = text
            )
        }
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontalPadding, 0.dp),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun DefaultButtonPreview() {
    AppTheme {
        DefaultButton(
            text = "Default Button"
        ) {

        }
    }
}

@Preview
@Composable
private fun DefaultButtonWithIconPreview() {
    AppTheme {
        DefaultButton(
            text = "Default Button",
            iconDrawableId = R.drawable.ic_danger_circle
        ) {

        }
    }
}

// ----------------------------------------------------------------
// ----------------------------------------------------------------

@Composable
fun DefaultIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes iconDrawableId: Int? = null,
    contentDescription: String = "Icon",
    size: Dp = 48.dp,
    padding: Dp = 4.dp,
    onClick: () -> Unit
) {
    val currentFocus = LocalFocusManager.current

    TextButton(
        modifier = modifier
            .background(
                brush = defaultButtonBackgroundBrush(),
                shape = defaultButtonShape()
            )
            .size(size),
        shape = defaultButtonShape(),
        colors = ButtonDefaults.textButtonColors(
            contentColor = Color.White
        ),
        onClick = {
            currentFocus.clearFocus()

            onClick()
        }) {
        if (iconDrawableId != null) {
            Icon(
                modifier = Modifier.padding(padding),
                painter = painterResource(id = iconDrawableId),
                contentDescription = contentDescription
            )
        }
    }
}

@Preview
@Composable
private fun DefaultIconButtonPreview() {
    AppTheme {
        DefaultIconButton(
            iconDrawableId = R.drawable.ic_danger_circle
        ) {

        }
    }
}

// ----------------------------------------------------------------
// ----------------------------------------------------------------

@Composable
fun DefaultIconButtonForMap(
    modifier: Modifier = Modifier,
    @DrawableRes iconDrawableId: Int? = null,
    contentDescription: String = "Icon",
    size: Dp = 48.dp,
    padding: Dp = 4.dp,
    onClick: () -> Unit
) {
    val currentFocus = LocalFocusManager.current

    TextButton(
        modifier = modifier
            .shadow(4.dp, CircleShape)
            .background(Color.White)
            .size(size),
        shape = CircleShape,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colors.onBackground
        ),
        onClick = {
            currentFocus.clearFocus()

            onClick()
        }) {
        if (iconDrawableId != null) {
            Icon(
                modifier = Modifier.padding(padding),
                painter = painterResource(id = iconDrawableId),
                contentDescription = contentDescription
            )
        }
    }
}

@Preview
@Composable
private fun DefaultIconButtonForMapPreview() {
    AppTheme {
        DefaultIconButtonForMap(
            iconDrawableId = R.drawable.ic_danger_circle
        ) {

        }
    }
}

// ----------------------------------------------------------------
// ----------------------------------------------------------------

@Composable
fun DefaultIconBorderLessButton(
    modifier: Modifier = Modifier,
    @DrawableRes iconDrawableId: Int? = null,
    contentDescription: String = "Icon",
    size: Dp = 48.dp,
    padding: Dp = 4.dp,
    color: Color = MaterialTheme.colors.primary,
    shape: Shape = defaultButtonShape(),
    onClick: () -> Unit
) {
    val currentFocus = LocalFocusManager.current

    TextButton(
        modifier = modifier
            .size(size),
        shape = shape,
        colors = ButtonDefaults.textButtonColors(
            contentColor = color
        ),
        onClick = {
            currentFocus.clearFocus()

            onClick()
        }) {
        if (iconDrawableId != null) {
            Icon(
                modifier = Modifier.padding(padding),
                painter = painterResource(id = iconDrawableId),
                contentDescription = contentDescription
            )
        }
    }
}

@Preview
@Composable
private fun DefaultIconBorderLessButtonPreview() {
    AppTheme {
        DefaultIconBorderLessButton(
            iconDrawableId = R.drawable.ic_danger_circle
        ) {

        }
    }
}

// ----------------------------------------------------------------
// ----------------------------------------------------------------

@Composable
fun DefaultBorderedButton(
    text: String,
    @DrawableRes iconDrawableId: Int? = null,
    modifier: Modifier = Modifier,
    height: Dp = 48.dp,
    fontSize: TextUnit = 18.sp,
    horizontalPadding: Dp = 16.dp,
    onClick: () -> Unit
) {
    val currentFocus = LocalFocusManager.current

    TextButton(
        modifier = modifier
            .border(
                width = 1.dp,
                brush = defaultButtonBackgroundBrush(.3f),
                shape = defaultButtonShape()
            )
            .height(height),
        shape = defaultButtonShape(),
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colors.primary
        ),
        onClick = {
            currentFocus.clearFocus()

            onClick()
        }) {
        if (iconDrawableId != null) {
            Icon(
                modifier = Modifier.padding(horizontalPadding, 0.dp, 0.dp, 0.dp),
                painter = painterResource(id = iconDrawableId),
                contentDescription = text
            )
        }
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontalPadding, 0.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun DefaultBorderedButtonPreview() {
    AppTheme {
        DefaultBorderedButton(
            text = "Default Bordered Button"
        ) {

        }
    }
}

@Preview
@Composable
private fun DefaultBorderedButtonWithIconPreview() {
    AppTheme {
        DefaultBorderedButton(
            text = "Default Bordered Button",
            iconDrawableId = R.drawable.ic_danger_circle
        ) {

        }
    }
}

// ----------------------------------------------------------------
// ----------------------------------------------------------------

@Composable
fun DefaultTextButton(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes iconDrawableId: Int? = null,
    height: Dp = 48.dp,
    color: Color = MaterialTheme.colors.primary,
    fontSize: TextUnit = 18.sp,
    horizontalPadding: Dp = 8.dp,
    verticalPadding: Dp = 4.dp,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val currentFocus = LocalFocusManager.current

    val textColor by animateColorAsState(targetValue = if (enabled) color else color.copy(alpha = ContentAlpha.disabled))

    TextButton(
        modifier = modifier
            .height(height),
        shape = defaultButtonShape(),
        contentPadding = PaddingValues(horizontalPadding, verticalPadding),
        enabled = enabled,
        onClick = {
            currentFocus.clearFocus()

            onClick()
        }) {
        if (iconDrawableId != null) {
            Icon(
                modifier = Modifier.padding(0.dp, 0.dp, horizontalPadding, 0.dp),
                painter = painterResource(id = iconDrawableId),
                contentDescription = text
            )
        }
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = FontWeight.Medium,
            modifier = Modifier,
            textAlign = TextAlign.Center,
            color = textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun DefaultTextButtonPreview() {
    AppTheme {
        DefaultTextButton(
            text = "Default Text Button"
        ) {

        }
    }
}

@Preview
@Composable
private fun DefaultTextButtonWithIconPreview() {
    AppTheme {
        DefaultTextButton(
            text = "Default Text Button",
            iconDrawableId = R.drawable.ic_danger_circle
        ) {

        }
    }
}

