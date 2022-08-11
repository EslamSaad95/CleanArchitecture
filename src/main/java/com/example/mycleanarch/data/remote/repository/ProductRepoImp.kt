package com.example.mycleanarch.data.remote.repository

import com.example.mycleanarch.data.remote.ApiService

import com.example.mycleanarch.data.remote.mapper.toRecipe
import com.example.mycleanarch.domain.RecipeRepository
import com.example.mycleanarch.domain.entity.Receipe
import javax.inject.Inject

class ProductRepoImp @Inject constructor(private val apiService: ApiService) : RecipeRepository {

    override suspend fun getRecipe(token: String, id: Int): Receipe {
        return apiService.getRecipe(token, id).toRecipe()

    }
}