package com.example.rickandmorty.data.source.remote

import com.example.rickandmorty.data.model.Character
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before

@ExperimentalCoroutinesApi
class CharacterRemoteDataSourceTest {

    private val character1 = Character(id = 1, name = "Character1")
    private val character2 = Character(id = 2, name = "Character2")
    private val character3 = Character(id = 3, name = "Character3")

    @RelaxedMockK
    private lateinit var characterRemoteDataSource: CharacterRemoteDataSource

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
    }
}
