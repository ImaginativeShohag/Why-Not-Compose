package com.example.store.repositories

import android.content.Context
import com.example.store.network.api.ProductsApiInterface
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.imaginativeworld.whynotcompose.base.network.SafeApiRequest

class ProductRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val productApi: ProductsApiInterface
) {
    suspend fun getProducts(page: Long) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            productApi.getProducts(page)
        }
    }
}
