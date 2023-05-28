/*
 * Copyright 2023 Md. Mahmudul Hasan Shohag
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

package org.imaginativeworld.whynotcompose.base.network.jsonadapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateJsonAdapter : JsonAdapter<Date>() {
    private val dateFormat =
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    override fun fromJson(reader: JsonReader): Date? {
        if (reader.peek() == JsonReader.Token.NULL) {
            reader.nextNull<Date>()
            return null
        }

        return try {
            val dateAsString = reader.nextString()
            dateFormat.parse(dateAsString)
        } catch (e: Exception) {
            e.printStackTrace()
            reader.nextNull<Date>()
            null
        }
    }

    override fun toJson(writer: JsonWriter, value: Date?) {
        if (value == null) {
            writer.nullValue()
        } else {
            writer.value(dateFormat.format(value))
        }
    }
}
