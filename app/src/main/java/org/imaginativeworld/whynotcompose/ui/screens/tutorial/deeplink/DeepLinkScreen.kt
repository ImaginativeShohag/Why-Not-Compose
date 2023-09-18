package org.imaginativeworld.whynotcompose.ui.screens.tutorial.deeplink

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.imaginativeworld.whynotcompose.base.extensions.openUrl
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

/**
 * Resources:
 *
 * - https://developer.android.com/training/app-links
 * - https://developer.android.com/training/app-links/verify-android-applinks#user-prompt-command-line-program
 */

@Composable
fun DeepLinkScreen(
    goBack: () -> Unit
) {
    DeepLinkScreenSkeleton(
        goBack = goBack
    )
}

@Preview
@Composable
fun DeepLinkScreenSkeletonPreview() {
    AppTheme {
        DeepLinkScreenSkeleton()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DeepLinkScreenSkeletonPreviewDark() {
    AppTheme {
        DeepLinkScreenSkeleton()
    }
}

@Composable
fun DeepLinkScreenSkeleton(
    goBack: () -> Unit = {}
) {
    val context = LocalContext.current

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
        ) {
            AppComponent.Header(
                "Deep Link",
                goBack = goBack
            )

            Divider()

            Column(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Try the following deep links to open the deep link activity.",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )

                OutlinedButton(
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = {
                        context.openUrl("app://why-not-compose")
                    }
                ) {
                    Text(
                        text = "app://why-not-compose",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp
                    )
                }

                OutlinedButton(
                    modifier = Modifier.padding(top = 8.dp),
                    onClick = {
                        context.openUrl("http://imaginativeworld.org/why-not-compose")
                    }
                ) {
                    Text(
                        text = "http://imaginativeworld.org/why-not-compose",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp
                    )
                }

                OutlinedButton(
                    modifier = Modifier.padding(top = 8.dp),
                    onClick = {
                        context.openUrl("https://imaginativeworld.org/why-not-compose")
                    }
                ) {
                    Text(
                        text = "https://imaginativeworld.org/why-not-compose",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
