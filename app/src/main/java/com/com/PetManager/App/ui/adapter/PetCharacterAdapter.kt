package com.kroger.classapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kroger.classapp.R
import com.kroger.classapp.databinding.CharacterCardViewBinding
import com.kroger.classapp.model.PetCharacter

class PetCharacterAdapter(
    private val onCharacterClicked: (character: PetCharacter, position: Int) -> Unit,
) : RecyclerView.Adapter<PetCharacterAdapter.PetCharacterViewHolder>() {

    inner class PetCharacterViewHolder(
        private val binding: CharacterCardViewBinding,
        private val onCharacterClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onCharacterClicked(adapterPosition)
            }
        }

        fun bind(character: PetCharacter) {
            val breedNames = character.breeds.joinToString { it.name } // Joining all breed names into a single string
            binding.petTitle.text = binding.root.context.getString(R.string.pet_name, breedNames)

            Glide.with(binding.root).load(character.url).into(binding.petImage)
        }
    }

    private val petCharacters = mutableListOf<PetCharacter>()

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(characters: List<PetCharacter>) {
        petCharacters.clear()
        petCharacters.addAll(characters)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PetCharacterViewHolder {
        val binding =
            CharacterCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetCharacterViewHolder(binding) { position ->
            onCharacterClicked(petCharacters[position], position)
        }
    }

    override fun getItemCount() = petCharacters.size

    override fun getItemId(position: Int) = position.toLong()

    override fun onBindViewHolder(holder: PetCharacterViewHolder, position: Int) {
        val character = petCharacters[position]
        holder.bind(character)
    }
}
