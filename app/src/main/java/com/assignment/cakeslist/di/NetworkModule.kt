package com.assignment.cakeslist.di

import com.assignment.cakeslist.data.CakesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideApiService(): CakesApi {
        return Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/t-reed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CakesApi::class.java)
    }
}