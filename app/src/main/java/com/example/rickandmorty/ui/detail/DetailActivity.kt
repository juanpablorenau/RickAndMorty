package com.example.rickandmorty.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ActivityDetailBinding
import com.example.rickandmorty.helpers.extensions.handleError
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
        initObservers()

        intent.extras?.let { viewModel.initData(it) }
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
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
