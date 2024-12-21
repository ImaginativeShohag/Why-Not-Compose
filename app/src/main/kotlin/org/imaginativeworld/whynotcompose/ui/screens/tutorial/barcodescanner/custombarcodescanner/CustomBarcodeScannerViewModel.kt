/*
 * Copyright 2021 Md. Mahmudul Hasan Shohag
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

package org.imaginativeworld.whynotcompose.ui.screens.tutorial.barcodescanner.custombarcodescanner

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.imaginativeworld.whynotcompose.base.models.Event

@HiltViewModel
class CustomBarcodeScannerViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context
) : ViewModel() {
    private val _scannedCode = MutableStateFlow<Barcode?>(null)
    val scannedCode = _scannedCode.asStateFlow()

    private val _message = MutableStateFlow<Event<String>?>(null)
    val message = _message.asStateFlow()

    // ----------------------------------------------------------------

    fun openGoogleBarcodeScanner() {
        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .enableAutoZoom()
            .build()

        val scanner = GmsBarcodeScanning.getClient(appContext, options)

        scanner.startScan()
            .addOnSuccessListener { barcode ->
                _scannedCode.value = barcode
            }
            .addOnCanceledListener {
                // Task canceled
            }
            .addOnFailureListener { e ->
                _message.value = Event(e.message ?: "Unknown error! Try again.")
            }
    }
}
