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

package org.imaginativeworld.whynotcompose.base.utils

object Constants {
    const val APPLICATION_ID = "org.imaginativeworld.whynotcompose"

    /**
     * Server endpoint without end slash.
     */
    const val SERVER_ENDPOINT = "https://imaginativeworld.org"
    const val CMS_SERVER_ENDPOINT = "https://gorest.co.in/public"

    /**
     * For MyNotificationOpenedHandler
     */
    const val INTENT_EXTRA_TARGET_KEY = "target"
    const val INTENT_EXTRA_TARGET_VAL_NOTIFICATIONS = "notifications"

    /**
     * For Broadcast
     */
    const val BROADCAST_ACTION_NOTIFICATIONS = "$APPLICATION_ID.notifications"
}
