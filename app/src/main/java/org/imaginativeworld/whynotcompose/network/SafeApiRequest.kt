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

package org.imaginativeworld.whynotcompose.network

import android.content.Context
import java.net.HttpURLConnection
import kotlinx.coroutines.CancellationException
import org.imaginativeworld.whynotcompose.utils.Utils
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import timber.log.Timber

object SafeApiRequest {
    suspend fun <T : Any?> apiRequest(
        context: Context,
        call: suspend () -> Response<T>
    ): T? {
        try {
            if (!Utils.isConnectedToInternet(context.applicationContext)) {
                throw ApiException("No internet connection!")
            }

            val response = call.invoke()

            if (response.isSuccessful &&
                response.code() >= HttpURLConnection.HTTP_OK &&
                response.code() < HttpURLConnection.HTTP_MULT_CHOICE
            ) {
                return response.body()
            } else {
                val error = response.errorBody()?.string()

                val message = StringBuilder()

                error?.let {
                    try {
                        message.append(JSONObject(it).getString("message"))
                    } catch (e: JSONException) {
                        /* no-op */
                    }
                }

                if (message.isNotEmpty()) {
                    message.append("\n")
                }

                message.append("Something went wrong! ${response.message()} (${response.code()})")

                Timber.e("ApiException: $message")

                throw ApiException(message.toString())
            }
        } catch (e: CancellationException) {
            e.printStackTrace()

            throw e
        } catch (e: Exception) {
            e.printStackTrace()

            throw ApiException(e.message ?: "Unknown Error!")
        }
    }
}
