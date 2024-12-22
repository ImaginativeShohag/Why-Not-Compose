package org.imaginativeworld.whynotcompose.ui.screens.tutorial.barcodescanner.custombarcodescanner.components

import android.os.Handler
import android.os.Looper
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.ZoomSuggestionOptions
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import timber.log.Timber

class BarcodeAnalyser(
    private val controller: LifecycleCameraController,
    private val onBarcodeDetected: (barcodes: List<Barcode>) -> Unit
) : ImageAnalysis.Analyzer {

    val barcodeScanner: BarcodeScanner

    init {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .setZoomSuggestionOptions(
                ZoomSuggestionOptions.Builder { zoomRatio ->
                    Handler(Looper.getMainLooper()).post {
                        Timber.d("Zoom suggestions ratio: $zoomRatio (Max: ${controller.cameraInfo?.zoomState?.value?.maxZoomRatio})")
                        controller.setZoomRatio(zoomRatio)
                    }
                    true
                }
                    .setMaxSupportedZoomRatio(controller.cameraInfo?.zoomState?.value?.maxZoomRatio ?: 1f)
                    .build()
            )
            .build()

        barcodeScanner = BarcodeScanning.getClient(options)
    }

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image

        if (mediaImage != null) {
            val imageToProcess = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            barcodeScanner.process(imageToProcess)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.isNotEmpty()) {
                        onBarcodeDetected(barcodes)
                    } else {
                        Timber.d("No barcode Scanned")
                    }
                }
                .addOnFailureListener { exception ->
                    Timber.d("Scan failure")
                    Timber.e(exception)
                }
                .addOnCompleteListener {
                    Timber.d("Scan task completed")
                    imageProxy.close()
                }
        } else {
            Timber.d("Image proxy has no image")
            imageProxy.close()
        }
    }
}
