package net.furkanakdemir.githubsample.ui.data.repository

import android.content.SharedPreferences
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.hamcrest.CoreMatchers.`is` as Is

@RunWith(MockitoJUnitRunner::class)
class FavRepositoryTest {

    private lateinit var favRepository: FavRepository

    private val preferences: SharedPreferences = mock(SharedPreferences::class.java)
    private val editor = mock(SharedPreferences.Editor::class.java)

    @Before
    fun setup() {
        `when`(editor.putBoolean(anyString(), anyBoolean())).thenReturn(editor)
        `when`(preferences.edit()).thenReturn(editor)
        favRepository = GithubFavRepository(preferences)
    }

    @Test
    fun favorite() {
        favRepository.favorite(1234)
        verify(editor).putBoolean("1234", true)
    }

    @Test
    fun unfavoriteExistRepo() {
        `when`(preferences.contains("1234")).thenReturn(true)
        favRepository.unfavorite(1234)
        verify(editor).remove("1234")
    }

    @Test
    fun unfavoriteNonExistRepo() {
        `when`(preferences.contains("1234")).thenReturn(false)
        favRepository.unfavorite(1234)
        verify(editor, never()).remove("1234")
    }

    @Test
    fun getFavorites() {
        `when`(preferences.all).thenReturn(mapOf("1234" to true, "4567" to true, "7890" to true))
        val actual = favRepository.getFavorites()
        val expected = listOf(1234, 4567, 7890)
        assertThat(actual, Is(expected))
    }

    @Test
    fun getEmptyFavorites() {
        `when`(preferences.all).thenReturn(mapOf<String, Boolean>())
        val actual = favRepository.getFavorites()
        val expected = emptyList<Int>()
        assertThat(actual, Is(expected))
    }
}