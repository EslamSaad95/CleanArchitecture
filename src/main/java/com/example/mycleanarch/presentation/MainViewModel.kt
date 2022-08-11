package com.example.mycleanarch.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycleanarch.common.EmailExceptionIsEmpty
import com.example.mycleanarch.common.EmailExceptionIsNotValid
import com.example.mycleanarch.domain.entity.Receipe
import com.example.mycleanarch.domain.usecase.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainViewModel @Inject constructor(
    val recipeUseCase: RecipeUseCase,
    @Named("auth_token") val token: String = "",

    ) : ViewModel() {

    val loading = MutableLiveData(false)
    val recipeData = MutableLiveData<Receipe>()
    val error = MutableLiveData<String>()
    fun getRecipeDetails() {
        viewModelScope.launch {
            loading.value = true
            kotlin.runCatching {
                loading.value = false
                recipeData.value = recipeUseCase.getRecipe(token, 1)
            }.getOrElse {
                loading.value = false
                when (it) {
                    is HttpException -> {
                        error.value = it.message()
                    }
                    is IOException -> error.value = "No Internet Connection"
                    else -> {
                        error.value = it.toString()
                        it.printStackTrace()
                    }
                }
            }

        }

    }

    fun validateEmailInputs(email: String) {
        kotlin.runCatching {
            recipeUseCase.validateEmailInput(email)
        }
            .onFailure { it.handleEmailError() }
    }

    private fun Throwable.handleEmailError() {
        when (this) {
            is EmailExceptionIsEmpty -> error.value = "Please enter your email"
            is EmailExceptionIsNotValid -> error.value = "Please enter a valid email"
        }
    }


}