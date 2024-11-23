package com.com.PetManager.App.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.kroger.classapp.R
import com.kroger.classapp.databinding.FragmentPetDetailBinding
import com.kroger.classapp.viewmodel.PetDetailViewModel
import com.kroger.classapp.viewmodel.PetDetailViewModel.PetCharacterEvent.*
import kotlinx.coroutines.launch

class PetDetailFragment : Fragment() {

    private var _binding: FragmentPetDetailBinding? = null
    private val binding get() = _binding!!

    private val petDetailViewModel: PetDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPetDetailBinding.inflate(inflater, container, false)
        setupObservers()

        if (arguments != null) {
            val characterId = requireArguments().getString(com.com.PetManager.App.ui.PetDetailFragment.Companion.BUNDLE_ID)
            if (characterId != null) {
                petDetailViewModel.fetchById(characterId)
            }
        }
        return binding.root
    }
    private fun setupObservers() {
        lifecycleScope.launch {
            petDetailViewModel.characters.collect { event ->
                when (event) {
                    Failure -> {
                        setVisible(false)
                    }

                    Loading -> {
                        setVisible(false)
                    }

                    is Success -> {
                        val character = event.character
                        binding.petBreed.text = getString(R.string.pet_name, character.breeds.first().name)
                        binding.petLifespan.text = getString(R.string.pet_lifespan, character.breeds.first().lifeSpan)
                        binding.petTraits.text = getString(R.string.pet_traits, character.breeds.first().temperament)
                        binding.petHeight.text = getString(R.string.pet_height, character.breeds.first().height.metric)
                        binding.petWeight.text = getString(R.string.pet_weight, character.breeds.first().weight.metric)
                        binding.petFor.text = getString(R.string.pet_for, character.breeds.first().bredFor)
                        binding.petGroup.text = getString(R.string.pet_group, character.breeds.first().breedGroup)
                        Glide.with(binding.root).load(character.url).into(binding.petImage)

                        setVisible(true)
                    }
                }
            }
        }
    }

    private fun setVisible(visible: Boolean) {
        binding.petImage.isVisible = visible
        binding.petBreed.isVisible = visible
        binding.petLifespan.isVisible = visible
        binding.petTraits.isVisible = visible
        binding.petHeight.isVisible = visible
        binding.petWeight.isVisible = visible
        binding.petFor.isVisible = visible
        binding.petGroup.isVisible = visible
    }
    companion object {
        private const val BUNDLE_ID = "character_id"

        fun newInstance(id: String) = com.com.PetManager.App.ui.PetDetailFragment().apply {
            arguments = bundleOf(com.com.PetManager.App.ui.PetDetailFragment.Companion.BUNDLE_ID to id)
        }
    }
}
