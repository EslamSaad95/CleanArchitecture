package com.example.mycleanarch.data.remote

import com.example.mycleanarch.data.remote.dto.RecipeDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("get")
    suspend fun getRecipe(
        @Header("Authorization") token: String,
        @Query("id") id: Int,
    ): RecipeDto
}