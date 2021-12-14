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

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.imaginativeworld.whynotcompose.utils.RealPathUtil
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SelectImageAndCropViewModel @Inject constructor() : ViewModel() {

    fun uploadPhoto(
        context: Context,
        imageUri: Uri
    ) = viewModelScope.launch {
        val imagePath: String = RealPathUtil.getRealPath(context, imageUri) ?: return@launch

        // `imagePath` is the real path of the image.

        // Sample code for Retrofit:

        var imagePart: MultipartBody.Part? = imagePath.let { picturePath ->
            val pictureFile = File(picturePath)
            val requestBody: RequestBody =
                pictureFile.asRequestBody(picturePath.toMediaTypeOrNull())

            MultipartBody.Part.createFormData(
                "photo",
                pictureFile.name,
                requestBody
            )
        }

        // Now use the `imagePart` in your request.

        // Example request:

        // @Multipart
        // @POST("v1/update_photo")
        // suspend fun updateProfilePhoto(
        //     @Header("Authorization") token: String?,
        //     @Part image: MultipartBody.Part?
        // ): Response<ProfileResponse>

        // Here, `imagePart` will be the `image` parameter.
    }
}
