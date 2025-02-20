/*
 * Copyright 2024 Md. Mahmudul Hasan Shohag
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

package org.imaginativeworld.whynotcompose.benchmarks

import android.Manifest.permission
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import android.util.Log
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until

/**
 * Because the app under test is different from the one running the instrumentation test,
 * the permission has to be granted manually by either:
 *
 * - tapping the Allow button
 *    ```kotlin
 *    val obj = By.text("Allow")
 *    val dialog = device.wait(Until.findObject(obj), TIMEOUT)
 *    dialog?.let {
 *        it.click()
 *        device.wait(Until.gone(obj), 5_000)
 *    }
 *    ```
 * - or (preferred) executing the grant command on the target package.
 */
fun MacrobenchmarkScope.allowNotifications() {
    if (SDK_INT >= TIRAMISU) {
        val command = "pm grant $packageName ${permission.POST_NOTIFICATIONS}"
        device.executeShellCommand(command)
    }
}

/**
 * Wraps starting the default activity, waiting for it to start and then allowing notifications in
 * one convenient call.
 */
fun MacrobenchmarkScope.startActivityAndAllowNotifications() {
    startActivityAndWait()
    allowNotifications()
}

/**
 * Traverse all visible list items' screens in the index screen.
 */
fun MacrobenchmarkScope.indexScreenTraverseActions(
    moduleButtonText: String,
    screenTag: String
) {
    val moduleButton = device.waitAndFindObject(By.text(moduleButtonText), 5_000)
    device.clickAndWaitForIdle(moduleButton)

    val items = device.waitAndFindObjects(By.res("list-item"), 5_000)

    Log.d("BaselineProfileGenerator", "hierarchy: ${device.dumpWindowHierarchy()}")

    val count = items.count()

    for (i in 0..<count) {
        Log.d("BaselineProfileGenerator", "item index: $i")

        // Wait for the index screen to come in the screen.
        device.waitAndFindObject(By.res(screenTag), 5_000, "item index: $i")

        // Query for all list items.
        val items = device.waitAndFindObjects(By.res("list-item"), 5_000)
        // Click on the next item in the index screen.
        device.clickAndWaitForIdle(items[i])

        // Wait until the index screen is gone.
        device.wait(Until.gone(By.res(screenTag)), 5_000)

        // Try to click the back button from app bar if it is available.
        if (device.wait(Until.hasObject(By.res("nav_btn_back")), 2_000)) {
            val backButton = device.findObject(By.res("nav_btn_back"))

            device.clickAndWaitForIdle(backButton)
        } else {
            device.pressBackAndWaitForIdle()
        }
    }
}
