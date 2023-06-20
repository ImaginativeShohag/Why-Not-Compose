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

import java.util.Date
import org.imaginativeworld.whynotcompose.cms.models.Post
import org.imaginativeworld.whynotcompose.cms.models.todo.Todo
import org.imaginativeworld.whynotcompose.cms.models.user.User

object MockData {
    // ----------------------------------------------------------------
    // User
    // ----------------------------------------------------------------

    val dummyUser = User(
        id = 1,
        name = "Md. Mahmudul Hasan Shohag",
        email = "imaginativeshohag@gmail.com",
        gender = "male",
        status = "active"
    )

    val dummyUserList = listOf(
        dummyUser.copy(id = 1),
        dummyUser.copy(id = 2),
        dummyUser.copy(id = 3),
        dummyUser.copy(id = 4),
        dummyUser.copy(id = 5),
        dummyUser.copy(id = 6),
        dummyUser.copy(id = 7),
        dummyUser.copy(id = 8),
        dummyUser.copy(id = 9),
        dummyUser.copy(id = 10)
    )

    // ----------------------------------------------------------------
    // Todos
    // ----------------------------------------------------------------

    val dummyTodo = Todo(
        id = 1,
        title = "The quick brown fox jumps over the lazy dog.",
        dueOn = Date(),
        status = "Completed",
        userId = 1
    )

    val dummyTodoList = listOf(
        dummyTodo.copy(id = 1),
        dummyTodo.copy(id = 2),
        dummyTodo.copy(id = 3),
        dummyTodo.copy(id = 4),
        dummyTodo.copy(id = 5),
        dummyTodo.copy(id = 6),
        dummyTodo.copy(id = 7),
        dummyTodo.copy(id = 8),
        dummyTodo.copy(id = 9),
        dummyTodo.copy(id = 10)
    )

    // ----------------------------------------------------------------
    // Post
    // ----------------------------------------------------------------

    val dummyPost = Post(
        id = 1,
        title = "The quick brown fox jumps over the lazy dog.",
        body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        userId = 1
    )

    val dummyPostList = listOf(
        dummyPost.copy(id = 1),
        dummyPost.copy(id = 2),
        dummyPost.copy(id = 3),
        dummyPost.copy(id = 4),
        dummyPost.copy(id = 5),
        dummyPost.copy(id = 6),
        dummyPost.copy(id = 7),
        dummyPost.copy(id = 8),
        dummyPost.copy(id = 9),
        dummyPost.copy(id = 10)
    )
}
