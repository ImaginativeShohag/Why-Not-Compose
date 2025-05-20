package com.example.store.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.store.models.product.Product
import com.example.store.repositories.ProductRepository
import okio.IOException
import org.imaginativeworld.whynotcompose.base.network.ApiException
import retrofit2.HttpException

class ProductPagingSource(
    private val repository: ProductRepository
) : PagingSource<Long, Product>() {

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, Product> {
        val pagePosition = params.key ?: 1

        return try {
            val products = repository.getProducts(
                pagePosition
            )

            if (products == null) {
                LoadResult.Error(ApiException("No data returned!"))
            } else {
                val nextKey = if (products.isEmpty()) {
                    null
                } else {
                    pagePosition + 1
                }

                LoadResult.Page(
                    data = products,
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
    override fun getRefreshKey(state: PagingState<Long, Product>): Long? {
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
