package com.kroger.classapp.data.model

import com.kroger.classapp.model.PetCharacter

sealed class PetResponse {
    data class Success(val characters: List<PetCharacter>) : PetResponse()
    object Error : PetResponse()
}
sealed class SinglePetResponse {
    data class Success(val character: PetCharacter) : SinglePetResponse()
    object Error : SinglePetResponse()
}
