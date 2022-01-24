package org.imaginativeworld.whynotcompose.exoplayer

import android.content.Context
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.FileDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSink
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.util.Util

object DataSourceHolder {
    private var cacheDataSourceFactory: CacheDataSource.Factory? = null
    private var defaultDataSourceFactory: DataSource.Factory? = null

    fun getCacheFactory(context: Context): CacheDataSource.Factory {
        if (cacheDataSourceFactory == null) {
            val simpleCache = CacheHolder.get(context)
            val defaultFactory = getDefaultFactory(context)
            cacheDataSourceFactory = CacheDataSource.Factory()
                .setCache(simpleCache)
                .setUpstreamDataSourceFactory(defaultFactory)
                .setCacheReadDataSourceFactory(FileDataSource.Factory())
                .setCacheWriteDataSinkFactory(
                    CacheDataSink.Factory()
                        .setCache(simpleCache)
                        .setFragmentSize(CacheDataSink.DEFAULT_FRAGMENT_SIZE)
                )
        }

        return cacheDataSourceFactory!!
    }

    private fun getDefaultFactory(context: Context): DataSource.Factory {
        if (defaultDataSourceFactory == null) {
            defaultDataSourceFactory = DefaultDataSource.Factory(context)
        }
        return defaultDataSourceFactory!!
    }
}