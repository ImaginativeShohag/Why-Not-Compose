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

package org.imaginativeworld.whynotcompose.repositories

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.imaginativeworld.whynotcompose.base.network.SafeApiRequest
import org.imaginativeworld.whynotcompose.base.network.api.GithubApiInterface

class AppRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: GithubApiInterface
) {
    // ----------------------------------------------------------------
    // Search Github Repositories
    // ----------------------------------------------------------------

    suspend fun searchGithubRepo(
        page: Int,
        sort: String,
        order: String,
        query: String
    ) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.searchGithubRepo(
                page = page,
                sort = sort,
                order = order,
                query = query
            )
        }
    }
}
