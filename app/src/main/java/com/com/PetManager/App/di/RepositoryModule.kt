package com.kroger.classapp.di

import com.kroger.classapp.data.repository.PetRepository
import com.kroger.classapp.data.repository.PetRepositoryReal
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPetRepository(
        petRepositoryImpl: PetRepositoryReal,
    ): PetRepository
}