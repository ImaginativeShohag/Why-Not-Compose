package org.imaginativeworld.whynotcompose.base.extensions

import android.app.Activity
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController

/**
 * Attempts to pop the controller's back stack.
 * If the back stack is empty, it will finish the activity.
 *
 * @param context Activity context.
 */
fun NavController.popBackStackOrFinish(context: Context) {
    if (!popBackStack()) {
        (context as Activity).finish()
    }
}

/**
 * Attempts to pop the controller's back stack.
 * It will check the current lifecycle and only allow the pop
 * if the current state is RESUMED.
 *
 * See [reference](https://github.com/google/accompanist/issues/1408#issuecomment-1673011548)
 */
fun NavController.popBackStackOrIgnore() {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        popBackStack()
    }
}
