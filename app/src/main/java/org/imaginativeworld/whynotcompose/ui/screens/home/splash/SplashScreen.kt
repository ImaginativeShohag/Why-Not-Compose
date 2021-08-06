package org.imaginativeworld.whynotcompose.ui.screens.home.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.ui.theme.AppTheme

@Composable
fun SplashScreen(
    gotoHomeIndex: () -> Unit = {},
) {
    LaunchedEffect(Unit) {
        delay(1000)

        gotoHomeIndex()
    }

    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier.size(128.dp),
                painter = painterResource(id = R.drawable.ic_jetpack_compose_logo),
                contentDescription = stringResource(id = R.string.app_name)
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                text = stringResource(id = R.string.app_name),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                textAlign = TextAlign.Center,
            )
        }
    }

}

@Preview
@Composable
fun SplashScreenPreview() {
    AppTheme {
        SplashScreen()
    }
}