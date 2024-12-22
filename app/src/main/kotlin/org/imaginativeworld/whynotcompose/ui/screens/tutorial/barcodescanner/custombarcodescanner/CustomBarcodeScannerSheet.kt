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
import android.content.pm.PackageManager
import androidx.camera.core.TorchState
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotcompose.base.models.Event
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.barcodescanner.custombarcodescanner.components.CameraPreviewView
import org.imaginativeworld.whynotcompose.ui.screens.tutorial.barcodescanner.custombarcodescanner.components.CodeScannerView

@OptIn(ExperimentalPermissionsApi::class)
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
    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.CAMERA
    )
    val hasAnyCamera = remember {
        context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY) == true
    }

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

    val torchState = cameraController.torchState.observeAsState()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = bottomSheetState
    ) {
        if (!hasAnyCamera) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Camera not available!")
            }
        } else if (cameraPermissionState.status.isGranted) {
            CustomBarcodeScannerScreenSkeleton(
                message = null,
                isFlashAvailable = true,
                isFlashOn = torchState.value == TorchState.ON,
                onFlashToggleClick = {
                    if (torchState.value == TorchState.ON) {
                        cameraController.enableTorch(false)
                    } else {
                        cameraController.enableTorch(true)
                    }
                },
                cameraContent = {
                    CameraPreviewView(
                        cameraController = cameraController,
                        onSuccess = { barcodes ->
                            barcodes.firstOrNull()?.let {
                                goBack()
                                currentOnSuccess(it)
                            }
                        }
                    )
                }
            )
        } else {
            val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
                // If the user has denied the permission but the rationale can be shown,
                // then gently explain why the app requires this permission
                "The camera is needed to use the scanner. Please grant the permission."
            } else {
                // If it's the first time the user lands on this feature, or the user
                // doesn't want to be asked again for this permission, explain that the
                // permission is required
                "Camera permission required for this feature to be available. " +
                    "Please grant the permission"
            }

            RequestPermissionSection(
                message = textToShow,
                onRequestPermissionClick = {
                    cameraPermissionState.launchPermissionRequest()
                }
            )
        }
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
            cameraContent = {},
            modifier = Modifier
                .background(BottomSheetDefaults.ContainerColor)
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
    cameraContent: @Composable () -> Unit,
    modifier: Modifier = Modifier
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
        modifier = modifier,
        isFlashAvailable = isFlashAvailable,
        isFlashOn = isFlashOn,
        onFlashToggleClick = onFlashToggleClick
    ) {
        cameraContent()
    }
}

@PreviewLightDark
@Composable
private fun RequestPermissionSectionPreview() {
    AppTheme {
        RequestPermissionSection(
            message = "The camera is needed to use the scanner. Please grant the permission.",
            onRequestPermissionClick = {},
            modifier = Modifier
                .fillMaxSize()
                .background(BottomSheetDefaults.ContainerColor)
        )
    }
}

@Composable
private fun RequestPermissionSection(
    message: String,
    onRequestPermissionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                message,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(16.dp))

            Button(onClick = onRequestPermissionClick) {
                Text("Request permission")
            }
        }
    }
}
