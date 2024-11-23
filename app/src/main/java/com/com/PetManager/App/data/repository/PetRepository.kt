package com.kroger.classapp.data.repository

import com.kroger.classapp.data.model.PetResponse
import com.kroger.classapp.data.model.SinglePetResponse

interface PetRepository {
    suspend fun getCharacters(): PetResponse
    suspend fun getCharacter(id: String): SinglePetResponse

}