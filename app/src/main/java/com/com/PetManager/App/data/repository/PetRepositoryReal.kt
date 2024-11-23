package com.kroger.classapp.data.repository

import com.kroger.classapp.data.PetAPI
import com.kroger.classapp.data.model.PetResponse
import com.kroger.classapp.data.model.SinglePetResponse
import com.kroger.classapp.model.PetCharacter
import javax.inject.Inject

class PetRepositoryReal @Inject constructor(
    private val petApi: PetAPI,
) : PetRepository {
    override suspend fun getCharacters(): PetResponse {
        try {
            val result = petApi.getCharacters()
            if (result.isSuccessful) {
                val characters = result.body() ?: emptyList()
                val validCharacters = characters.filter { it.hasValidInfo() }
                return if (validCharacters.isNotEmpty()) {
                    PetResponse.Success(validCharacters)
                } else {
                    PetResponse.Error
                }
            } else {
                return PetResponse.Error
            }
        } catch (e: Exception) {
            return PetResponse.Error
        }
    }

    override suspend fun getCharacter(id: String): SinglePetResponse {
        try {
            val result = petApi.getCharacter(id)
            if (result.isSuccessful) {
                val character = result.body()
                return if (character != null && character.hasValidInfo()) {
                    SinglePetResponse.Success(character)
                } else {
                    SinglePetResponse.Error
                }
            } else {
                return SinglePetResponse.Error
            }
        } catch (e: Exception) {
            return SinglePetResponse.Error
        }
    }

    private fun PetCharacter.hasValidInfo(): Boolean {
        return breeds.isNotEmpty() && height > 0 && id.isNotBlank() && url.isNotBlank()
    }
}
