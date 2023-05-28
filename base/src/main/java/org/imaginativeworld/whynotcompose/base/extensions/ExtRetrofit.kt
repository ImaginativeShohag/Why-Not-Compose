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

package org.imaginativeworld.whynotcompose.base.extensions

import java.io.File
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun String?.toFormRequestBody() = this?.toRequestBody(MultipartBody.FORM)

/**
 * @return [MultipartBody.Part] from the file's real path or null if the path is null.
 */
fun String?.createFormDataFromPath(fieldName: String): MultipartBody.Part? {
    return this?.let { filePath ->
        val imageFile = File(filePath)

        val requestBody = imageFile.asRequestBody(filePath.toMediaTypeOrNull())

        MultipartBody.Part.createFormData(
            fieldName,
            imageFile.name,
            requestBody
        )
    }
}
