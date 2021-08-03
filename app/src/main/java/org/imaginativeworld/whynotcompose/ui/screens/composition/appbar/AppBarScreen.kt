package org.imaginativeworld.whynotcompose.ui.screens.composition.appbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.imaginativeworld.whynotcompose.ui.screens.AppComponent
import org.imaginativeworld.whynotcompose.ui.screens.AppComponent.Header
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme

@Composable
fun AppBarScreen() {
    AppBarScreenSkeleton()
}


@Preview
@Composable
fun AppBarScreenSkeletonPreview() {
    AppTheme {
        AppBarScreenSkeleton()
    }
}

@Composable
fun AppBarScreenSkeleton() {

    Scaffold {
        Column(Modifier.fillMaxSize()) {

            Header("App Bar")

            // ----------------------------------------------------------------

            TopAppBar(
                title = { Text("Simple TopAppBar") },
                navigationIcon = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
                    }
                },
                actions = {
                    // RowScope here, so these icons will be placed horizontally
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
                    }
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
                    }
                }
            )

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            TopAppBar {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Flexible TopAppBar",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                )
            }

            // ----------------------------------------------------------------

            AppComponent.MediumSpacer()

            TopAppBar(
                modifier = Modifier.clip(RoundedCornerShape(0.dp, 0.dp, 24.dp, 24.dp)),
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Custom TopAppBar",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                )
            }
        }
    }

}