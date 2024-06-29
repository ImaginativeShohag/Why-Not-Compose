package org.imaginativeworld.whynotcompose.popbackstack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import org.imaginativeworld.whynotcompose.base.extensions.popBackStackOrFinish
import org.imaginativeworld.whynotcompose.base.extensions.popBackStackOrIgnore
import org.imaginativeworld.whynotcompose.base.models.UIThemeMode
import org.imaginativeworld.whynotcompose.base.utils.UIThemeController
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

class PopBackStackActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val uiThemeMode by UIThemeController.uiThemeMode.collectAsState()
            val isSystemInDarkTheme = isSystemInDarkTheme()

            val isDarkMode by remember(isSystemInDarkTheme) {
                derivedStateOf {
                    when (uiThemeMode) {
                        UIThemeMode.AUTO -> isSystemInDarkTheme
                        UIThemeMode.LIGHT -> false
                        UIThemeMode.DARK -> true
                    }
                }
            }

            val textStyle = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Monospace)

            AppTheme(
                darkTheme = isDarkMode
            ) {
                CompositionLocalProvider(LocalTextStyle provides textStyle) {
                    NavHostContainer()
                }
            }
        }
    }
}

private sealed class NavRoute {
    @Serializable
    object Main

    @Serializable
    object Another
}

@Composable
fun NavHostContainer(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavRoute.Main
    ) {
        composable<NavRoute.Main> {
            PopBackStackMainScreen(
                goNextScreen = {
                    navController.navigate(NavRoute.Another)
                }
            )
        }

        composable<NavRoute.Another> {
            PopBackStackAnotherScreen(
                onPopBackStackClick = {
                    navController.popBackStack()
                },
                onPopBackStackOrFinishClick = {
                    navController.popBackStackOrFinish(context)
                },
                onPopBackStackOrIgnoreClick = {
                    navController.popBackStackOrIgnore()
                },
                onDropUnlessResumedClick = dropUnlessResumed {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun PopBackStackMainScreen(
    modifier: Modifier = Modifier,
    goNextScreen: () -> Unit = {}
) {
    Scaffold(
        modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding()
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "This is a new Activity. The previous activity is finished to check the method effects.",
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(16.dp))

            MonoButton("Go Next Screen", onClick = goNextScreen)
        }
    }
}

@PreviewLightDark
@Composable
private fun PopBackStackMainScreenPreview() {
    AppTheme {
        CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Monospace)) {
            PopBackStackMainScreen()
        }
    }
}

@Composable
fun PopBackStackAnotherScreen(
    modifier: Modifier = Modifier,
    onPopBackStackClick: () -> Unit = {},
    onPopBackStackOrFinishClick: () -> Unit = {},
    onPopBackStackOrIgnoreClick: () -> Unit = {},
    onDropUnlessResumedClick: () -> Unit = {}
) {
    Scaffold(
        modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding()
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Quickly click any button multiple times to see the effect of each implementation.",
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(16.dp))

            MonoButton("popBackStack()", onClick = onPopBackStackClick)

            MonoButton("popBackStackOrFinish()", onClick = onPopBackStackOrFinishClick)

            MonoButton("popBackStackOrIgnore", onClick = onPopBackStackOrIgnoreClick)

            MonoButton("dropUnlessResumed", onClick = onDropUnlessResumedClick)
        }
    }
}

@Composable
private fun MonoButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily.Monospace
            )
        )
    }
}

@PreviewLightDark
@Composable
private fun PopBackStackAnotherScreenPreview() {
    AppTheme {
        CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Monospace)) {
            PopBackStackAnotherScreen()
        }
    }
}
