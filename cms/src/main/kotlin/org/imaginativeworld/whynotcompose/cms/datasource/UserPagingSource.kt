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

package org.imaginativeworld.whynotcompose.cms.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import okio.IOException
import org.imaginativeworld.whynotcompose.base.network.ApiException
import org.imaginativeworld.whynotcompose.cms.models.user.User
import org.imaginativeworld.whynotcompose.cms.repositories.UserRepository
import retrofit2.HttpException

class UserPagingSource(
    private val repository: UserRepository
) : PagingSource<Long, User>() {

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, User> {
        val pagePosition = params.key ?: 1

        return try {
            val users = repository.getUsers(
                pagePosition
            )

            if (users == null) {
                LoadResult.Error(ApiException("No data returned!"))
            } else {
                val nextKey = if (users.isEmpty()) {
                    null
                } else {
                    pagePosition + 1
                }

                LoadResult.Page(
                    data = users,
                    prevKey = if (pagePosition == 1L) null else pagePosition - 1,
                    nextKey = nextKey
                )
            }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: ApiException) {
            return LoadResult.Error(exception)
        }
    }

    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Long, User>): Long? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)

            // For cursor paging use this:
            // https://stackoverflow.com/questions/67691903/how-to-implement-pagingsource-getrefreshkey-for-cursor-based-pagination-androi
            // val anchorPageIndex = state.pages.indexOf(state.closestPageToPosition(anchorPosition))
            // state.pages.getOrNull(anchorPageIndex + 1)?.prevKey ?: state.pages.getOrNull(anchorPageIndex - 1)?.nextKey
        }
    }
}
