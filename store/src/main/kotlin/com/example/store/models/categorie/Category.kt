package com.example.store.models.categorie

data class Category(
    val id: Int,
    val name: String,
    val imageUrl: String
)

val categories = listOf(
    Category(id = 1, name = "Electron-ics", imageUrl = "https://fakestoreapi.com/img/61IBBVJvSDL._AC_SY879_.jpg"),
    Category(id = 2, name = "Jewelery", imageUrl = "https://fakestoreapi.com/img/71pWzhdJNwL._AC_UL640_QL65_ML3_.jpg"),
    Category(id = 3, name = "Men's Clothing", imageUrl = "https://fakestoreapi.com/img/71pWzhdJNwL._AC_UL640_QL65_ML3_.jpg"),
    Category(id = 4, name = "Women's Clothing", imageUrl = "https://fakestoreapi.com/img/61IBBVJvSDL._AC_SY879_.jpg")
)
