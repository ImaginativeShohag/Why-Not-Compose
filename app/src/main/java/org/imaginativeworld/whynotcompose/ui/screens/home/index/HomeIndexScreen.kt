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

package org.imaginativeworld.whynotcompose.ui.screens.home.index

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.common.compose.theme.TailwindCSSColor
import org.imaginativeworld.whynotcompose.ui.screens.Screen
import org.imaginativeworld.whynotcompose.utils.extensions.openUrl
import org.imaginativeworld.whynotcompose.utils.extensions.shadow

private val menuItems = listOf(
    MenuItem(
        name = "Animations",
        icon = R.drawable.ic_round_animation_24,
        color = TailwindCSSColor.Yellow500,
        route = Screen.Animations
    ),
    MenuItem(
        name = "Compositions",
        icon = R.drawable.ic_round_widgets_24,
        color = TailwindCSSColor.Red500,
        route = Screen.Compositions
    ),
    MenuItem(
        name = "UIs",
        icon = R.drawable.ic_round_grid_view_24,
        color = TailwindCSSColor.Blue500,
        route = Screen.UIs
    ),
    MenuItem(
        name = "Tutorials",
        icon = R.drawable.ic_round_sticky_note_2_24,
        color = TailwindCSSColor.Purple500,
        route = Screen.Tutorials
    ),
)

@ExperimentalFoundationApi
@Composable
fun HomeIndexScreen(
    navigate: (Screen) -> Unit = {},
    turnOnDarkMode: (Boolean) -> Unit = {},
) {
    val context = LocalContext.current

    Scaffold(
        Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding()
    ) {

        Column(
            Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AppComponent.Header(
                text = stringResource(id = R.string.app_name)
            )

            val isDark = !MaterialTheme.colors.isLight
            val (darkModeState, onDarkModeStateChange) = remember { mutableStateOf(isDark) }

            Row(
                Modifier
                    .padding(start = 32.dp, end = 32.dp)
                    .fillMaxWidth()
                    .height(56.dp)
                    .border(2.dp, MaterialTheme.colors.primary, MaterialTheme.shapes.medium)
                    .clip(MaterialTheme.shapes.medium)
                    .clickable {
                        onDarkModeStateChange(!darkModeState)
                        turnOnDarkMode(!darkModeState)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isDark) R.drawable.ic_moon_stars else R.drawable.ic_brightness_high
                    ),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.requiredWidth(16.dp))

                Text(
                    modifier = Modifier,
                    text = if (isDark) "Dark Mode" else "Light Mode",
                    fontWeight = FontWeight.Medium,
                )
            }

            Spacer(modifier = Modifier.requiredHeight(8.dp))

            LazyVerticalGrid(
                modifier = Modifier.weight(1f),
                cells = GridCells.Fixed(2),
                contentPadding = PaddingValues(24.dp, 8.dp)
            ) {
                items(menuItems) { menu ->
                    ModuleButton(
                        name = menu.name,
                        icon = menu.icon,
                        color = menu.color,
                        onClick = {
                            navigate(menu.route)
                        }
                    )
                }
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Developed By —",
                    fontSize = 12.sp,
                    color = MaterialTheme.colors.onBackground.copy(.75f)
                )

                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            context.openUrl("https://imaginativeshohag.github.io/")
                        }
                        .padding(4.dp),
                    text = "@ImaginativeShohag",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ModuleButton(
    name: String,
    @DrawableRes icon: Int,
    color: Color,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth()
            .shadow(
                spread = 8.dp,
                alpha = .25f,
                color = color,
                radius = 8.dp,
            ),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = color
        ),
        onClick = onClick,
        contentPadding = PaddingValues(8.dp),
        elevation = null,
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(id = icon),
                contentDescription = name,
                tint = LocalContentColor.current,
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = name,
                color = LocalContentColor.current,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview
@Composable
fun HomeIndexScreenPreview() {
    AppTheme {
        HomeIndexScreen()
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeIndexScreenPreviewDark() {
    AppTheme {
        HomeIndexScreen()
    }
}

data class MenuItem(
    val name: String,
    @DrawableRes val icon: Int,
    val color: Color,
    val route: Screen,
)
