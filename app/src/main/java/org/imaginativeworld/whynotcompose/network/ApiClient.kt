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

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.imaginativeworld.whynotcompose.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ApiClient {
    companion object {

        @Volatile
        private var retrofit: Retrofit? = null

        @Volatile
        private var apiInterface: ApiInterface? = null

        private fun buildClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        this.level = HttpLoggingInterceptor.Level.BODY
                    }
                )
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .build()

                    chain.proceed(request)
                }
                .build()
        }

        @Synchronized
        private fun getRetrofit(): Retrofit {
            return retrofit ?: synchronized(this) {

                Timber.e("getRetrofit(): new generated")

                val moshi = Moshi.Builder()
                    // Note: To automatically convert date string to Date object use this:
                    .add(Date::class.java, DateJsonAdapter())
                    .build()

                retrofit = Retrofit.Builder()
                    .client(buildClient())
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .baseUrl(Constants.SERVER_ENDPOINT + "/")
                    .build()

                retrofit!!
            }
        }

        @Synchronized
        fun getClient(): ApiInterface {
            return apiInterface ?: synchronized(this) {

                Timber.e("getClient(): new generated")

                apiInterface = getRetrofit().create(ApiInterface::class.java)

                apiInterface!!
            }
        }
    }

    class DateJsonAdapter : JsonAdapter<Date>() {

        private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        override fun fromJson(reader: JsonReader): Date? {
            return try {
                val dateAsString = reader.nextString()
                dateFormat.parse(dateAsString)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        override fun toJson(writer: JsonWriter, value: Date?) {
            if (value != null) {
                writer.value(dateFormat.format(value))
            }
        }
    }
}
