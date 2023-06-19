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

import org.imaginativeworld.whynotcompose.cms.models.user.User

object MockData {
    val dummyUser = User(
        id = 1,
        name = "Md. Mahmudul Hasan Shohag",
        email = "imaginativeshohag@gmail.com",
        gender = "male",
        status = "active"
    )

    val dummayUserList = listOf(
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
}
