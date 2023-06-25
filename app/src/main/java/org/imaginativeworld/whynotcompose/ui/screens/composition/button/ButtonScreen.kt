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

package org.imaginativeworld.whynotcompose.ui.screens.composition.button

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

private val ELEMENT_HEIGHT = 48.dp

@Composable
fun defaultButtonBackgroundBrush(
    alpha: Float = 1f
) = Brush.verticalGradient(
    0.0f to MaterialTheme.colors.primary.copy(alpha),
    1.0f to MaterialTheme.colors.primaryVariant.copy(alpha)
)

@Composable
fun ButtonScreen(
    goBack: () -> Unit
) {
    ButtonScreenSkeleton(
        goBack = goBack
    )
}

@Preview
@Composable
fun ButtonScreenSkeletonPreview() {
    AppTheme {
        ButtonScreenSkeleton()
    }
}

@Composable
fun ButtonScreenSkeleton(
    goBack: () -> Unit = {}
) {
    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding()
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 32.dp, end = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppComponent.Header(
                "Button",
                goBack = goBack
            )

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

            AppComponent.SubHeader("Official Examples")

            // ----------------------------------------------------------------

            Button(onClick = { /* Do something! */ }) {
                Text(
                    "Button"
                )
            }

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            OutlinedButton(onClick = { /* Do something! */ }) {
                Text(
                    "Outlined Button"
                )
            }

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            TextButton(onClick = { /* Do something! */ }) {
                Text(
                    "Text Button"
                )
            }

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            Button(onClick = { /* Do something! */ }) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                    tint = Color.White
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    "Like",
                    color = Color.White
                )
            }

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
            }

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            var checked by remember { mutableStateOf(false) }

            IconToggleButton(checked = checked, onCheckedChange = { checked = it }) {
                val tint by animateColorAsState(
                    if (checked) Color(0xFFEC407A) else Color(0xFFB0BEC5)
                )
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Localized description",
                    tint = tint
                )
            }

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            Divider()

            AppComponent.SubHeader("Solid Buttons")

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            SolidButton(
                text = "Like",
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            SolidButton(
                text = "Like",
                startIcon = R.drawable.ic_danger_circle,
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            SolidButton(
                text = "Like",
                endIcon = R.drawable.ic_danger_circle,
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            SolidButton(
                text = "Like",
                startIcon = R.drawable.ic_danger_circle,
                endIcon = R.drawable.ic_danger_circle,
                onClick = {}
            )

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            Divider()

            AppComponent.SubHeader("Solid Wide Buttons")

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            SolidWideButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Like",
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            SolidWideButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Like",
                startIcon = R.drawable.ic_danger_circle,
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            SolidWideButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Like",
                endIcon = R.drawable.ic_danger_circle,
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            SolidWideButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Like",
                startIcon = R.drawable.ic_danger_circle,
                endIcon = R.drawable.ic_danger_circle,
                onClick = {}
            )

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            Divider()

            AppComponent.SubHeader("Gradient Buttons")

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            GradientButton(
                text = "Like",
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            GradientButton(
                text = "Like",
                startIcon = R.drawable.ic_danger_circle,
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            GradientButton(
                text = "Like",
                endIcon = R.drawable.ic_danger_circle,
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            GradientButton(
                text = "Like",
                startIcon = R.drawable.ic_danger_circle,
                endIcon = R.drawable.ic_danger_circle,
                onClick = {}
            )

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            Divider()

            AppComponent.SubHeader("Gradient Wide Buttons")

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            GradientWideButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Like",
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            GradientWideButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Like",
                startIcon = R.drawable.ic_danger_circle,
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            GradientWideButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Like",
                endIcon = R.drawable.ic_danger_circle,
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            GradientWideButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Like",
                startIcon = R.drawable.ic_danger_circle,
                endIcon = R.drawable.ic_danger_circle,
                onClick = {}
            )

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            Divider()

            AppComponent.SubHeader("Bordered Buttons")

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            BorderedButton(
                text = "Like",
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            BorderedButton(
                text = "Like",
                startIcon = R.drawable.ic_danger_circle,
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            BorderedButton(
                text = "Like",
                endIcon = R.drawable.ic_danger_circle,
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            BorderedButton(
                text = "Like",
                startIcon = R.drawable.ic_danger_circle,
                endIcon = R.drawable.ic_danger_circle,
                onClick = {}
            )

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            Divider()

            AppComponent.SubHeader("Bordered Wide Buttons")

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            BorderedWideButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Like",
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            BorderedWideButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Like",
                startIcon = R.drawable.ic_danger_circle,
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            BorderedWideButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Like",
                endIcon = R.drawable.ic_danger_circle,
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            BorderedWideButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Like",
                startIcon = R.drawable.ic_danger_circle,
                endIcon = R.drawable.ic_danger_circle,
                onClick = {}
            )

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            Divider()

            AppComponent.SubHeader("Borderless Buttons")

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            BorderlessButton(
                text = "Like",
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            BorderlessButton(
                text = "Like",
                startIcon = R.drawable.ic_danger_circle,
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            BorderlessButton(
                text = "Like",
                endIcon = R.drawable.ic_danger_circle,
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            BorderlessButton(
                text = "Like",
                startIcon = R.drawable.ic_danger_circle,
                endIcon = R.drawable.ic_danger_circle,
                onClick = {}
            )

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            Divider()

            AppComponent.SubHeader("Borderless Wide Buttons")

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            BorderlessWideButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Like",
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            BorderlessWideButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Like",
                startIcon = R.drawable.ic_danger_circle,
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            BorderlessWideButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Like",
                endIcon = R.drawable.ic_danger_circle,
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            BorderlessWideButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Like",
                startIcon = R.drawable.ic_danger_circle,
                endIcon = R.drawable.ic_danger_circle,
                onClick = {}
            )

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            Divider()

            AppComponent.SubHeader("Icon Buttons")

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            SolidIconButton(
                icon = R.drawable.ic_danger_circle,
                contentDescription = null,
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            GradientIconButton(
                icon = R.drawable.ic_danger_circle,
                contentDescription = null,
                onClick = {}
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            BorderlessIconButton(
                icon = R.drawable.ic_danger_circle,
                contentDescription = null,
                onClick = {}
            )

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.BigSpacer()
        }
    }
}

// ------------------------------------------------------
// ------------------------------------------------------

@Composable
fun SolidButton(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes startIcon: Int? = null,
    @DrawableRes endIcon: Int? = null,
    height: Dp = ButtonDefaults.MinHeight,
    fontSize: TextUnit = 16.sp,
    backgroundColor: Color = MaterialTheme.colors.primary,
    shape: Shape = MaterialTheme.shapes.medium,
    onClick: () -> Unit
) {
    val currentFocus = LocalFocusManager.current

    Button(
        modifier = modifier.height(height),
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        ),
        onClick = {
            currentFocus.clearFocus()

            onClick()
        }
    ) {
        if (startIcon != null) {
            Icon(
                painterResource(id = startIcon),
                contentDescription = text,
                modifier = Modifier.size(ButtonDefaults.IconSize),
                tint = Color.White
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        }

        Text(
            text,
            style = MaterialTheme.typography.button,
            color = Color.White,
            fontSize = fontSize
        )

        if (endIcon != null) {
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Icon(
                painterResource(id = endIcon),
                contentDescription = text,
                modifier = Modifier.size(ButtonDefaults.IconSize),
                tint = Color.White
            )
        }
    }
}

@Composable
fun SolidWideButton(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes startIcon: Int? = null,
    @DrawableRes endIcon: Int? = null,
    height: Dp = ButtonDefaults.MinHeight,
    fontSize: TextUnit = 16.sp,
    horizontalPadding: Dp = ButtonDefaults.IconSpacing,
    backgroundColor: Color = MaterialTheme.colors.primary,
    shape: Shape = MaterialTheme.shapes.medium,
    onClick: () -> Unit
) {
    val currentFocus = LocalFocusManager.current

    Button(
        modifier = modifier
            .height(height),
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = Color.White
        ),
        onClick = {
            currentFocus.clearFocus()

            onClick()
        }
    ) {
        if (startIcon != null) {
            Icon(
                modifier = Modifier.size(ButtonDefaults.IconSize),
                painter = painterResource(id = startIcon),
                contentDescription = text
            )
            Spacer(Modifier.size(horizontalPadding))
        }

        Text(
            modifier = Modifier
                .padding(
                    start = if (startIcon != null) 0.dp else ButtonDefaults.IconSize + horizontalPadding,
                    end = if (endIcon != null) 0.dp else ButtonDefaults.IconSize + horizontalPadding
                )
                .weight(1f),
            text = text,
            fontSize = fontSize,
            style = MaterialTheme.typography.button,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        if (endIcon != null) {
            Spacer(Modifier.size(horizontalPadding))
            Icon(
                modifier = Modifier.size(ButtonDefaults.IconSize),
                painter = painterResource(id = endIcon),
                contentDescription = text
            )
        }
    }
}

// ------------------------------------------------------
// ------------------------------------------------------

@Composable
fun GradientButton(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes startIcon: Int? = null,
    @DrawableRes endIcon: Int? = null,
    height: Dp = ButtonDefaults.MinHeight,
    fontSize: TextUnit = 16.sp,
    backgroundGradient: Brush = defaultButtonBackgroundBrush(),
    shape: Shape = MaterialTheme.shapes.medium,
    onClick: () -> Unit
) {
    val currentFocus = LocalFocusManager.current

    TextButton(
        modifier = modifier
            .height(height)
            .background(
                brush = backgroundGradient,
                shape = shape
            ),
        shape = shape,
        onClick = {
            currentFocus.clearFocus()

            onClick()
        }
    ) {
        if (startIcon != null) {
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Icon(
                painterResource(id = startIcon),
                contentDescription = text,
                modifier = Modifier.size(ButtonDefaults.IconSize),
                tint = Color.White
            )
        }

        Spacer(Modifier.size(ButtonDefaults.IconSpacing))

        Text(
            text,
            style = MaterialTheme.typography.button,
            color = Color.White,
            fontSize = fontSize
        )

        Spacer(Modifier.size(ButtonDefaults.IconSpacing))

        if (endIcon != null) {
            Icon(
                painterResource(id = endIcon),
                contentDescription = text,
                modifier = Modifier.size(ButtonDefaults.IconSize),
                tint = Color.White
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        }
    }
}

@Composable
fun GradientWideButton(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes startIcon: Int? = null,
    @DrawableRes endIcon: Int? = null,
    height: Dp = ButtonDefaults.MinHeight,
    fontSize: TextUnit = 16.sp,
    horizontalPadding: Dp = ButtonDefaults.IconSpacing,
    backgroundGradient: Brush = defaultButtonBackgroundBrush(),
    shape: Shape = MaterialTheme.shapes.medium,
    onClick: () -> Unit
) {
    val currentFocus = LocalFocusManager.current

    TextButton(
        modifier = modifier
            .height(height)
            .background(
                brush = backgroundGradient,
                shape = shape
            ),
        colors = ButtonDefaults.textButtonColors(
            contentColor = Color.White
        ),
        shape = shape,
        onClick = {
            currentFocus.clearFocus()

            onClick()
        }
    ) {
        if (startIcon != null) {
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Icon(
                modifier = Modifier.size(ButtonDefaults.IconSize),
                painter = painterResource(id = startIcon),
                contentDescription = text,
                tint = Color.White
            )
        }

        Spacer(Modifier.size(ButtonDefaults.IconSpacing))

        Text(
            modifier = Modifier
                .padding(
                    start = if (startIcon != null) 0.dp else ButtonDefaults.IconSize + horizontalPadding,
                    end = if (endIcon != null) 0.dp else ButtonDefaults.IconSize + horizontalPadding
                )
                .weight(1f),
            text = text,
            fontSize = fontSize,
            style = MaterialTheme.typography.button,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(Modifier.size(ButtonDefaults.IconSpacing))

        if (endIcon != null) {
            Icon(
                modifier = Modifier.size(ButtonDefaults.IconSize),
                painter = painterResource(id = endIcon),
                contentDescription = text,
                tint = Color.White
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        }
    }
}

// ----------------------------------------------------------------
// ----------------------------------------------------------------

@Composable
fun BorderedButton(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes startIcon: Int? = null,
    @DrawableRes endIcon: Int? = null,
    height: Dp = ButtonDefaults.MinHeight,
    fontSize: TextUnit = 16.sp,
    borderGradient: Brush = defaultButtonBackgroundBrush(.3f),
    shape: Shape = MaterialTheme.shapes.medium,
    onClick: () -> Unit
) {
    val currentFocus = LocalFocusManager.current

    OutlinedButton(
        modifier = modifier
            .height(height),
        border = BorderStroke(
            width = 2.dp,
            brush = borderGradient
        ),
        shape = shape,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.Transparent
        ),
        onClick = {
            currentFocus.clearFocus()

            onClick()
        }
    ) {
        if (startIcon != null) {
            Icon(
                painterResource(id = startIcon),
                contentDescription = text,
                modifier = Modifier.size(ButtonDefaults.IconSize),
                tint = MaterialTheme.colors.primary
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        }

        Text(
            text,
            style = MaterialTheme.typography.button,
            fontSize = fontSize
        )

        if (endIcon != null) {
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Icon(
                painterResource(id = endIcon),
                contentDescription = text,
                modifier = Modifier.size(ButtonDefaults.IconSize),
                tint = MaterialTheme.colors.primary
            )
        }
    }
}

@Composable
fun BorderedWideButton(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes startIcon: Int? = null,
    @DrawableRes endIcon: Int? = null,
    height: Dp = ButtonDefaults.MinHeight,
    fontSize: TextUnit = 16.sp,
    horizontalPadding: Dp = ButtonDefaults.IconSpacing,
    borderGradient: Brush = defaultButtonBackgroundBrush(.3f),
    shape: Shape = MaterialTheme.shapes.medium,
    onClick: () -> Unit
) {
    val currentFocus = LocalFocusManager.current

    OutlinedButton(
        modifier = modifier
            .height(height),
        border = BorderStroke(
            width = 2.dp,
            brush = borderGradient
        ),
        shape = shape,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.Transparent
        ),
        onClick = {
            currentFocus.clearFocus()

            onClick()
        }
    ) {
        if (startIcon != null) {
            Icon(
                modifier = Modifier.size(ButtonDefaults.IconSize),
                painter = painterResource(id = startIcon),
                contentDescription = text,
                tint = MaterialTheme.colors.primary
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        }

        Text(
            modifier = Modifier
                .padding(
                    start = if (startIcon != null) 0.dp else ButtonDefaults.IconSize + horizontalPadding,
                    end = if (endIcon != null) 0.dp else ButtonDefaults.IconSize + horizontalPadding
                )
                .weight(1f),
            text = text,
            fontSize = fontSize,
            style = MaterialTheme.typography.button,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        if (endIcon != null) {
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Icon(
                modifier = Modifier.size(ButtonDefaults.IconSize),
                painter = painterResource(id = endIcon),
                contentDescription = text,
                tint = MaterialTheme.colors.primary
            )
        }
    }
}

// ------------------------------------------------------
// ------------------------------------------------------

@Composable
fun BorderlessButton(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes startIcon: Int? = null,
    @DrawableRes endIcon: Int? = null,
    height: Dp = ButtonDefaults.MinHeight,
    fontSize: TextUnit = 16.sp,
    shape: Shape = MaterialTheme.shapes.medium,
    onClick: () -> Unit
) {
    val currentFocus = LocalFocusManager.current

    TextButton(
        modifier = modifier
            .height(height),
        shape = shape,
        onClick = {
            currentFocus.clearFocus()

            onClick()
        }
    ) {
        if (startIcon != null) {
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Icon(
                painterResource(id = startIcon),
                contentDescription = text,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }

        Spacer(Modifier.size(ButtonDefaults.IconSpacing))

        Text(
            text,
            style = MaterialTheme.typography.button,
            fontSize = fontSize
        )

        Spacer(Modifier.size(ButtonDefaults.IconSpacing))

        if (endIcon != null) {
            Icon(
                painterResource(id = endIcon),
                contentDescription = text,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        }
    }
}

@Composable
fun BorderlessWideButton(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes startIcon: Int? = null,
    @DrawableRes endIcon: Int? = null,
    height: Dp = ButtonDefaults.MinHeight,
    fontSize: TextUnit = 16.sp,
    horizontalPadding: Dp = ButtonDefaults.IconSpacing,
    shape: Shape = MaterialTheme.shapes.medium,
    onClick: () -> Unit
) {
    val currentFocus = LocalFocusManager.current

    TextButton(
        modifier = modifier
            .height(height),
        shape = shape,
        onClick = {
            currentFocus.clearFocus()

            onClick()
        }
    ) {
        if (startIcon != null) {
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Icon(
                modifier = Modifier.size(ButtonDefaults.IconSize),
                painter = painterResource(id = startIcon),
                contentDescription = text
            )
        }

        Spacer(Modifier.size(ButtonDefaults.IconSpacing))

        Text(
            modifier = Modifier
                .padding(
                    start = if (startIcon != null) 0.dp else ButtonDefaults.IconSize + horizontalPadding,
                    end = if (endIcon != null) 0.dp else ButtonDefaults.IconSize + horizontalPadding
                )
                .weight(1f),
            text = text,
            fontSize = fontSize,
            style = MaterialTheme.typography.button,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(Modifier.size(ButtonDefaults.IconSpacing))

        if (endIcon != null) {
            Icon(
                modifier = Modifier.size(ButtonDefaults.IconSize),
                painter = painterResource(id = endIcon),
                contentDescription = text
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        }
    }
}

// ----------------------------------------------------------------
// ----------------------------------------------------------------

@Composable
fun SolidIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    contentDescription: String?,
    backgroundColor: Color = MaterialTheme.colors.primary,
    size: Dp = ButtonDefaults.MinHeight,
    shape: Shape = MaterialTheme.shapes.medium,
    onClick: () -> Unit
) {
    val currentFocus = LocalFocusManager.current

    Button(
        modifier = modifier
            .size(size),
        shape = shape,
        colors = ButtonDefaults.textButtonColors(
            contentColor = Color.White,
            backgroundColor = backgroundColor
        ),
        contentPadding = PaddingValues(0.dp),
        onClick = {
            currentFocus.clearFocus()

            onClick()
        }
    ) {
        Icon(
            modifier = Modifier.size(ButtonDefaults.IconSize),
            painter = painterResource(id = icon),
            contentDescription = contentDescription
        )
    }
}

// ----------------------------------------------------------------
// ----------------------------------------------------------------

@Composable
fun GradientIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    contentDescription: String?,
    backgroundGradient: Brush = defaultButtonBackgroundBrush(),
    size: Dp = ButtonDefaults.MinHeight,
    shape: Shape = MaterialTheme.shapes.medium,
    onClick: () -> Unit
) {
    val currentFocus = LocalFocusManager.current

    TextButton(
        modifier = modifier
            .background(
                brush = backgroundGradient,
                shape = shape
            )
            .size(size),
        contentPadding = PaddingValues(0.dp),
        shape = shape,
        colors = ButtonDefaults.textButtonColors(
            contentColor = Color.White
        ),
        onClick = {
            currentFocus.clearFocus()

            onClick()
        }
    ) {
        Icon(
            modifier = Modifier.size(ButtonDefaults.IconSize),
            painter = painterResource(id = icon),
            contentDescription = contentDescription
        )
    }
}

// ----------------------------------------------------------------
// ----------------------------------------------------------------

@Composable
fun BorderlessIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    contentDescription: String?,
    size: Dp = ButtonDefaults.MinHeight,
    shape: Shape = MaterialTheme.shapes.medium,
    onClick: () -> Unit
) {
    val currentFocus = LocalFocusManager.current

    TextButton(
        modifier = modifier
            .size(size),
        shape = shape,
        contentPadding = PaddingValues(0.dp),
        onClick = {
            currentFocus.clearFocus()

            onClick()
        }
    ) {
        Icon(
            modifier = Modifier.size(ButtonDefaults.IconSize),
            painter = painterResource(id = icon),
            contentDescription = contentDescription
        )
    }
}
