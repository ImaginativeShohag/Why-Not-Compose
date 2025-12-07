package com.example.store.repositories

import android.content.Context
import com.example.store.network.api.CategoriesApiInterface
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.imaginativeworld.whynotcompose.base.network.SafeApiRequest

class CategoriesRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val categoriesApi: CategoriesApiInterface
) {
    suspend fun getCategories() = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            categoriesApi.getCategories()
        }
    }
}
