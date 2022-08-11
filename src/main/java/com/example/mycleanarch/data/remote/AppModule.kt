package com.example.mycleanarch.data.remote

import com.example.mycleanarch.BuildConfig
import com.example.mycleanarch.data.remote.repository.ProductRepoImp
import com.example.mycleanarch.domain.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePaprikaApi(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    fun provideRecipe(apiService: ApiService): RecipeRepository =
        ProductRepoImp(
            apiService
        )

    @Provides
    @Singleton
    @Named("auth_token")
    fun provideToken() = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
}

