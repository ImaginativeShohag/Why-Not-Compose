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
