package com.example.store.ui.screen.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.store.theme.StoreAppTheme
import kotlinx.coroutines.delay

@Composable
fun StoreSplashScreen(
    gotoHomeIndex: () -> Unit = {}
) {
    LaunchedEffect(gotoHomeIndex) {
        delay(1000)

        gotoHomeIndex()
    }

    StoreSplashScreenSkeleton()
}

@PreviewLightDark
@Composable
private fun SplashScreenSkeletonPreview() {
    StoreAppTheme {
        StoreSplashScreenSkeleton()
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun StoreSplashScreenSkeleton() {
    Scaffold { innerPadding ->
        Box(
            Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Store",
                style = MaterialTheme.typography.displayLarge
            )
        }
    }
}
