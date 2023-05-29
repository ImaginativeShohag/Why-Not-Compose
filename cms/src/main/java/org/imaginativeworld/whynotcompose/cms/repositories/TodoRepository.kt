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

package org.imaginativeworld.whynotcompose.cms.repositories

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.imaginativeworld.whynotcompose.base.network.SafeApiRequest
import org.imaginativeworld.whynotcompose.base.utils.Utils
import org.imaginativeworld.whynotcompose.cms.db.CMSDatabase
import org.imaginativeworld.whynotcompose.cms.models.todo.Todo
import org.imaginativeworld.whynotcompose.cms.models.todo.asEntity
import org.imaginativeworld.whynotcompose.cms.models.todo.asModel
import org.imaginativeworld.whynotcompose.cms.network.api.TodoApiInterface

class TodoRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: TodoApiInterface,
    private val db: CMSDatabase
) {
    suspend fun getTodos(userId: Int, page: Int) = withContext(Dispatchers.IO) {
        if (Utils.isConnectedToInternet(context.applicationContext)) {
            // Online
            val todoItems = SafeApiRequest.apiRequest(context) {
                api.getTodos(userId, page)
            }

            todoItems?.let {
                // If this is the first page, then remove all data.
                // Because it is probably reloading data from server.
                if (page == 1) {
                    db.todoDao().removeAll()
                }

                db.todoDao().insertAll(todoItems.map { it.asEntity() })
            }

            todoItems
        } else {
            // Offline
            db.todoDao().getAll().map { it.asModel() }
        }
    }

    suspend fun addTodo(userId: Int, todo: Todo) = withContext(Dispatchers.IO) {
        val newTodo = SafeApiRequest.apiRequest(context) {
            api.addTodo(userId, todo)
        }

        newTodo?.let {
            db.todoDao().insert(it.asEntity())
        }

        newTodo
    }

    suspend fun getTodo(todoId: Int) = withContext(Dispatchers.IO) {
        if (Utils.isConnectedToInternet(context.applicationContext)) {
            // Online
            val todoItem = SafeApiRequest.apiRequest(context) {
                api.getTodo(todoId)
            }

            todoItem?.let {
                db.todoDao().update(todoItem.asEntity())
            }

            todoItem
        } else {
            // Offline
            db.todoDao().getById(todoId)?.asModel()
        }
    }

    suspend fun deleteTodo(postId: Int) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.deleteTodo(postId)
        }
    }

    suspend fun updateTodo(id: Int, todo: Todo) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.updateTodo(id, todo)
        }
    }
}
