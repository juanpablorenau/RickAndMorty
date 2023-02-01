package com.example.rickandmorty.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.databinding.ItemCharacterBinding

class CharactersAdapter(
    private val onCharacterClick: (Character) -> Unit
) :
    RecyclerView.Adapter<CharacterViewHolder>() {

    private var characterList: List<Character> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characterList[position], onCharacterClick)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    fun submitList(list: List<Character>) {
        characterList = list
        notifyDataSetChanged()
    }
}

class CharacterViewHolder(private val binding: ItemCharacterBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(character: Character, onCharacterClick: (Character) -> Unit) {
        binding.character = character

        Glide.with(binding.root.context)
            .load(character.image)
            .placeholder(R.drawable.ic_person)
            .into(binding.imageCharacter)

        binding.card.setOnClickListener {
            onCharacterClick(character)
        }
    }
}
