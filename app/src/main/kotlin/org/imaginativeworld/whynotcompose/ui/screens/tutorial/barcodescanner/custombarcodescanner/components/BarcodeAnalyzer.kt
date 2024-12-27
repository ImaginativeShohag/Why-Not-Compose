/*
 * Copyright 2024 Md. Mahmudul Hasan Shohag
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ------------------------------------------------------------------------
 *
 * Project: Why Not Compose!
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * Source: https://github.com/ImaginativeShohag/Why-Not-Compose
 */

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

class BarcodeAnalyzer(
    private val controller: LifecycleCameraController,
    private val onBarcodeDetect: (barcodes: List<Barcode>) -> Unit
) : ImageAnalysis.Analyzer {
    private val barcodeScanner: BarcodeScanner
    private val maxZoomRatio: Float = controller.cameraInfo?.zoomState?.value?.maxZoomRatio ?: 1f

    init {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .setZoomSuggestionOptions(
                ZoomSuggestionOptions.Builder { zoomRatio ->
                    Handler(Looper.getMainLooper()).post {
                        Timber.d("Zoom suggestions ratio: $zoomRatio (Max: $maxZoomRatio)")

                        controller.setZoomRatio(zoomRatio)
                    }
                    true
                }
                    .setMaxSupportedZoomRatio(maxZoomRatio)
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
                        onBarcodeDetect(barcodes)
                    } else {
                        Timber.d("No barcode found")
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
