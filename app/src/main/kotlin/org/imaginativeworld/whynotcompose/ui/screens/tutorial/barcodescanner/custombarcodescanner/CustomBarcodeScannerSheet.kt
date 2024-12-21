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

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture.FLASH_MODE_OFF
import androidx.camera.core.ImageCapture.FLASH_MODE_ON
import androidx.camera.view.LifecycleCameraController
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.base.extensions.toast
import org.imaginativeworld.whynotcompose.base.models.Event
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

// todo#1: add accompanist permission and add permission uis
// todo#2: add no camera ui

@Composable
fun CustomBarcodeScannerSheet(
    onDismissRequest: () -> Unit,
    onSuccess: (barcode: Barcode) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val currentOnSuccess by rememberUpdatedState(onSuccess)
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val goBack: () -> Unit = {
        scope.launch {
            bottomSheetState.hide()
            onDismissRequest()
        }
    }

    val cameraController = remember {
        LifecycleCameraController(context).apply {
            // Bind the LifecycleCameraController to the lifecycleOwner
            bindToLifecycle(lifecycleOwner)
        }
    }

    val requestSinglePermission =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->
            if (permissionGranted) {
                context.toast("Single permission is granted.")
            } else {
                context.toast("Single permission is denied.")
            }
        }

    LaunchedEffect(Unit) {
        requestSinglePermission.launch(Manifest.permission.CAMERA)
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = bottomSheetState
    ) {
        CustomBarcodeScannerScreenSkeleton(
            message = null,
            isFlashAvailable = cameraController.cameraInfo?.hasFlashUnit() == true,
            isFlashOn = cameraController.imageCaptureFlashMode == FLASH_MODE_ON,
            onFlashToggleClick = {
                if (cameraController.imageCaptureFlashMode == FLASH_MODE_ON) {
                    cameraController.imageCaptureFlashMode = FLASH_MODE_OFF
                } else {
                    cameraController.imageCaptureFlashMode = FLASH_MODE_ON
                }
            },
            cameraContent = {
                CameraPreviewView(
                    onSuccess = { barcodes ->
                        barcodes.firstOrNull()?.let {
                            goBack()
                            currentOnSuccess(it)
                        }
                    }
                )
            }
        )
    }
}

@PreviewLightDark
@Composable
private fun CustomBarcodeScannerScreenSkeletonPreview() {
    AppTheme {
        CustomBarcodeScannerScreenSkeleton(
            message = null,
            isFlashAvailable = true,
            isFlashOn = true,
            onFlashToggleClick = {},
            cameraContent = {}
        )
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun CustomBarcodeScannerScreenSkeleton(
    message: Event<String>?,
    isFlashAvailable: Boolean,
    isFlashOn: Boolean,
    onFlashToggleClick: () -> Unit,
    cameraContent: @Composable () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(message) {
        message?.value?.let { message ->
            scope.launch {
                snackbarHostState.showSnackbar(message)
            }
        }
    }

    CodeScannerView(
        isFlashAvailable = isFlashAvailable,
        isFlashOn = isFlashOn,
        onFlashToggleClick = onFlashToggleClick
    ) {
        cameraContent()
    }
}
