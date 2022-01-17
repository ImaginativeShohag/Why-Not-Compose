package org.imaginativeworld.whynotcompose.base.extensions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyGridScope
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems

/**
 * This is following [LazyPagingItems]'s [LazyListScope.items] extension function.
 */
@ExperimentalFoundationApi
fun <T : Any> LazyGridScope.items(
    items: LazyPagingItems<T>,
    itemContent: @Composable LazyItemScope.(value: T?) -> Unit
) {
    items(
        count = items.itemCount,
    ) { index ->
        itemContent(items[index])
    }
}
