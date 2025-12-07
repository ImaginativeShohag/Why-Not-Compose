package com.example.store.ui.compositions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.BrightnessAuto
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.store.theme.StoreAppTheme
import org.imaginativeworld.whynotcompose.base.models.UIThemeMode
import org.imaginativeworld.whynotcompose.base.utils.UIThemeController

@Composable
fun StoreAppBar(
    modifier: Modifier = Modifier,
    title: String = "Store Overflow",
    goBack: (() -> Unit)? = null,
    toggleUIMode: () -> Unit = {}
) {
    val uiThemeMode by UIThemeController.uiThemeMode.collectAsState()

    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            if (goBack != null) {
                IconButton(onClick = {
                    goBack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Go back"
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = {
                toggleUIMode()
            }) {
                Icon(
                    imageVector = when (uiThemeMode) {
                        UIThemeMode.AUTO -> Icons.Rounded.BrightnessAuto
                        UIThemeMode.LIGHT -> Icons.Rounded.LightMode
                        UIThemeMode.DARK -> Icons.Rounded.DarkMode
                    },
                    contentDescription = ""
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    )
}

@PreviewLightDark
@Composable
private fun StoreAppBarPreview() {
    StoreAppTheme {
        StoreAppBar()
    }
}
