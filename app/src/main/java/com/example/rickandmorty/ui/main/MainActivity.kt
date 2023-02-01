package com.example.rickandmorty.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.databinding.ActivityMainBinding
import com.example.rickandmorty.helpers.extensions.handleError
import com.example.rickandmorty.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerAdapter()
        iniListeners()
        initObservers()
    }

    override fun onResume() {
        viewModel.reloadCharacters()
        super.onResume()
    }

    private fun initRecyclerAdapter() {
        val adapter = CharactersAdapter { clickedCharacter ->
            goToDetailActivity(clickedCharacter.id)
        }
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter
    }

    private fun iniListeners() {
        binding.swipe.setOnRefreshListener {
            viewModel.reloadCharacters()
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.uiState
                .map { it.items }
                .distinctUntilChanged()
                .collect {
                    if (it.isNotEmpty()) {
                        (binding.recycler.adapter as? CharactersAdapter)?.submitList(it)
                    }
                }
        }
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
                .map { it.isReloading }
                .distinctUntilChanged()
                .collect {
                    binding.swipe.isRefreshing = it
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

    private fun goToDetailActivity(id: Int) {
        startActivity(
            Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailActivity.INTENT_ID, id)
            }
        )
    }
}
