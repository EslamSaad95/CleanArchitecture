package com.example.mycleanarch.domain

import com.example.mycleanarch.domain.entity.Receipe

interface RecipeRepository {


    suspend fun getRecipe(token: String, id: Int): Receipe

}