package com.example.mycleanarch.domain.usecase

import com.example.mycleanarch.common.EmailExceptionIsEmpty
import com.example.mycleanarch.common.EmailExceptionIsNotValid
import com.example.mycleanarch.domain.RecipeRepository
import com.example.mycleanarch.domain.entity.Receipe
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class RecipeUseCaseTest {


    @Mock
    private lateinit var recipeRepository: RecipeRepository

    @Before
    fun init() {

    }


    @Test(expected = Exception::class)
    fun getRecipe_withWrongParams_returnException() {

        val repo = object : RecipeRepository {
            override suspend fun getRecipe(token: String, id: Int): Receipe {
                throw Exception()
            }
        }
        val useCase = RecipeUseCase(repo)
        runBlocking { useCase.getRecipe("", 3) }
    }


    @Test()
    fun getRecipe_withRightParams_ThenReturnRecipeObj() {
        //act
        val expected = Receipe(title = "")

        //arrange
        val repo = object : RecipeRepository {
            override suspend fun getRecipe(token: String, id: Int): Receipe {
                return Receipe(title = "")
            }
        }
        val useCase = RecipeUseCase(repo)
        val result = runBlocking { useCase.getRecipe("", 3) }

        //assert
        assertEquals(result.title, expected.title)
    }


    @Test(expected = EmailExceptionIsEmpty::class)
    fun validateEmailInput_withEmptyValue_ReturnEmptyException() {
        //act
        val input = ""
        //arrange
        val recipeUseCase = RecipeUseCase(recipeRepository)
        recipeUseCase.validateEmailInput(input)
    }

    @Test(expected = EmailExceptionIsNotValid::class)
    fun validateEmailInput_withWrongValue_ReturnNotValidException() {
        //act
        val input = "www"
        //arrange
        val recipeUseCase = RecipeUseCase(recipeRepository)
        recipeUseCase.validateEmailInput(input)
    }

    @Test()
    fun validateEmailInput_withRightValue_ReturnTrue() {
        //act
        val input = "www@example.com"
        //arrange
        val recipeUseCase = RecipeUseCase(recipeRepository)
        val result = recipeUseCase.validateEmailInput(input)
        //assert
        assertTrue(result)
    }

}