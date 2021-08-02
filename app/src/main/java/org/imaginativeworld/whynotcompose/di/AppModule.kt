package org.imaginativeworld.whynotcompose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.imaginativeworld.whynotcompose.network.ApiClient
import org.imaginativeworld.whynotcompose.network.ApiInterface

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    fun provideApiInterface(): ApiInterface {
        return ApiClient.getClient()
    }
}
