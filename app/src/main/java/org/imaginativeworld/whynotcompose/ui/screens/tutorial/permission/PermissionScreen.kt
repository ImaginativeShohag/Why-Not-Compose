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

package org.imaginativeworld.whynotcompose.ui.screens.tutorial.permission

import android.Manifest
import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.base.extensions.toast
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

// Note: Clear Storage will clear the permissions.

@Composable
fun PermissionScreen(
    goBack: () -> Unit
) {
    val context = LocalContext.current

    val requestSinglePermission =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->
            if (permissionGranted) {
                context.toast("Single permission is granted.")
            } else {
                context.toast("Single permission is denied.")
            }
        }

    val requestMultiplePermission =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionsStatus ->
            permissionsStatus.forEach { permissionStatus ->
                if (permissionStatus.value) {
                    context.toast("${permissionStatus.key} permission is granted.")
                } else {
                    context.toast("${permissionStatus.key} permission is denied.")
                }
            }
        }

    PermissionScreenSkeleton(
        goBack = goBack,
        onSinglePermissionClicked = {
            requestSinglePermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        },
        onMultiplePermissionsClicked = {
            requestMultiplePermission.launch(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        }
    )
}

@Preview
@Composable
fun PermissionScreenSkeletonPreview() {
    AppTheme {
        PermissionScreenSkeleton()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PermissionScreenSkeletonPreviewDark() {
    AppTheme {
        PermissionScreenSkeleton()
    }
}

@Composable
fun PermissionScreenSkeleton(
    goBack: () -> Unit = {},
    onSinglePermissionClicked: () -> Unit = {},
    onMultiplePermissionsClicked: () -> Unit = {}
) {
    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding()
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AppComponent.Header(
                "Permission",
                goBack = goBack
            )

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

            // ----------------------------------------------------------------

            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 32.dp),
                onClick = {
                    onSinglePermissionClicked()
                }
            ) {
                Text("Request Single Permission")
            }

            // ----------------------------------------------------------------

            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 32.dp),
                onClick = {
                    onMultiplePermissionsClicked()
                }
            ) {
                Text("Request Multiple Permissions")
            }

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.BigSpacer()
        }
    }
}
