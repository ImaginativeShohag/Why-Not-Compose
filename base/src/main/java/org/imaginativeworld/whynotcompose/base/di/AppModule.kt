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

package org.imaginativeworld.whynotcompose.base.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.imaginativeworld.whynotcompose.base.extensions.MoshiUtil
import org.imaginativeworld.whynotcompose.base.network.ApiClient
import org.imaginativeworld.whynotcompose.base.network.api.GithubApiInterface
import org.imaginativeworld.whynotcompose.base.utils.Constants
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return MoshiUtil.getMoshi()
    }

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return ApiClient.getRetrofit(moshi)
    }

    @Singleton
    @Provides
    fun provideGithubApiInterface(moshi: Moshi): GithubApiInterface {
        return ApiClient.getRetrofit(
            moshi,
            Constants.CMS_SERVER_ENDPOINT + "/"
        ).create(GithubApiInterface::class.java)
    }
}
