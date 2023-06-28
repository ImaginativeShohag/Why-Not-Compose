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

package org.imaginativeworld.whynotcompose.cms.di

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
import org.imaginativeworld.whynotcompose.base.network.ApiClient
import org.imaginativeworld.whynotcompose.base.utils.Constants
import org.imaginativeworld.whynotcompose.cms.BuildConfig
import org.imaginativeworld.whynotcompose.cms.db.CMSDatabase
import org.imaginativeworld.whynotcompose.cms.network.api.CommentApiInterface
import org.imaginativeworld.whynotcompose.cms.network.api.PostApiInterface
import org.imaginativeworld.whynotcompose.cms.network.api.TodoApiInterface
import org.imaginativeworld.whynotcompose.cms.network.api.UserApiInterface
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
class CMSAppModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): CMSDatabase {
        return CMSDatabase(context)
    }

    @Singleton
    @Provides
    @Named("CMS")
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return ApiClient.getRetrofit(
            moshi,
            Constants.CMS_SERVER_ENDPOINT + "/",
            mapOf(
                "Authorization" to "Bearer ${BuildConfig.CMS_API_KEY}"
            )
        )
    }

    @Singleton
    @Provides
    fun provideUserApiInterface(@Named("CMS") retrofit: Retrofit): UserApiInterface {
        return retrofit.create(UserApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideTodoApiInterface(@Named("CMS") retrofit: Retrofit): TodoApiInterface {
        return retrofit.create(TodoApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun providePostApiInterface(@Named("CMS") retrofit: Retrofit): PostApiInterface {
        return retrofit.create(PostApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideCommentApiInterface(@Named("CMS") retrofit: Retrofit): CommentApiInterface {
        return retrofit.create(CommentApiInterface::class.java)
    }
}
