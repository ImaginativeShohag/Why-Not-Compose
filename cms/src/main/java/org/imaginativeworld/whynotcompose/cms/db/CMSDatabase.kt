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

package org.imaginativeworld.whynotcompose.cms.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Date
import org.imaginativeworld.whynotcompose.cms.models.todo.TodoEntity
import org.imaginativeworld.whynotcompose.cms.models.user.UserEntity

@Database(
    entities = [
        UserEntity::class,
        TodoEntity::class
    ],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class CMSDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun todoDao(): TodoDao

    companion object {

//        private val MIGRATION_1_2 = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL(
//                    "DROP TABLE tbl_user"
//                )
//            }
//        }

        operator fun invoke(context: Context) = buildDatabase(context)

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CMSDatabase::class.java,
            "CMSDatabase.db"
        )
//            .addMigrations(MIGRATION_1_2) // Note: Migration example
            .fallbackToDestructiveMigration() // Note: Mostly for debug
            .build()
    }
}

class DateConverter {
    @TypeConverter
    fun toDatabaseValue(date: Date?): Long? = date?.time

    @TypeConverter
    fun fromDatabaseValue(value: Long?): Date? = value?.let { Date(it) }
}
