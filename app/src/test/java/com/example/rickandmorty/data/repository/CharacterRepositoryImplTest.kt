package com.example.rickandmorty.data.repository

import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.data.source.local.CharacterLocalDataSource
import com.example.rickandmorty.data.source.remote.CharacterRemoteDataSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterRepositoryImplTest {

    private val character1 = Character(id = 1, name = "Character1")
    private val character2 = Character(id = 2, name = "Character2")
    private val character3 = Character(id = 3, name = "Character3")

    private val remoteCharacters = listOf(character1, character2)
    private val localCharacters = listOf(character3)

    private lateinit var characterRepositoryImpl: CharacterRepositoryImpl

    @RelaxedMockK
    private lateinit var characterRemoteDataSource: CharacterRemoteDataSource

    @RelaxedMockK
    private lateinit var characterLocalDataSource: CharacterLocalDataSource

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        initRepository()
    }

    private fun initRepository() {
        characterRepositoryImpl = CharacterRepositoryImpl(
            characterRemoteDataSource,
            characterLocalDataSource
        )
    }

    @Test
    fun `When localDataSource is empty then remoteDataSource is called`() = runTest {
        // Given
        coEvery { characterLocalDataSource.getCharacters() } returns emptyList()

        // When
        characterRepositoryImpl.getCharacters()

        // Then
        coVerify(exactly = 1) { characterRemoteDataSource.getCharacters(1) }
    }

    @Test
    fun `When localDataSource is not empty then remoteDataSource is not called`() = runTest {
        // Given
        coEvery { characterLocalDataSource.getCharacters() } returns localCharacters

        // When
        characterRepositoryImpl.getCharacters()

        // Then
        coVerify(exactly = 0) { characterRemoteDataSource.getCharacters(1) }
    }

    @Test
    fun `When sources are empty then getCharacters() doesn't fail and return empty`() = runTest {
        // Given
        coEvery { characterLocalDataSource.getCharacters() } returns emptyList()
        coEvery { characterRemoteDataSource.getCharacters(1) } returns emptyList()

        // When
        val list = characterRepositoryImpl.getCharacters()

        // Then
        Assert.assertTrue(list == emptyList<Character>())
    }
}
