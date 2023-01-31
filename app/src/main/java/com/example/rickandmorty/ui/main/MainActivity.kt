package com.example.rickandmorty.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.databinding.ActivityMainBinding
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
        initObservers()
    }

    private fun initRecyclerAdapter() {
        val adapter = CharactersAdapter()
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter
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
    }
}
