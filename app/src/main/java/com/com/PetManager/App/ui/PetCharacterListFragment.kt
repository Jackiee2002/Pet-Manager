package com.kroger.classapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kroger.classapp.R
import com.kroger.classapp.databinding.FragmentCharacterListBinding
import com.kroger.classapp.ui.adapter.PetCharacterAdapter
import com.kroger.classapp.viewmodel.PetCharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PetCharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val petCharacterViewModel: PetCharacterViewModel by activityViewModels()
    private val characterAdapter = PetCharacterAdapter { character, _ ->
        requireActivity().supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, PetDetailFragment.newInstance(character.id))
            addToBackStack(null)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        setupObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = characterAdapter
        }
        petCharacterViewModel.fillData()

    }

    private fun setupObservers() {
        lifecycleScope.launch {
            petCharacterViewModel.characters.collect { event ->
                when (event) {
                    PetCharacterViewModel.PetCharacterEvent.Failure -> {
                        binding.progressBar.isVisible = false
                        binding.recyclerView.isVisible = false
                        binding.errorMessage.isVisible = true
                    }

                    PetCharacterViewModel.PetCharacterEvent.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.recyclerView.isVisible = false
                        binding.errorMessage.isVisible = false
                    }

                    is PetCharacterViewModel.PetCharacterEvent.Success -> {
                        characterAdapter.refreshData(event.characters)
                        binding.progressBar.isVisible = false
                        binding.errorMessage.isVisible = false
                        binding.recyclerView.isVisible = true
                    }
                }
            }
        }
    }
}
