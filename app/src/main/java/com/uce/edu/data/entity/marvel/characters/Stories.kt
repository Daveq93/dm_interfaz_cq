package com.uce.edu.data.entity.marvel.characters

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)