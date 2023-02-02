package com.example.rickandmorty.data.source.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.rickandmorty.data.CoroutineRule
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.data.source.local.db.AppDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.* // ktlint-disable no-wildcard-imports
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class CharacterLocalDataSourceTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = CoroutineRule()

    private lateinit var database: AppDatabase
    private lateinit var characterLocalDataSource: CharacterLocalDataSource

    private val character1 = Character(id = 1, name = "Character1")
    private val character2 = Character(id = 2, name = "Character2")
    private val character1Updated = Character(id = 1, name = "Character1Updated")

    private val localCharacters = listOf(character1, character2)

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .build()

        characterLocalDataSource = CharacterLocalDataSource(database.characterDao())
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun `When storing characters, then getCharacters() returns then correctly `() = runTest {
        // When
        characterLocalDataSource.saveCharacters(localCharacters)
        val list = characterLocalDataSource.getCharacters()

        // Then
        Assert.assertEquals(localCharacters, list)
    }

    @Test
    fun `When storing characters, then getCharacterById() returns it correctly `() = runTest {
        // When
        characterLocalDataSource.saveCharacters(localCharacters)
        val character = characterLocalDataSource.getCharacterById(1)

        // Then
        Assert.assertEquals(character, character1)
    }

    @Test
    fun `When storing characters and updating one of then, then getCharacterById() returns it correctly `() = runTest {
        // When
        characterLocalDataSource.saveCharacters(localCharacters)
        characterLocalDataSource.updateCharacter(character1Updated)
        val character = characterLocalDataSource.getCharacterById(1)

        // Then
        Assert.assertEquals(character, character1Updated)
    }

    @Test
    fun `When storing characters and deleting one of then, then getCharacters returns it correctly `() = runTest {
        // When
        characterLocalDataSource.saveCharacters(localCharacters)
        characterLocalDataSource.deleteCharacter(1)
        val list = characterLocalDataSource.getCharacters()

        // Then
        Assert.assertTrue(list.size == 1)
        Assert.assertEquals(list[0], character2)
    }
}
