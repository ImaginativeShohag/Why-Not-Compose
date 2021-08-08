package org.imaginativeworld.whynotcompose.ui.screens.home.index

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.ui.screens.AppComponent
import org.imaginativeworld.whynotcompose.ui.screens.Screen
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme
import org.imaginativeworld.whynotcompose.ui.theme.TailwindCSSColor


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
    Scaffold {

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
                    .border(2.dp, MaterialTheme.colors.primary, MaterialTheme.shapes.small)
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
            .padding(8.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = color,
            contentColor = Color.White
        ),
        onClick = onClick,
        contentPadding = PaddingValues(16.dp, 16.dp),
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

@Preview
@Composable
fun HomeIndexScreenPreviewDark() {
    AppTheme(
        darkTheme = true
    ) {
        HomeIndexScreen()
    }
}

data class MenuItem(
    val name: String,
    @DrawableRes val icon: Int,
    val color: Color,
    val route: Screen,
)