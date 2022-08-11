package com.example.mycleanarch.data.remote.mapper

import com.example.mycleanarch.data.remote.dto.RecipeDto
import com.example.mycleanarch.domain.entity.Receipe

fun RecipeDto.toRecipe(): Receipe {
    return Receipe(
        title = title,
        publisher = publisher,
        image = featured_image,
        rating = rating,
        sourceUrl = source_url,
        description = description,
        cookingInstruction = cooking_instructions,
        ingredients = ingredients
    )
}

fun Receipe.toRecipeDto(): RecipeDto {
    return RecipeDto(
        cookingInstruction,
        "",
        "",
        description,
        image,
        ingredients,
        0,
        0,
        0,
        publisher,
        rating,
        sourceUrl,
        title
    )
}