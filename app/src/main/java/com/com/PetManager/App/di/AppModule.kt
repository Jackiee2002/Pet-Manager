package com.kroger.classapp.di

import com.kroger.classapp.data.PetAPI
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRickAndMortyApi(): PetAPI =
        Retrofit.Builder()
            .baseUrl("https://api.thedogapi.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
}