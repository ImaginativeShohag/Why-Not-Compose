package org.imaginativeworld.whynotcompose.benchmarks

import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import java.io.ByteArrayOutputStream

fun UiDevice.flingElementDownUp(element: UiObject2) {
    // Set some margin from the sides to prevent triggering system navigation
    element.setGestureMargin(displayWidth / 5)

    element.fling(Direction.DOWN)
    waitForIdle()
    element.fling(Direction.UP)
}

/**
 * Waits until an object with [selector] if visible on screen and returns the object.
 * If the element is not available in [timeout], throws [AssertionError]
 */
fun UiDevice.waitAndFindObject(selector: BySelector, timeout: Long): UiObject2 {
    if (!wait(Until.hasObject(selector), timeout)) {
        throw AssertionError("Element not found on screen in ${timeout}ms (selector=$selector)")
    }

    return findObject(selector)
}

fun UiDevice.waitAndFindObjects(selector: BySelector, timeout: Long): List<UiObject2> {
    if (!wait(Until.hasObject(selector), timeout)) {
        throw AssertionError("Element not found on screen in ${timeout}ms (selector=$selector)")
    }

    return findObjects(selector)
}

/**
 * Helper to press back and wait for idle.
 */
fun UiDevice.pressBackAndWaitForIdle() {
    pressBack()
    waitForIdle()
}

fun UiDevice.clickAndWaitForIdle(uiObject: UiObject2) {
    uiObject.click()
    waitForIdle()
}

/**
 * Helper to dump window hierarchy into a string.
 */
fun UiDevice.dumpWindowHierarchy(): String {
    val buffer = ByteArrayOutputStream()
    dumpWindowHierarchy(buffer)
    return buffer.toString()
}
