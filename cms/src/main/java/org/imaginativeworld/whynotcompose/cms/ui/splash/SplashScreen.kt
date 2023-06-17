package org.imaginativeworld.whynotcompose.cms.ui.splash

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme

@Composable
fun SplashScreen(
    gotoHomeIndex: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        delay(1000)

        gotoHomeIndex()
    }

    SplashScreenSkeleton()
}

@Preview
@Composable
fun TemplateChildScreenSkeletonPreview() {
    CMSAppTheme {
        SplashScreenSkeleton()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SplashScreenSkeletonPreviewDark() {
    CMSAppTheme {
        SplashScreenSkeleton()
    }
}

@Composable
fun SplashScreenSkeleton() {
    Scaffold { innerPadding ->
        Box(
            Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "CMS",
                style = MaterialTheme.typography.displayLarge
            )
        }
    }
}
