package org.imaginativeworld.whynotcompose.network

import android.content.Context
import kotlinx.coroutines.CancellationException
import org.imaginativeworld.oopsnointernet.utils.NoInternetUtils
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import timber.log.Timber
import java.net.HttpURLConnection

object SafeApiRequest {

    suspend fun <T : Any> apiRequest(context: Context, call: suspend () -> Response<T>): T {

        try {

            if (!NoInternetUtils.isConnectedToInternet(context.applicationContext)) {
                throw ApiException("No internet connection!")
            }

            val response = call.invoke()

            if (response.isSuccessful && response.code() == HttpURLConnection.HTTP_OK) {

                return response.body()!!
            } else {

                val error = response.errorBody()?.string()

                val message = StringBuilder()

                error?.let {
                    try {
                        message.append(JSONObject(it).getString("message"))
                    } catch (e: JSONException) {
                    }
                }

                if (message.isNotEmpty()) {
                    message.append("\n")
                }

                message.append("Something went wrong! ${response.message()} (${response.code()})")

                Timber.e("SafeApiRequest: ApiException: $message")

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
