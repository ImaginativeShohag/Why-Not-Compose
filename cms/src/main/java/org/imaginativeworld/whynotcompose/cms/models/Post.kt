package org.imaginativeworld.whynotcompose.cms.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlin.random.Random

@JsonClass(generateAdapter = true)
data class Post(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "body")
    val body: String,
    @Json(name = "user_id")
    val userId: Int
) {
    companion object {
        fun getDummies(): List<Post> {
            return listOf(
                Post(
                    Random.nextInt(),
                    "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                    "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\n" +
                        "reprehenderit molestiae ut ut quas totam\\n" +
                        "nostrum rerum est autem sunt rem eveniet architecto",
                    Random(0).nextInt()
                ),
                Post(
                    Random.nextInt(),
                    "qui est esse",
                    "est rerum tempore vitae\n" +
                        "sequi sint nihil reprehenderit dolor beatae ea dolores neque\n" +
                        "fugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\n" +
                        "qui aperiam non debitis possimus qui neque nisi nulla",
                    Random.nextInt()
                ),
                Post(
                    Random.nextInt(),
                    "eum et est occaecati",
                    "ullam et saepe reiciendis voluptatem adipisci\n" +
                        "sit amet autem assumenda provident rerum culpa\n" +
                        "quis hic commodi nesciunt rem tenetur doloremque ipsam iure\n" +
                        "quis sunt voluptatem rerum illo velit",
                    Random.nextInt()
                ),
                Post(
                    Random.nextInt(),
                    "nesciunt quas odio",
                    "repudiandae veniam quaerat sunt sed\n" +
                        "alias aut fugiat sit autem sed est\n" +
                        "voluptatem omnis possimus esse voluptatibus quis\n" +
                        "est aut tenetur dolor neque",
                    Random.nextInt()
                )
            )
        }
    }
}
