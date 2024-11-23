package com.kroger.classapp.data

import com.kroger.classapp.model.PetCharacter
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PetAPI {
    @GET("/v1/images/search?limit=10&api_key=live_vSxaFKHiz0UnoIjH5L0s5y7yzbKEK8QCKr99GNdLr0KoLENvWdb3xLgXm0yiinsu")
    suspend fun getCharacters(): Response<List<PetCharacter>>

    @GET("/v1/images/{id}")
    suspend fun getCharacter(@Path("id") id: String): Response<PetCharacter>
}