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

package org.imaginativeworld.whynotcompose.ui.screens.tutorial.selectimageandcrop

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.io.File
import java.util.Date
import org.imaginativeworld.whynotcompose.base.extensions.toast
import org.imaginativeworld.whynotcompose.common.compose.R as CommonR
import org.imaginativeworld.whynotcompose.common.compose.composeutils.rememberImagePainter
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme
import org.imaginativeworld.whynotcompose.utils.CropImage

/**
 * Resources:
 * - https://developer.android.com/training/data-storage/shared/documents-files
 * - https://developer.android.com/guide/topics/providers/document-provider#client
 * - https://developer.android.com/training/data-storage/shared/photopicker
 */

@Composable
fun SelectImageAndCropScreen(
    viewModel: SelectImageAndCropViewModel,
    goBack: () -> Unit
) {
    val context = LocalContext.current

    // ----------------------------------------------------------------

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val uCropLauncher = rememberLauncherForActivityResult(CropImage()) { uri ->
        imageUri = uri

        uri?.apply {
            viewModel.uploadPhoto(
                context = context,
                imageUri = uri
            )
        }
    }

    val imageSelectorLauncher1 =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                uCropLauncher.launch(
                    Pair(
                        first = uri,
                        second = Uri.fromFile(
                            File(context.cacheDir, "temp_image_file_${Date().time}")
                        )
                    )
                )
            } else {
                context.toast("No image selected!")
            }
        }

    val imageSelectorLauncher2 =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            if (uri != null) {
                uCropLauncher.launch(
                    Pair(
                        first = uri,
                        second = Uri.fromFile(
                            File(context.cacheDir, "temp_image_file_${Date().time}")
                        )
                    )
                )
            } else {
                context.toast("No image selected!")
            }
        }

    val imageSelectorLauncher3 =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                uCropLauncher.launch(
                    Pair(
                        first = uri,
                        second = Uri.fromFile(
                            File(context.cacheDir, "temp_image_file_${Date().time}")
                        )
                    )
                )
            } else {
                context.toast("No image selected!")
            }
        }

    // ----------------------------------------------------------------

    SelectImageAndCropScreenSkeleton(
        imagePath = imageUri,
        goBack = goBack,
        onChooseImage1Click = {
            imageSelectorLauncher1.launch("image/*")
        },
        onChooseImage2Click = {
            imageSelectorLauncher2.launch(arrayOf("image/*"))
        },
        onChooseImage3Click = {
            imageSelectorLauncher3.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    )
}

@Preview
@Composable
private fun SelectImageAndCropScreenSkeletonPreview() {
    AppTheme {
        SelectImageAndCropScreenSkeleton()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SelectImageAndCropScreenSkeletonPreviewDark() {
    AppTheme {
        SelectImageAndCropScreenSkeleton()
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun SelectImageAndCropScreenSkeleton(
    imagePath: Uri? = null,
    goBack: () -> Unit = {},
    onChooseImage1Click: () -> Unit = {},
    onChooseImage2Click: () -> Unit = {},
    onChooseImage3Click: () -> Unit = {}
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
                "Select Image and Crop for Upload",
                goBack = goBack
            )

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            HorizontalDivider()

            // ----------------------------------------------------------------

            Image(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 32.dp, top = 32.dp, end = 32.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp)),
                painter = rememberImagePainter(
                    data = imagePath,
                    placeholder = CommonR.drawable.default_placeholder
                ),
                contentDescription = "Image",
                contentScale = ContentScale.Crop
            )

            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 32.dp),
                onClick = {
                    onChooseImage1Click()
                }
            ) {
                Text("Choose Image (GetContent())")
            }

            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp),
                onClick = {
                    onChooseImage2Click()
                }
            ) {
                Text("Choose Image (OpenDocument())")
            }

            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp),
                onClick = {
                    onChooseImage3Click()
                }
            ) {
                Text("Choose Image (PickVisualMedia())")
            }

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            AppComponent.BigSpacer()
        }
    }
}
