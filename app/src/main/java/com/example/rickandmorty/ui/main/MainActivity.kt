package com.example.rickandmorty.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.ActivityMainBinding
import com.example.rickandmorty.helpers.extensions.handleError
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
        initListeners()
        initObservers()
    }

    private fun initRecyclerAdapter() {
        val adapter = CharactersAdapter()
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter
    }

    private fun initListeners() {
        binding.recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
                val listSize = (recyclerView.adapter as CharactersAdapter).itemCount

                if (layoutManager.findLastVisibleItemPosition() >= listSize - 2) {
                    viewModel.notifyLastVisible()
                }
            }
        })
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
                .map { it.error }
                .collect {
                    handleError(it)
                }
        }
    }
}
