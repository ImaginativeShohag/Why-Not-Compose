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

package org.imaginativeworld.whynotcompose.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import com.yalantis.ucrop.UCrop
import org.imaginativeworld.whynotcompose.BuildConfig
import timber.log.Timber

/**
 * This is using UCrop to crop/resize an image.
 *
 * Usage:
 * ```
 * val uCropLauncher = rememberLauncherForActivityResult(SquireCropImage()) { uri ->
 *     uri?.let {
 *         // the "uri" holds the final image
 *     }
 * }
 *
 * uCropLauncher.launch(
 *     Pair(
 *         first = uri, // <-- Uri from Camera or Gallery
 *         second = Uri.fromFile(File(context.cacheDir, "temp_image_file"))
 *     )
 * )
 * ```
 */
@Suppress("KotlinConstantConditions")
class SquireCropImage : ActivityResultContract<Pair<Uri, Uri>, Uri?>() {
    override fun createIntent(context: Context, input: Pair<Uri, Uri>): Intent =
        UCrop.of(input.first, input.second)
            .withAspectRatio(1f, 1f)
            .getIntent(context)

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        if (resultCode != Activity.RESULT_OK || intent == null) {
            return null
        } else if (BuildConfig.DEBUG && resultCode == UCrop.RESULT_ERROR) {
            UCrop.getError(intent)?.let { cropError ->
                Timber.e("cropError: $cropError")
            }
        }
        return UCrop.getOutput(intent)
    }
}
