package com.example.mycleanarch.domain.usecase

import androidx.core.util.PatternsCompat
import com.example.mycleanarch.common.EmailExceptionIsEmpty
import com.example.mycleanarch.common.EmailExceptionIsNotValid
import com.example.mycleanarch.domain.RecipeRepository
import com.example.mycleanarch.domain.entity.Receipe
import javax.inject.Inject


class RecipeUseCase @Inject constructor(val recipeRepo: RecipeRepository) {
    suspend fun getRecipe(token: String, id: Int): Receipe {
        return runCatching { recipeRepo.getRecipe(token, id) }
            .getOrElse { throw it }


    }

    fun validateEmailInput(email: String): Boolean {
        when {
            email.isEmpty() -> throw EmailExceptionIsEmpty()
            PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
                .not() -> throw EmailExceptionIsNotValid()
            else -> return true
        }
    }

}