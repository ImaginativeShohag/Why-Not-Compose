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

package org.imaginativeworld.whynotcompose.ui.screens.tutorial.baselineprofiles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.profileinstaller.ProfileVerifier
import androidx.profileinstaller.ProfileVerifier.CompilationStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.launch

class BaselineProfilesViewModel : ViewModel() {
    private val _profileInstallStatus = MutableStateFlow("Loading...")
    val profileInstallStatus = _profileInstallStatus.asStateFlow()

    init {
        logCompilationStatus()
    }

    /**
     * Logs the app's Baseline Profile Compilation Status using [ProfileVerifier].
     *
     * When delivering through Google Play, the baseline profile is compiled during installation.
     * In this case you will see the correct state logged without any further action necessary.
     * To verify baseline profile installation locally, you need to manually trigger baseline
     * profile installation.
     *
     * For immediate compilation, call:
     * ```bash
     * adb shell cmd package compile -f -m speed-profile com.example.macrobenchmark.target
     * ```
     * You can also trigger background optimizations:
     * ```bash
     * adb shell pm bg-dexopt-job
     * ```
     * Both jobs run asynchronously and might take some time complete.
     *
     * To see quick turnaround of the ProfileVerifier, we recommend using `speed-profile`.
     * If you don't do either of these steps, you might only see the profile status reported as
     * "enqueued for compilation" when running the sample locally.
     *
     * @see androidx.profileinstaller.ProfileVerifier.CompilationStatus.ResultCode
     */
    private fun logCompilationStatus() = viewModelScope.launch(Dispatchers.IO) {
        val status = ProfileVerifier.getCompilationStatusAsync().await()

        when (status.profileInstallResultCode) {
            CompilationStatus.RESULT_CODE_NO_PROFILE_INSTALLED ->
                _profileInstallStatus.value = "Baseline Profile not found"

            CompilationStatus.RESULT_CODE_COMPILED_WITH_PROFILE ->
                _profileInstallStatus.value = "Compiled with profile"

            CompilationStatus.RESULT_CODE_PROFILE_ENQUEUED_FOR_COMPILATION ->
                _profileInstallStatus.value = "Enqueued for compilation"

            CompilationStatus.RESULT_CODE_COMPILED_WITH_PROFILE_NON_MATCHING ->
                _profileInstallStatus.value = "App was installed through Play store"

            CompilationStatus.RESULT_CODE_ERROR_PACKAGE_NAME_DOES_NOT_EXIST ->
                _profileInstallStatus.value = "PackageName not found"

            CompilationStatus.RESULT_CODE_ERROR_CACHE_FILE_EXISTS_BUT_CANNOT_BE_READ ->
                _profileInstallStatus.value = "Cache file exists but cannot be read"

            CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE ->
                _profileInstallStatus.value = "Can't write cache file"

            CompilationStatus.RESULT_CODE_ERROR_UNSUPPORTED_API_VERSION ->
                _profileInstallStatus.value = "Enqueued for compilation"

            else ->
                _profileInstallStatus.value = "Profile not compiled or enqueued"
        }
    }
}
