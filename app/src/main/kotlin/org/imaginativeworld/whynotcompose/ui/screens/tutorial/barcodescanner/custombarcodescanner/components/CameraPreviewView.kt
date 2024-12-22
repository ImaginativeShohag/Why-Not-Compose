package org.imaginativeworld.whynotcompose.ui.screens.tutorial.barcodescanner.custombarcodescanner.components

import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.common.Barcode
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.barcodescanner.custombarcodescanner.components.BarcodeAnalyser
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import timber.log.Timber

@Composable
fun CameraPreviewView(
    cameraController: LifecycleCameraController,
    onSuccess: (barcodes: List<Barcode>) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

//    val cameraController = remember {
//        LifecycleCameraController(context).apply {
//            // Bind the LifecycleCameraController to the lifecycleOwner
//            bindToLifecycle(lifecycleOwner)
//        }
//    }
    var cameraProvider: ProcessCameraProvider? = remember { null }

    val stopCamera = {
        cameraController.unbind()
        cameraProvider?.unbindAll()
    }

    AndroidView(
        factory = { androidViewContext ->
            val previewView = PreviewView(androidViewContext).apply {
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
                val preview = Preview.Builder().build().also {
                    it.surfaceProvider = previewView.surfaceProvider
                }

                cameraProvider = cameraProviderFuture.get()
                val barcodeAnalyser = BarcodeAnalyser(
                    controller = cameraController
                ) { barcodes ->
                    stopCamera()

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
            Timber.d("Released...")
            stopCamera()
        }
    )
}
