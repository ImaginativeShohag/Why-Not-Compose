package org.imaginativeworld.whynotcompose.ui.screens.tutorial.barcodescanner.custombarcodescanner

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.ZoomSuggestionOptions
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import timber.log.Timber

@SuppressLint("UnsafeOptInUsageError")
class BarcodeAnalyser(
    private val controller: LifecycleCameraController,
    private val onBarcodeDetected: (barcodes: List<Barcode>) -> Unit
) : ImageAnalysis.Analyzer {
    override fun analyze(image: ImageProxy) {
        image.image?.let { imageToAnalyze ->
            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                .setZoomSuggestionOptions(
                    ZoomSuggestionOptions.Builder { zoomRatio ->
                        Timber.d("zoom suggestions ratio: $zoomRatio")
                        controller.setZoomRatio(zoomRatio)
                        true
                    }.build()
                )
                .build()

            val barcodeScanner = BarcodeScanning.getClient(options)
            val imageToProcess = InputImage.fromMediaImage(imageToAnalyze, image.imageInfo.rotationDegrees)

            barcodeScanner.process(imageToProcess)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.isNotEmpty()) {
                        onBarcodeDetected(barcodes)
                    } else {
                        Timber.d("No barcode Scanned")
                    }
                }
                .addOnFailureListener { exception ->
                    Timber.e(exception)
                }
                .addOnCompleteListener {
                    image.close()
                }
        } ?: image.close()
    }
}
