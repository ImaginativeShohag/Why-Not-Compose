package org.imaginativeworld.whynotcompose.ui.screens.tutorial.barcodescanner.custombarcodescanner.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FlashOff
import androidx.compose.material.icons.rounded.FlashOn
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

@Composable
fun CodeScannerView(
    isFlashAvailable: Boolean,
    isFlashOn: Boolean,
    onFlashToggleClick: () -> Unit,
    modifier: Modifier = Modifier,
    cameraContent: @Composable () -> Unit
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.qr_code_scanning)
    )

    Box(modifier = modifier.fillMaxSize()) {
        cameraContent()

        LottieAnimation(
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.Center),
            composition = composition,
            iterations = LottieConstants.IterateForever
        )

        if (isFlashAvailable) {
            IconButton(
                onClick = onFlashToggleClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    if (isFlashOn) {
                        Icons.Rounded.FlashOn
                    } else {
                        Icons.Rounded.FlashOff
                    },
                    contentDescription = "Toggle Flash",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CameraPreviewViewPreview() {
    AppTheme {
        CodeScannerView(
            modifier = Modifier
                .background(BottomSheetDefaults.ContainerColor),
            isFlashAvailable = true,
            isFlashOn = true,
            onFlashToggleClick = {}
        ) {
            // camera preview view
        }
    }
}
