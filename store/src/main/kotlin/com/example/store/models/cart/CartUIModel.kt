package com.example.store.models.cart

import com.example.store.models.product.Product

data class CartUIModel(
    val product: Product,
    var quantity: Int
)
