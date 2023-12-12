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

package org.imaginativeworld.whynotcompose.ui.screens.tutorial.reactivemodel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class ReactiveModelViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<ProductReactiveModel>>(emptyList())
    val products = _products.asStateFlow()

    init {
        _products.value = ProductReactiveModelMock.items
    }

    fun incrementQuantity(product: ProductReactiveModel) {
        product.increaseQuantity()

        Timber.e("product: $product")
    }

    fun decreaseQuantity(product: ProductReactiveModel) {
        product.decreaseQuantity()
    }
}

// Region: Models

class ProductReactiveModel internal constructor(
    val name: String,
    val price: Double,
    initialQuantity: Int
) {
    var quantity by mutableIntStateOf(initialQuantity)
        private set

    val totalPrice by derivedStateOf {
        initialQuantity * price
    }

    fun increaseQuantity() {
        quantity += 1
    }

    fun decreaseQuantity() {
        if (quantity > 0) {
            quantity -= 1
        }
    }

    // Use Android Studio menu "Code -> Generate -> toString()" to auto generate `toString()`.
    override fun toString(): String {
        return "ProductReactiveModel(name='$name', price=$price, quantity=$quantity, totalPrice=$totalPrice)"
    }
}

// Region: Mocks

object ProductReactiveModelMock {
    val items = listOf(
        ProductReactiveModel(
            name = "Apple",
            price = 5.0,
            initialQuantity = 0
        ),
        ProductReactiveModel(
            name = "Orange",
            price = 100.0,
            initialQuantity = 4
        ),
        ProductReactiveModel(
            name = "Banana",
            price = 10.0,
            initialQuantity = 50
        ),
        ProductReactiveModel(
            name = "Grapes",
            price = 8.5,
            initialQuantity = 20
        ),
        ProductReactiveModel(
            name = "Strawberry",
            price = 15.0,
            initialQuantity = 12
        ),
        ProductReactiveModel(
            name = "Watermelon",
            price = 25.0,
            initialQuantity = 2
        ),
        ProductReactiveModel(
            name = "Pineapple",
            price = 12.0,
            initialQuantity = 8
        ),
        ProductReactiveModel(
            name = "Mango",
            price = 18.0,
            initialQuantity = 6
        ),
        ProductReactiveModel(
            name = "Cherry",
            price = 7.0,
            initialQuantity = 15
        ),
        ProductReactiveModel(
            name = "Blueberry",
            price = 20.0,
            initialQuantity = 10
        ),
        ProductReactiveModel(
            name = "Peach",
            price = 14.0,
            initialQuantity = 18
        ),
        ProductReactiveModel(
            name = "Kiwi",
            price = 9.0,
            initialQuantity = 25
        ),
        ProductReactiveModel(
            name = "Pear",
            price = 11.0,
            initialQuantity = 30
        )
    )
}
