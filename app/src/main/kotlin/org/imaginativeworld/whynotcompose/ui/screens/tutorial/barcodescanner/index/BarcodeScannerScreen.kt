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

package org.imaginativeworld.whynotcompose.ui.screens.tutorial.barcodescanner.index

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.barcodescanner.custombarcodescanner.CustomBarcodeScannerSheet

@Composable
fun BarcodeScannerScreen(
    viewModel: BarcodeScannerViewModel,
    goBack: () -> Unit
) {
    var showCustomCodeScanner by remember { mutableStateOf(false) }

    val scannedCode = viewModel.scannedCode.collectAsState()

    BarcodeScannerScreenSkeleton(
        scannedCode = scannedCode.value,
        goBack = goBack,
        onOpenGoogleCodeScannerClick = {
            viewModel.openGoogleBarcodeScanner()
        },
        onOpenCustomCodeScannerClick = {
            showCustomCodeScanner = true
        }
    )

    // ----------------------------------------------------------------
    // Sheets
    // ----------------------------------------------------------------

    if (showCustomCodeScanner) {
        CustomBarcodeScannerSheet(
            onDismissRequest = {
                showCustomCodeScanner = false
            },
            onSuccess = { barcode ->
                viewModel.setScannedCode(barcode)
            }
        )
    }
}

@PreviewLightDark
@Composable
private fun BarcodeScannerScreenSkeletonPreview() {
    AppTheme {
        BarcodeScannerScreenSkeleton()
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun BarcodeScannerScreenSkeleton(
    scannedCode: String? = null,
    goBack: () -> Unit = {},
    onOpenGoogleCodeScannerClick: () -> Unit = {},
    onOpenCustomCodeScannerClick: () -> Unit = {}
) {
    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            AppComponent.Header(
                "Barcode Scanner",
                goBack = goBack
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Scanned Data",
                style = MaterialTheme.typography.titleMedium
            )

            if (scannedCode.isNullOrBlank()) {
                Text("No Data")
            } else {
                Text(scannedCode)
            }

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = onOpenGoogleCodeScannerClick
            ) {
                Text("Open Google Code Scanner")
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = onOpenCustomCodeScannerClick
            ) {
                Text("Open Custom Code Scanner")
            }
        }
    }
}
