package com.example.store.di

import com.example.store.network.api.CategoriesApiInterface
import com.example.store.network.api.ProductsApiInterface
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
import org.imaginativeworld.whynotcompose.base.network.ApiClient
import org.imaginativeworld.whynotcompose.base.utils.Constants
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
class StoreAppModule {
    @Singleton
    @Provides
    @Named("STORE")
    fun provideRetrofit(moshi: Moshi): Retrofit = ApiClient.getRetrofit(
        moshi,
        Constants.STORE_SERVER_ENDPOINT + "/",
        mapOf()
    )

    @Singleton
    @Provides
    fun provideProductApiInterface(@Named("STORE") retrofit: Retrofit): ProductsApiInterface = retrofit.create(ProductsApiInterface::class.java)

    @Singleton
    @Provides
    fun provideCategoriesApiInterface(@Named("STORE") retrofit: Retrofit): CategoriesApiInterface = retrofit.create(CategoriesApiInterface::class.java)
}
