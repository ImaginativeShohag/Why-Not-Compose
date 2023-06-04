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
