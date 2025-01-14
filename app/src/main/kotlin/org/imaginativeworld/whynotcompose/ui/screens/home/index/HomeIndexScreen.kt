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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
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
import dev.chrisbanes.haze.ExperimentalHazeApi
import dev.chrisbanes.haze.HazeInputScale
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import org.imaginativeworld.whynotcompose.BuildConfig
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.base.extensions.openUrlElseToast
import org.imaginativeworld.whynotcompose.base.extensions.shadow
import org.imaginativeworld.whynotcompose.base.extensions.toast
import org.imaginativeworld.whynotcompose.base.models.UIThemeMode
import org.imaginativeworld.whynotcompose.base.models.humanReadable
import org.imaginativeworld.whynotcompose.base.models.nextMode
import org.imaginativeworld.whynotcompose.base.utils.UIThemeController
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.common.compose.theme.AppleSystemColor
import org.imaginativeworld.whynotcompose.ui.screens.MainActivity
import org.imaginativeworld.whynotcompose.ui.screens.Screen
import org.imaginativeworld.whynotcompose.ui.screens.home.index.elements.BookKeeperButton
import org.imaginativeworld.whynotcompose.ui.screens.home.index.model.MenuItem
import org.imaginativeworld.whynotcompose.base.R as BaseR

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun HomeIndexScreen(
    navigate: (Screen) -> Unit = {},
    updateUiThemeMode: (UIThemeMode) -> Unit = {}
) {
    val context = LocalContext.current

    val uiThemeMode by UIThemeController.uiThemeMode.collectAsState()
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

    HomeIndexScreenSkeleton(
        uiThemeMode = uiThemeMode,
        showNotificationPermissionRationale = showNotificationPermissionRationale,
        navigate = navigate,
        onThemeChangeClick = updateUiThemeMode,
        onRequestNotificationPermissionClick = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    )
}

@OptIn(ExperimentalHazeMaterialsApi::class, ExperimentalHazeApi::class)
@Composable
private fun HomeIndexScreenSkeleton(
    uiThemeMode: UIThemeMode,
    showNotificationPermissionRationale: Boolean,
    modifier: Modifier = Modifier,
    onThemeChangeClick: (UIThemeMode) -> Unit = {},
    navigate: (Screen) -> Unit = {},
    onRequestNotificationPermissionClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val hazeState = remember { HazeState() }
    val (darkModeState, onDarkModeStateChange) = remember { mutableStateOf(uiThemeMode) }

    Scaffold(
        modifier
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
                // ----------------------------------------------------------------
                // List Items
                // ----------------------------------------------------------------

                LazyVerticalGrid(
                    modifier = Modifier
                        .hazeSource(state = hazeState)
                        .fillMaxSize(),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(24.dp, 128.dp, 24.dp, 128.dp)
                ) {
                    item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                        if (showNotificationPermissionRationale) {
                            Card(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp)
                                    .padding(bottom = 16.dp),
                                border = BorderStroke(
                                    width = 1.dp,
                                    AppleSystemColor.Yellow
                                ),
                                colors = CardDefaults.cardColors(
                                    containerColor = AppleSystemColor.Yellow.copy(0.15f)
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
                                        text = "Notification permission is necessary for some sample.",
                                        style = MaterialTheme.typography.bodyMedium
                                    )

                                    Button(onClick = {
                                        onRequestNotificationPermissionClick()
                                    }) {
                                        Text("Allow")
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
                            color = AppleSystemColor.Green,
                            onClick = {
                                onDarkModeStateChange(darkModeState.nextMode())
                                onThemeChangeClick(darkModeState.nextMode())
                            }
                        )
                    }

                    items(MenuItem.menuItems) { menu ->
                        ModuleButton(
                            name = menu.name,
                            icon = menu.icon,
                            color = menu.color(),
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
                                    color = AppleSystemColor.Pink,
                                    radius = 8.dp
                                ),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                contentColor = AppleSystemColor.Pink
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
                                    color = AppleSystemColor.Pink,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                            }
                        }
                    }

                    item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                        Column(Modifier.fillMaxWidth()) {
                            Text(
                                "Discover My Other App",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleMedium
                            )

                            BookKeeperButton()
                        }
                    }
                }

                // ----------------------------------------------------------------
                // Top Section
                // ----------------------------------------------------------------

                Column(
                    modifier = Modifier
                        .hazeEffect(state = hazeState, style = HazeMaterials.thin(MaterialTheme.colorScheme.background)) {
                            inputScale = HazeInputScale.Auto
                            progressive = HazeProgressive.verticalGradient(
                                startIntensity = 1f,
                                endIntensity = 0f,
                                preferPerformance = true
                            )
                        }
                        .padding(top = 24.dp)
                        .fillMaxWidth(),
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
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
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
                }

                // ----------------------------------------------------------------
                // Bottom Section
                // ----------------------------------------------------------------

                Row(
                    Modifier
                        .hazeEffect(state = hazeState, style = HazeMaterials.thin(MaterialTheme.colorScheme.background)) {
                            inputScale = HazeInputScale.Auto
                            progressive = HazeProgressive.verticalGradient(
                                startIntensity = 0f,
                                endIntensity = 1f,
                                preferPerformance = true
                            )
                        }
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(top = 24.dp, bottom = 16.dp),
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
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.bmc_button),
                                contentDescription = "Buy me a coffee"
                            )
                        }
                    }

                    Spacer(Modifier.width(16.dp))

                    Column(
                        Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .hazeEffect(
                                state = hazeState,
                                style = HazeMaterials.regular(MaterialTheme.colorScheme.background)
                            ) {
                                inputScale = HazeInputScale.Auto
                            }
                            .clickable {
                                context.openUrlElseToast(
                                    "https://github.com/ImaginativeShohag/Why-Not-Compose"
                                )
                            }
                            .padding(vertical = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Source Code —",
                            fontSize = 12.sp,
                            lineHeight = 12.sp,
                            color = MaterialTheme.colorScheme.onBackground.copy(.75f),
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            modifier = Modifier,
                            text = "GitHub",
                            fontSize = 14.sp,
                            lineHeight = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
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
                alpha = .5f,
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

@PreviewLightDark
@Composable
private fun HomeIndexScreenSkeletonPreview() {
    AppTheme {
        HomeIndexScreenSkeleton(
            uiThemeMode = UIThemeMode.AUTO,
            showNotificationPermissionRationale = false
        )
    }
}

@PreviewLightDark
@Composable
private fun HomeIndexScreenSkeletonWithNotificationPermissionRationalePreview() {
    AppTheme {
        HomeIndexScreenSkeleton(
            uiThemeMode = UIThemeMode.AUTO,
            showNotificationPermissionRationale = true
        )
    }
}
