package com.example.store.repositories

import android.content.Context
import com.example.store.models.product.Product
import com.example.store.network.api.ProductDetailsApiInterface
import com.example.store.network.api.ProductsApiInterface
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.imaginativeworld.whynotcompose.base.network.SafeApiRequest

class ProductRepository @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val productApi: ProductsApiInterface,

    private val productDetailsApi: ProductDetailsApiInterface

) {
//    suspend fun getProducts(page: Long) = withContext(Dispatchers.IO) {
//        SafeApiRequest.apiRequest(context) {
//            productApi.getProducts(page)
//        }
//    }

    suspend fun getProducts(page: Long): List<Product>? = withContext(Dispatchers.IO) {
        try {
            val response = productApi.getProducts(page)

            if (response.isSuccessful) {
                val body = response.body()
                return@withContext body
            } else {
                val errorBody = response.errorBody()?.string()
                return@withContext null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext null
        }
    }

    suspend fun getProductsByCategory(categoryName: String): List<Product>? = withContext(Dispatchers.IO) {
        try {
            val response = productApi.getProductsByCategory(categoryName)

            if (response.isSuccessful) {
                return@withContext response.body()
            } else {
                return@withContext null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext null
        }
    }

    suspend fun getProductsId(id: Int) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            productDetailsApi.getProductDetails(id)
        }
    }

    suspend fun getCategories(): List<String>? = withContext(Dispatchers.IO) {
        try {
            val response = productApi.getCategories()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
