package org.imaginativeworld.whynotcompose.ui.screens.composition.scaffold

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme

// Source: https://cs.android.com/androidx/platform/tools/dokka-devsite-plugin/+/master:testData/compose/samples/material/samples/ScaffoldSamples.kt

@Composable
fun ScaffoldWithCoroutinesSnackbarScreen() {
    ScaffoldWithCoroutinesSnackbarScreenSkeleton()
}

@Preview
@Composable
fun ScaffoldWithCoroutinesSnackbarScreenSkeletonPreview() {
    AppTheme {
        ScaffoldWithCoroutinesSnackbarScreenSkeleton()
    }
}

@Preview
@Composable
fun ScaffoldWithCoroutinesSnackbarScreenSkeletonPreviewDark() {
    AppTheme(
        darkTheme = true
    ) {
        ScaffoldWithCoroutinesSnackbarScreenSkeleton()
    }
}

@Composable
fun ScaffoldWithCoroutinesSnackbarScreenSkeleton() {
    // decouple snackbar host state from scaffold state for demo purposes
    // this state, channel and flow is for demo purposes to demonstrate business logic layer
    val snackbarHostState = remember { SnackbarHostState() }
    // we allow only one snackbar to be in the queue here, hence conflated
    val channel = remember { Channel<Int>(Channel.Factory.CONFLATED) }
    LaunchedEffect(channel) {
        channel.receiveAsFlow().collect { index ->
            val result = snackbarHostState.showSnackbar(
                message = "Snackbar # $index",
                actionLabel = "Action on $index"
            )
            when (result) {
                SnackbarResult.ActionPerformed -> {
                    /* action has been performed */
                }
                SnackbarResult.Dismissed -> {
                    /* dismissed, no action needed */
                }
            }
        }
    }
    Scaffold(
        modifier = Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding(),
        // attach snackbar host state to the scaffold
        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
        floatingActionButton = {
            var clickCount by remember { mutableStateOf(0) }
            ExtendedFloatingActionButton(
                text = { Text("Show snackbar") },
                onClick = {
                    // offset snackbar data to the business logic
                    channel.trySend(++clickCount)
                }
            )
        },
        content = { innerPadding ->
            Text(
                "Snackbar demo",
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .wrapContentSize()
            )
        }
    )
}
