package com.kroger.classapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kroger.classapp.data.model.SinglePetResponse
import com.kroger.classapp.data.repository.PetRepository
import com.kroger.classapp.model.PetCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PetDetailViewModel @Inject constructor(
    private val petRepository: PetRepository,
) : ViewModel() {

    private val _characters = MutableStateFlow<PetCharacterEvent>(PetCharacterEvent.Loading)
    val characters: StateFlow<PetCharacterEvent> = _characters

    fun fetchById(id: String) = viewModelScope.launch {
        when (val response = petRepository.getCharacter(id)) {
            SinglePetResponse.Error -> _characters.value = PetCharacterEvent.Failure
            is SinglePetResponse.Success -> _characters.value = PetCharacterEvent.Success(response.character)
        }
    }


    sealed class PetCharacterEvent {
        data class Success(val character: PetCharacter) : PetCharacterEvent()
        data object Failure : PetCharacterEvent()
        data object Loading : PetCharacterEvent()
    }

}