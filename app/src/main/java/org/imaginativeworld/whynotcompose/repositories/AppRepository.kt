package org.imaginativeworld.whynotcompose.repositories

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import org.imaginativeworld.whynotcompose.network.ApiInterface
import javax.inject.Inject


class AppRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: ApiInterface
) {

}
