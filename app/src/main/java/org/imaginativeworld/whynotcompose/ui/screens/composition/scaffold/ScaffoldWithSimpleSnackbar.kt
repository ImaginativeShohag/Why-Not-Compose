package org.imaginativeworld.whynotcompose.ui.screens.composition.scaffold

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme

// Source: https://cs.android.com/androidx/platform/tools/dokka-devsite-plugin/+/master:testData/compose/samples/material/samples/ScaffoldSamples.kt

@Composable
fun ScaffoldWithSimpleSnackbarScreen() {
    ScaffoldWithSimpleSnackbarScreenSkeleton()
}

@Preview
@Composable
fun ScaffoldWithSimpleSnackbarScreenSkeletonPreview() {
    AppTheme {
        ScaffoldWithSimpleSnackbarScreenSkeleton()
    }
}

@Preview
@Composable
fun ScaffoldWithSimpleSnackbarScreenSkeletonPreviewDark() {
    AppTheme(
        darkTheme = true
    ) {
        ScaffoldWithSimpleSnackbarScreenSkeleton()
    }
}

@Composable
fun ScaffoldWithSimpleSnackbarScreenSkeleton() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding(),
        scaffoldState = scaffoldState,
        floatingActionButton = {
            var clickCount by remember { mutableStateOf(0) }
            ExtendedFloatingActionButton(
                text = { Text("Show snackbar") },
                onClick = {
                    // show snackbar as a suspend function
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Snackbar # ${++clickCount}")
                    }
                }
            )
        },
        content = { innerPadding ->
            Text(
                text = "Body content",
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .wrapContentSize()
            )
        }
    )
}