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

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import org.imaginativeworld.whynotcompose.BuildConfig
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.base.R as BaseR
import org.imaginativeworld.whynotcompose.base.extensions.longToast
import org.imaginativeworld.whynotcompose.base.extensions.openUrl
import org.imaginativeworld.whynotcompose.base.extensions.openUrlElseToast
import org.imaginativeworld.whynotcompose.base.extensions.shadow
import org.imaginativeworld.whynotcompose.base.extensions.toast
import org.imaginativeworld.whynotcompose.base.models.UIThemeMode
import org.imaginativeworld.whynotcompose.base.models.humanReadable
import org.imaginativeworld.whynotcompose.base.models.nextMode
import org.imaginativeworld.whynotcompose.base.utils.UIThemeController
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.common.compose.theme.AppleSystemColor
import org.imaginativeworld.whynotcompose.common.compose.theme.TailwindCSSColor
import org.imaginativeworld.whynotcompose.ui.screens.MainActivity
import org.imaginativeworld.whynotcompose.ui.screens.Screen

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
    )
)

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun HomeIndexScreen(
    navigate: (Screen) -> Unit = {},
    updateUiThemeMode: (UIThemeMode) -> Unit = {}
) {
    val context = LocalContext.current

    val uiThemeMode by UIThemeController.uiThemeMode.collectAsState()
    val (darkModeState, onDarkModeStateChange) = remember { mutableStateOf(uiThemeMode) }
    var showNotificationPermissionRationale by remember { mutableStateOf(false) }

    val requestNotificationPermission =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->
            showNotificationPermissionRationale = false

            if (!permissionGranted) {
                context.toast("Notification permission is denied. Some feature will not work.")
            }
        }

    val askForNotificationPermission: () -> Unit = {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_DENIED
        ) {
            when {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // no-op
                }

                shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) -> {
                    showNotificationPermissionRationale = true
                }

                else -> {
                    requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        askForNotificationPermission()
    }

    Scaffold(
        Modifier
            .testTag("screen:home")
            .navigationBarsPadding()
            .imePadding()
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(24.dp, 24.dp, 24.dp, 24.dp)
                ) {
                    item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))

                            Text(
                                modifier = Modifier
                                    .padding(
                                        start = 16.dp,
                                        top = 16.dp,
                                        end = 16.dp
                                    )
                                    .fillMaxWidth(),
                                text = stringResource(id = BaseR.string.app_name),
                                style = MaterialTheme.typography.titleLarge,
                                textAlign = TextAlign.Center
                            )

                            Text(
                                modifier = Modifier
                                    .padding(
                                        start = 16.dp,
                                        top = 4.dp,
                                        end = 16.dp,
                                        bottom = 16.dp
                                    ),
                                text = "Version ${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})",
                                textAlign = TextAlign.Center,
                                fontSize = 12.sp,
                                fontFamily = FontFamily.Monospace
                            )

                            if (showNotificationPermissionRationale) {
                                Card(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp)
                                        .padding(bottom = 16.dp),
                                    border = BorderStroke(
                                        width = 1.dp,
                                        TailwindCSSColor.Yellow500.copy(alpha = 1f)
                                    )
                                ) {
                                    Row(
                                        Modifier.padding(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .weight(1f),
                                            text = "Notification permission is necessary for some sample. Please allow.",
                                            fontSize = 14.sp
                                        )

                                        Button(onClick = {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                                requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
                                            }
                                        }) {
                                            Text("Allow")
                                        }
                                    }
                                }
                            }
                        }
                    }

                    item {
                        ModuleButton(
                            name = uiThemeMode.humanReadable(),
                            icon = when (uiThemeMode) {
                                UIThemeMode.AUTO -> R.drawable.ic_night_sight_auto
                                UIThemeMode.LIGHT -> R.drawable.ic_brightness_high
                                UIThemeMode.DARK -> R.drawable.ic_moon_stars
                            },
                            color = TailwindCSSColor.Green500,
                            onClick = {
                                onDarkModeStateChange(darkModeState.nextMode())
                                updateUiThemeMode(darkModeState.nextMode())
                            }
                        )
                    }

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

                    item {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(
                                    spread = 8.dp,
                                    alpha = .25f,
                                    color = TailwindCSSColor.Pink500,
                                    radius = 8.dp
                                ),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                contentColor = TailwindCSSColor.Pink500
                            ),
                            onClick = {
                                context.openUrlElseToast("https://imaginativeshohag.github.io/")
                            },
                            contentPadding = PaddingValues(8.dp),
                            elevation = null
                        ) {
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Developed By —",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onBackground.copy(.75f)
                                )

                                Text(
                                    modifier = Modifier
                                        .padding(top = 4.dp),
                                    text = "@ImaginativeShohag",
                                    color = TailwindCSSColor.Pink500,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                            }
                        }
                    }

                    item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(
                                    spread = 8.dp,
                                    alpha = .25f,
                                    color = TailwindCSSColor.Indigo500,
                                    radius = 8.dp
                                ),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                contentColor = TailwindCSSColor.Indigo500
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
                                            modifier = Modifier,
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
                }

                Box(
                    Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(16.dp)
                        .background(
                            Brush.verticalGradient(
                                0f to Color.Transparent,
                                1f to MaterialTheme.colorScheme.background
                            )
                        )
                )
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 16.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    Modifier
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextButton(
                        onClick = {
                            context.openUrlElseToast("https://www.buymeacoffee.com/ImShohag")
                        },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.bmc_button),
                            contentDescription = "Buy me a coffee"
                        )
                    }
                }

                HorizontalDivider(
                    Modifier
                        .width(1.dp)
                        .height(24.dp)
                )

                Column(
                    Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            context.openUrlElseToast(
                                "https://github.com/ImaginativeShohag/Why-Not-Compose"
                            )
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Source Code —",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(.75f),
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        modifier = Modifier
                            .padding(horizontal = 8.dp),
                        text = "GitHub",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun ModuleButton(
    name: String,
    @DrawableRes icon: Int,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier
            .padding(0.dp)
            .fillMaxWidth()
            .shadow(
                spread = 8.dp,
                alpha = .25f,
                color = color,
                radius = 8.dp
            ),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = color
        ),
        onClick = onClick,
        contentPadding = PaddingValues(8.dp),
        elevation = null
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(id = icon),
                contentDescription = name,
                tint = LocalContentColor.current
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = name,
                color = LocalContentColor.current,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

data class MenuItem(
    val name: String,
    @DrawableRes val icon: Int,
    val color: Color,
    val route: Screen
)

@PreviewLightDark
@Composable
private fun HomeIndexScreenPreview() {
    AppTheme {
        HomeIndexScreen()
    }
}
