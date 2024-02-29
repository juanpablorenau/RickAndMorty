package com.example.rickandmorty.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.databinding.ItemCharacterBinding
import com.example.rickandmorty.helpers.listeners.setClickWithDebounce

class CharactersAdapter(private val onCharacterClick: (Character) -> Unit) :
    RecyclerView.Adapter<CharacterViewHolder>() {

    private var characterList: List<Character> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        with(holder) {
            bind(characterList[position], onCharacterClick)
            itemView.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.recycler_anim)
        }
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

        binding.card.setClickWithDebounce {
            val animation = AnimationUtils.loadAnimation(binding.card.context, R.anim.navigation_anim)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) { onCharacterClick(character) }
                override fun onAnimationRepeat(animation: Animation) {}
            })
            binding.card.startAnimation(animation)
        }
    }
}
