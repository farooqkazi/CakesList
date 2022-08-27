package com.assignment.cakeslist.di

import com.assignment.cakeslist.data.cake.CakeRepositoryImpl
import com.assignment.cakeslist.domain.cake.CakeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun provideDepartmentRepository(cakeRepositoryImpl: CakeRepositoryImpl): CakeRepository
}