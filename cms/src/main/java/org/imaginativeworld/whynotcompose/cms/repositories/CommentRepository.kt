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
import org.imaginativeworld.whynotcompose.cms.models.Comment
import org.imaginativeworld.whynotcompose.cms.network.api.CommentApiInterface

class CommentRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: CommentApiInterface
) {
    suspend fun getComments(postId: Int, page: Int) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.getComments(postId, page)
        }
    }

    suspend fun addComment(postId: Int, comment: Comment) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.addComment(postId, comment)
        }
    }

    suspend fun getComment(todoId: Int) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.getComment(todoId)
        }
    }

    suspend fun deleteComment(commentId: Int) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.deleteComment(commentId)
        }
    }

    suspend fun updateComment(id: Int, comment: Comment) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.updateComment(id, comment)
        }
    }
}
