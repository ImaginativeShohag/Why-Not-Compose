package org.imaginativeworld.whynotcompose.ui.compositions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.ui.theme.AppFont

@Composable
fun CustomAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onBackClicked: () -> Unit = {},
) {

    Box(
        modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White)
    ) {
        DefaultIconBorderLessButton(
            modifier = Modifier,
            size = 56.dp,
            padding = 0.dp,
            iconDrawableId = R.drawable.ic_arrow_left,
            color = MaterialTheme.colors.onBackground,
            shape = CircleShape,
        ) {
            onBackClicked()
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            text = title,
            textAlign = TextAlign.Center,
            fontFamily = AppFont.TitilliumWeb,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}