package com.example.rickandmorty.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.data.model.Location
import com.example.rickandmorty.databinding.ActivityDetailBinding
import com.example.rickandmorty.helpers.extensions.handleError
import com.example.rickandmorty.helpers.extensions.toast
import com.example.rickandmorty.helpers.listeners.setClickWithDebounce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        iniListeners()
        initObservers()

        intent.extras?.let { viewModel.initData(it) }
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun iniListeners() {
        binding.buttonSaveChanges.setClickWithDebounce {
            saveChanges()
        }

        binding.buttonDelete.setClickWithDebounce {
            viewModel.deleteCharacter()
            finish()
        }
    }

    private fun saveChanges() {
        val newStatus = binding.editStatus.text.toString()
        val newSpecies = binding.editSpecies.text.toString()
        val newGender = binding.editGender.text.toString()
        val newLocation = Location(
            name = binding.editLocation.text.toString(),
            url = viewModel.uiState.value.character.location.url
        )
        with(viewModel.uiState.value.character) {
            val character = Character(
                id = id,
                name = name,
                status = newStatus,
                species = newSpecies,
                type = type,
                gender = newGender,
                location = newLocation,
                image = image,
                episodes = episodes
            )
            viewModel.saveCharacter(character)
            toast(R.string.changes_saved)
            finish()
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.uiState
                .map { it.isLoading }
                .distinctUntilChanged()
                .collect {
                    binding.progressBar.isVisible = it
                }
        }

        lifecycleScope.launch {
            viewModel.uiState
                .map { it.character }
                .distinctUntilChanged()
                .collect {
                    with(binding) {
                        character = it

                        Glide.with(this@DetailActivity)
                            .load(it.image)
                            .placeholder(R.drawable.ic_person)
                            .into(imageCharacter)
                    }
                }
        }

        lifecycleScope.launch {
            viewModel.uiState
                .map { it.error }
                .collect {
                    handleError(it)
                }
        }
    }

    companion object {
        const val INTENT_ID = "ID"
    }
}
