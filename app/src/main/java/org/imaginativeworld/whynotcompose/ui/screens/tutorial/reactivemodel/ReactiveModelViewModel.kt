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
import kotlinx.coroutines.flow.StateFlow

class ReactiveModelViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<ProductReactiveModel>>(emptyList())
    val products: StateFlow<List<ProductReactiveModel>>
        get() = _products

    init {
        _products.value = ProductReactiveModel.dummayItems
    }

    fun incrementQuantity(product: ProductReactiveModel) {
        product.increaseQuantity()
    }

    fun decreaseQuantity(product: ProductReactiveModel) {
        product.decreaseQuantity()
    }
}

// Region: Models

data class ProductReactiveModel internal constructor(
    val name: String,
    val price: Double
) {
    constructor(
        name: String,
        price: Double,
        quantity: Int
    ) : this(name, price) {
        this.quantity = quantity
    }

    var quantity by mutableIntStateOf(0)
        private set

    val totalPrice by derivedStateOf {
        quantity * price
    }

    fun increaseQuantity() {
        quantity += 1
    }

    fun decreaseQuantity() {
        if (quantity > 0) {
            quantity -= 1
        }
    }

    companion object {
        val dummayItems = listOf(
            ProductReactiveModel(
                name = "Apple",
                price = 5.0,
                quantity = 0
            ),
            ProductReactiveModel(
                name = "Orange",
                price = 100.0,
                quantity = 4
            ),
            ProductReactiveModel(
                name = "Banana",
                price = 10.0,
                quantity = 50
            ),
            ProductReactiveModel(
                name = "Grapes",
                price = 8.5,
                quantity = 20
            ),
            ProductReactiveModel(
                name = "Strawberry",
                price = 15.0,
                quantity = 12
            ),
            ProductReactiveModel(
                name = "Watermelon",
                price = 25.0,
                quantity = 2
            ),
            ProductReactiveModel(
                name = "Pineapple",
                price = 12.0,
                quantity = 8
            ),
            ProductReactiveModel(
                name = "Mango",
                price = 18.0,
                quantity = 6
            ),
            ProductReactiveModel(
                name = "Cherry",
                price = 7.0,
                quantity = 15
            ),
            ProductReactiveModel(
                name = "Blueberry",
                price = 20.0,
                quantity = 10
            ),
            ProductReactiveModel(
                name = "Peach",
                price = 14.0,
                quantity = 18
            ),
            ProductReactiveModel(
                name = "Kiwi",
                price = 9.0,
                quantity = 25
            ),
            ProductReactiveModel(
                name = "Pear",
                price = 11.0,
                quantity = 30
            )
        )
    }
}
