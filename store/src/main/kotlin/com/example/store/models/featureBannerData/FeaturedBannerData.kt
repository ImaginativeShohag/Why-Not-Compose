package com.example.store.models.featureBannerData

data class FeaturedBannerData(
    val imageUrl: String,
    val price: String,
    val title: String
)

val featuredBanners = listOf(
    FeaturedBannerData(
        imageUrl = "https://fakestoreapi.com/img/71pWzhdJNwL._AC_UL640_QL65_ML3_t.png",
        price = "$695.00",
        title = "John Hardy Women's Legends Naga Gold & Silver Bracelet"
    ),
    FeaturedBannerData(
        imageUrl = "https://fakestoreapi.com/img/51UDEzMJVpL._AC_UL640_QL65_ML3_t.png",
        price = "$350.00",
        title = "Classic Created Wedding Engagement Solitaire Diamond Ring"
    ),
    FeaturedBannerData(
        imageUrl = "https://fakestoreapi.com/img/71YAIFU48IL._AC_UL640_QL65_ML3_t.png",
        price = "$1,200.00",
        title = "White Gold Plated Princess"
    ),

    FeaturedBannerData(
        imageUrl = "https://fakestoreapi.com/img/71pWzhdJNwL._AC_UL640_QL65_ML3_t.png",
        price = "$695.00",
        title = "John Hardy Women's Legends Naga Gold & Silver Bracelet"
    ),
    FeaturedBannerData(
        imageUrl = "https://fakestoreapi.com/img/51UDEzMJVpL._AC_UL640_QL65_ML3_t.png",
        price = "$350.00",
        title = "Classic Created Wedding Engagement Solitaire Diamond Ring"
    ),
    FeaturedBannerData(
        imageUrl = "https://fakestoreapi.com/img/71YAIFU48IL._AC_UL640_QL65_ML3_t.png",
        price = "$1,200.00",
        title = "White Gold Plated Princess"
    )
)
