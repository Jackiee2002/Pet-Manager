package com.kroger.classapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kroger.classapp.data.model.PetResponse
import com.kroger.classapp.data.repository.PetRepository
import com.kroger.classapp.model.PetCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PetCharacterViewModel @Inject constructor(
    private val petRepository: PetRepository,
) : ViewModel() {

    private val _characters = MutableStateFlow<PetCharacterEvent>(PetCharacterEvent.Loading)
    val characters: StateFlow<PetCharacterEvent> = _characters

    fun fillData() = viewModelScope.launch {
        when (val response = petRepository.getCharacters()) {
            PetResponse.Error -> _characters.value = PetCharacterEvent.Failure
            is PetResponse.Success -> _characters.value = PetCharacterEvent.Success(response.characters)
        }
    }

    fun fetchById(id: String): PetCharacter? {
        val characters = characters.value
        if (characters is PetCharacterEvent.Success) {
            return characters.characters.find { it.id == id }
        }
        return null
    }
    sealed class PetCharacterEvent {
        data class Success(val characters: List<PetCharacter>) : PetCharacterEvent()
        data object Failure : PetCharacterEvent()
        data object Loading : PetCharacterEvent()
    }

}