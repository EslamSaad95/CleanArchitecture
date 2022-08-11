package com.example.mycleanarch.domain.entity

data class Receipe(
    val title: String? = null,
    val publisher: String? = null,
    val image: String? = null,
    val rating: Int? = null,
    val sourceUrl: String? = null,
    val description: String? = null,
    val cookingInstruction: String? = null,
    val ingredients: List<String>? = listOf(),
)
