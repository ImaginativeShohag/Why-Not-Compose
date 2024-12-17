package org.imaginativeworld.whynotcompose.benchmarks

import android.Manifest.permission
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import android.util.Log
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By

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

fun MacrobenchmarkScope.commonModuleTraverseActions(
    moduleButtonText: String,
    screenTag: String
) {
    val moduleButton = device.findObject(By.text(moduleButtonText))
    device.clickAndWaitForIdle(moduleButton)

    val items = device.waitAndFindObjects(By.res("list-item"), 5_000)

    Log.d("BaselineProfileGenerator", "hierarchy: ${device.dumpWindowHierarchy()}")

    val count = items.count()

    for (i in 0..<count) {
        device.waitAndFindObject(By.res(screenTag), 5_000)
        val items = device.waitAndFindObjects(By.res("list-item"), 5_000)
        device.clickAndWaitForIdle(items[i])

        Thread.sleep(2_000)

        device.pressBackAndWaitForIdle()
    }
}
