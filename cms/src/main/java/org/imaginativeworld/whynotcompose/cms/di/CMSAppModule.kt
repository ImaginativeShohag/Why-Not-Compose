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

package org.imaginativeworld.whynotcompose.cms.di

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.imaginativeworld.whynotcompose.base.network.ApiClient
import org.imaginativeworld.whynotcompose.base.utils.Constants
import org.imaginativeworld.whynotcompose.cms.db.CMSDatabase
import org.imaginativeworld.whynotcompose.cms.network.api.CommentApiInterface
import org.imaginativeworld.whynotcompose.cms.network.api.PostApiInterface
import org.imaginativeworld.whynotcompose.cms.network.api.TodoApiInterface
import org.imaginativeworld.whynotcompose.cms.network.api.UserApiInterface

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
    fun provideUserApiInterface(moshi: Moshi): UserApiInterface {
        return ApiClient.getRetrofit(
            moshi,
            Constants.CMS_SERVER_ENDPOINT + "/"
        ).create(UserApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideTodoApiInterface(moshi: Moshi): TodoApiInterface {
        return ApiClient.getRetrofit(
            moshi,
            Constants.CMS_SERVER_ENDPOINT + "/"
        ).create(TodoApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun providePostApiInterface(moshi: Moshi): PostApiInterface {
        return ApiClient.getRetrofit(
            moshi,
            Constants.CMS_SERVER_ENDPOINT + "/"
        ).create(PostApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideCommentApiInterface(moshi: Moshi): CommentApiInterface {
        return ApiClient.getRetrofit(
            moshi,
            Constants.CMS_SERVER_ENDPOINT + "/"
        ).create(CommentApiInterface::class.java)
    }
}
