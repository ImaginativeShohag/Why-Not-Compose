package org.imaginativeworld.whynotcompose.ui.screens.composition.scaffold

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme


// Source: https://cs.android.com/androidx/platform/tools/dokka-devsite-plugin/+/master:testData/compose/samples/material/samples/ScaffoldSamples.kt

@Composable
fun ScaffoldWithCustomSnackbarScreen() {
    ScaffoldWithCustomSnackbarScreenSkeleton()
}

@Preview
@Composable
fun ScaffoldWithCustomSnackbarScreenSkeletonPreview() {
    AppTheme {
        ScaffoldWithCustomSnackbarScreenSkeleton()
    }
}

@Preview
@Composable
fun ScaffoldWithCustomSnackbarScreenSkeletonPreviewDark() {
    AppTheme(
        darkTheme = true
    ) {
        ScaffoldWithCustomSnackbarScreenSkeleton()
    }
}

@Composable
fun ScaffoldWithCustomSnackbarScreenSkeleton() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding(),
        scaffoldState = scaffoldState,
        snackbarHost = {
            // reuse default SnackbarHost to have default animation and timing handling
            SnackbarHost(it) { data ->
                // custom snackbar with the custom border
                Snackbar(
                    modifier = Modifier.border(2.dp, MaterialTheme.colors.secondary),
                    snackbarData = data
                )
            }
        },
        floatingActionButton = {
            var clickCount by remember { mutableStateOf(0) }
            ExtendedFloatingActionButton(
                text = { Text("Show snackbar") },
                onClick = {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Snackbar # ${++clickCount}")
                    }
                }
            )
        },
        content = { innerPadding ->
            Text(
                text = "Custom Snackbar Demo",
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .wrapContentSize()
            )
        }
    )
}

//@Composable
//fun TemplateChildScreen() {
//    TemplateChildScreenSkeleton()
//}
//
//@Preview
//@Composable
//fun TemplateChildScreenSkeletonPreview() {
//    AppTheme {
//        TemplateChildScreenSkeleton()
//    }
//}
//
//@Preview
//@Composable
//fun TemplateChildScreenSkeletonPreviewDark() {
//    AppTheme(
//        darkTheme = true
//    ) {
//        TemplateChildScreenSkeleton()
//    }
//}
//
//@Composable
//fun TemplateChildScreenSkeleton() {
//    Scaffold(
//        Modifier
//            .navigationBarsWithImePadding()
//            .statusBarsPadding()
//    ) {
//        Column(
//            Modifier
//                .fillMaxSize()
//                .verticalScroll(rememberScrollState())
//                .padding(start = 16.dp, end = 16.dp)
//        ) {
//            AppComponent.Header("Title")
//
//            // ----------------------------------------------------------------
//            // ----------------------------------------------------------------
//
//            Divider()
//
//            AppComponent.SubHeader("Sub Title")
//
//            // ----------------------------------------------------------------
//
//            // Content here...
//
//            // ----------------------------------------------------------------
//
//            AppComponent.MediumSpacer()
//
//            // Content here...
//
//            // ----------------------------------------------------------------
//            // ----------------------------------------------------------------
//
//            AppComponent.BigSpacer()
//
//        }
//    }
//}