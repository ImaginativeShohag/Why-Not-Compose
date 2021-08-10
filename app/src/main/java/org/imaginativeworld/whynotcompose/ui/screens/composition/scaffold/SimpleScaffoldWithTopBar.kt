package org.imaginativeworld.whynotcompose.ui.screens.composition.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme

// Source: https://cs.android.com/androidx/platform/tools/dokka-devsite-plugin/+/master:testData/compose/samples/material/samples/ScaffoldSamples.kt

private val colors = listOf(
    Color(0xFFffd7d7.toInt()),
    Color(0xFFffe9d6.toInt()),
    Color(0xFFfffbd0.toInt()),
    Color(0xFFe3ffd9.toInt()),
    Color(0xFFd0fff8.toInt())
)

@Composable
fun SimpleScaffoldWithTopBarScreen() {
    SimpleScaffoldWithTopBarScreenSkeleton()
}

@Preview
@Composable
fun SimpleScaffoldWithTopBarScreenSkeletonPreview() {
    AppTheme {
        SimpleScaffoldWithTopBarScreenSkeleton()
    }
}

@Preview
@Composable
fun SimpleScaffoldWithTopBarScreenSkeletonPreviewDark() {
    AppTheme(
        darkTheme = true
    ) {
        SimpleScaffoldWithTopBarScreenSkeleton()
    }
}

@Composable
fun SimpleScaffoldWithTopBarScreenSkeleton() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier
            .navigationBarsWithImePadding()
            .statusBarsPadding(),
        scaffoldState = scaffoldState,
        drawerContent = { Text("Drawer content") },
        topBar = {
            TopAppBar(
                title = { Text("Simple Scaffold Screen") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch { scaffoldState.drawerState.open() }
                        }
                    ) {
                        Icon(Icons.Filled.Menu, contentDescription = "Localized description")
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Inc") },
                onClick = { /* fab click handler */ }
            )
        },
        content = { innerPadding ->
            LazyColumn(contentPadding = innerPadding) {
                items(count = 100) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(colors[it % colors.size])
                    )
                }
            }
        }
    )
}

