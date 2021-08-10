package org.imaginativeworld.whynotcompose.template

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import org.imaginativeworld.whynotcompose.ui.screens.AppComponent
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme

@Composable
fun TemplateChildScreen() {
    TemplateChildScreenSkeleton()
}

@Preview
@Composable
fun TemplateChildScreenSkeletonPreview() {
    AppTheme {
        TemplateChildScreenSkeleton()
    }
}

@Preview
@Composable
fun TemplateChildScreenSkeletonPreviewDark() {
    AppTheme(
        darkTheme = true
    ) {
        TemplateChildScreenSkeleton()
    }
}

@Composable
fun TemplateChildScreenSkeleton() {
    Scaffold(
        Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 16.dp, end = 16.dp)
        ) {
            AppComponent.Header("Title")

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

            AppComponent.SubHeader("Sub Title")

            // ----------------------------------------------------------------

            // Content here...

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            // Content here...

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.BigSpacer()

        }
    }
}