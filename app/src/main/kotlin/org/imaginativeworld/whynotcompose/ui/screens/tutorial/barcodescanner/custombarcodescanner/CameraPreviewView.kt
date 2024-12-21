package org.imaginativeworld.whynotcompose.ui.screens.tutorial.barcodescanner.custombarcodescanner

import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FlashOff
import androidx.compose.material.icons.rounded.FlashOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.common.Barcode
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import org.imaginativeworld.whynotcompose.R
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import timber.log.Timber

@Composable
fun CameraPreviewView(
    onSuccess: (barcodes: List<Barcode>) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraController = remember {
        LifecycleCameraController(context).apply {
            // Bind the LifecycleCameraController to the lifecycleOwner
            bindToLifecycle(lifecycleOwner)
        }
    }

    AndroidView(
        factory = { androidViewContext ->
            val previewView = PreviewView(androidViewContext).apply {
                setBackgroundColor(android.graphics.Color.GREEN)
                this.scaleType = PreviewView.ScaleType.FILL_CENTER
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                controller = cameraController
            }

            // ----------------------------------------------------------------

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
            val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> = ProcessCameraProvider.getInstance(context)

            cameraProviderFuture.addListener({
                val preview = Preview.Builder()
                    .build()
                    .also {
                        it.surfaceProvider = previewView.surfaceProvider
                    }

                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                val barcodeAnalyser = BarcodeAnalyser(
                    controller = cameraController
                ) { barcodes ->
                    onSuccess(barcodes)
                }
                val imageAnalysis: ImageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutor, barcodeAnalyser)
                    }

                try {
                    // Unbind use cases before rebinding
                    cameraProvider.unbindAll()

                    // Bind use cases to camera
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalysis
                    )
                } catch (e: Exception) {
                    Timber.d("CameraPreview: ${e.localizedMessage}")
                }
            }, ContextCompat.getMainExecutor(context))

            // ----------------------------------------------------------------

            previewView
        },
        modifier = modifier,
        onRelease = {
            cameraController.unbind()
        }
    )
}

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
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    if (isFlashOn) {
                        Icons.Rounded.FlashOn
                    } else {
                        Icons.Rounded.FlashOff
                    },
                    contentDescription = "Toggle Flash"
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
            isFlashAvailable = true,
            isFlashOn = true,
            onFlashToggleClick = {}
        ) {
            // camera preview view
        }
    }
}
