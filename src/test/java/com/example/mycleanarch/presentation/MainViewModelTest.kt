package com.example.mycleanarch.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mycleanarch.common.EmailExceptionIsEmpty
import com.example.mycleanarch.common.EmailExceptionIsNotValid
import com.example.mycleanarch.domain.entity.Receipe
import com.example.mycleanarch.domain.usecase.RecipeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class MainViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()


    @Mock
    private lateinit var recipeUseCase: RecipeUseCase

    private lateinit var viewModel: MainViewModel


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(recipeUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }


    @Test()
    fun getRecipeDetails_withNoInternet_thenEmitError_NotEmitRecipeData() {
        runBlocking {
            Mockito.`when`(recipeUseCase.getRecipe("", 1))
                .then { throw Exception("Fail") }
            viewModel.getRecipeDetails()
            val expected = viewModel.recipeData.value
            Assert.assertNull(expected)
            System.out.println(viewModel.error.value)
            Assert.assertNotNull(viewModel.error.value)
        }

    }


    @Test()
    fun getRecipeDetails_withRightValue_ThenEmitRecipeData() {
        runBlocking {
            Mockito.`when`(recipeUseCase.getRecipe("", 1))
                .thenAnswer { Receipe() }
            viewModel.getRecipeDetails()
            val expected = viewModel.recipeData.value
            Assert.assertNotNull(expected)
            Assert.assertNull(viewModel.error.value)
        }

    }

    //regionValidateEmailTestCases
    @Test
    fun whenValidateEmail_WithEmptyValue_ThenEmitErrorLiveData() {
        runBlocking {
            Mockito.`when`(recipeUseCase.validateEmailInput(""))
                .thenAnswer { throw EmailExceptionIsEmpty() }
            viewModel.validateEmailInputs("")
            val error = viewModel.error.value
            Assert.assertNotNull(error)

        }
    }

    @Test
    fun whenValidateEmail_WithWrongValue_ThenEmitErrorLiveData() {
        runBlocking {
            Mockito.`when`(recipeUseCase.validateEmailInput("ee"))
                .thenAnswer { throw EmailExceptionIsNotValid() }
            viewModel.validateEmailInputs("ee")
            val error = viewModel.error.value
            Assert.assertNotNull(error)

        }
    }


    @Test
    fun whenValidateEmail_WithRightValue_ThenNotEmitErrorLiveData() {
        runBlocking {
            Mockito.`when`(recipeUseCase.validateEmailInput("")).thenAnswer { true }
            viewModel.validateEmailInputs("")
            val error = viewModel.error.value
            Assert.assertNull(error)

        }
    }

    //endregion


}