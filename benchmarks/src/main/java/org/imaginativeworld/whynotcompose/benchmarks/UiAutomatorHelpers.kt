package org.imaginativeworld.whynotcompose.benchmarks

import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.UiObject2Condition

/**
 * Condition will be satisfied if given element has specified count of children
 */
fun untilHasChildren(
    childCount: Int = 1,
    op: HasChildrenOp = HasChildrenOp.AT_LEAST
): UiObject2Condition<Boolean> = object : UiObject2Condition<Boolean>() {
    override fun apply(element: UiObject2): Boolean = when (op) {
        HasChildrenOp.AT_LEAST -> element.childCount >= childCount
        HasChildrenOp.EXACTLY -> element.childCount == childCount
        HasChildrenOp.AT_MOST -> element.childCount <= childCount
    }
}

enum class HasChildrenOp {
    AT_LEAST,
    EXACTLY,
    AT_MOST
}
