package org.imaginativeworld.whynotcompose.ui.screens.home.index

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.ui.screens.AppComponent
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme

@Composable
fun HomeIndexScreen(
    gotoAnimationIndex: () -> Unit = {},
    gotoCompositionIndex: () -> Unit = {},
) {

    val scroll = rememberScrollState()

    Scaffold {

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scroll),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AppComponent.Header(
                text = stringResource(id = R.string.app_name)
            )

            ModuleButton(
                icon = R.drawable.ic_round_animation_24,
                name = "Animations",
                onClick = {
                    gotoAnimationIndex()
                }
            )

            ModuleButton(
                icon = R.drawable.ic_round_grid_view_24,
                name = "Compositions",
                onClick = {
                    gotoCompositionIndex()
                }
            )
        }
    }
}

@Composable
fun ModuleButton(
    @DrawableRes icon: Int,
    name: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .padding(start = 32.dp, top = 8.dp, end = 32.dp, bottom = 8.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White
        ),
        onClick = onClick,
        contentPadding = PaddingValues(16.dp, 16.dp),
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = name,
            tint = LocalContentColor.current,
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = name,
            color = LocalContentColor.current,
        )
    }
}

@Preview
@Composable
fun HomeIndexScreenPreview() {
    AppTheme {
        HomeIndexScreen()
    }
}