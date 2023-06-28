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

package org.imaginativeworld.whynotcompose.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import okio.IOException
import org.imaginativeworld.whynotcompose.base.models.github.GithubRepo
import org.imaginativeworld.whynotcompose.base.network.ApiException
import org.imaginativeworld.whynotcompose.repositories.AppRepository
import retrofit2.HttpException

class GithubRepoDataSource(
    private val repository: AppRepository,
    private val query: String
) : PagingSource<Int, GithubRepo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepo> {
        val pagePosition = params.key ?: 1

        return try {
            val response = repository.searchGithubRepo(
                page = pagePosition,
                sort = "stars",
                order = "desc",
                query = query
            )

            val result = response?.items

            if (result == null) {
                LoadResult.Error(ApiException(response?.message ?: "No data returned!"))
            } else {
                val nextKey = if (result.isEmpty()) {
                    null
                } else {
                    pagePosition + 1
                }

                LoadResult.Page(
                    data = result,
                    prevKey = if (pagePosition == 1) null else pagePosition - 1,
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
    override fun getRefreshKey(state: PagingState<Int, GithubRepo>): Int? {
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
