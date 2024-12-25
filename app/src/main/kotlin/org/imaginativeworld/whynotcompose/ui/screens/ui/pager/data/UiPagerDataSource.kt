package org.imaginativeworld.whynotcompose.ui.screens.ui.pager.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.delay
import okio.IOException
import org.imaginativeworld.whynotcompose.base.network.ApiException
import retrofit2.HttpException

class UiPagerDataSource(
    private val type: ElementCategory
) : PagingSource<Long, Element>() {
    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, Element> {
        val pagePosition = params.key ?: 1

        return try {
            // Adding a delay to simulate network call
            delay(1000)

            val items = UiPagerRepository.getItems(
                type = type,
                page = pagePosition
            )

            val nextKey = if (items.isEmpty()) {
                null
            } else {
                pagePosition + 1
            }

            LoadResult.Page(
                data = items,
                prevKey = if (pagePosition == 1L) null else pagePosition - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: ApiException) {
            return LoadResult.Error(exception)
        }
    }

    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Long, Element>): Long? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
