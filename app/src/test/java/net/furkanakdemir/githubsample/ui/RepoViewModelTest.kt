package net.furkanakdemir.githubsample.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import net.furkanakdemir.githubsample.ext.testObserver
import net.furkanakdemir.githubsample.ui.data.Repo
import net.furkanakdemir.githubsample.ui.data.Result
import net.furkanakdemir.githubsample.ui.data.repository.FavRepository
import net.furkanakdemir.githubsample.ui.data.repository.Repository
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.hamcrest.CoreMatchers.`is` as Is

@ExperimentalCoroutinesApi
class RepoViewModelTest {

    private lateinit var repoViewModel: RepoViewModel
    private lateinit var repository: Repository
    private lateinit var favRepository: FavRepository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        repository = Mockito.mock(Repository::class.java)
        favRepository = Mockito.mock(FavRepository::class.java)
        repoViewModel = RepoViewModel(repository, favRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun search() = runBlocking {

        whenever(repository.getRepos(TEST_USERNAME)).thenReturn(Result.Success(TEST_LIST))

        val testObserver = repoViewModel.repoLiveData.testObserver()

        repoViewModel.search(TEST_USERNAME)

        delay(500)

        verify(repository).getRepos(TEST_USERNAME)

        assertThat(testObserver.observedValues.size, Is(2))
        assertEquals(testObserver.observedValues[0], Result.Loading)
        assertTrue(testObserver.observedValues[1] is Result.Success)
        assertEquals(TEST_REPO, (testObserver.observedValues[1] as Result.Success).repos[0])
    }

    @Test
    fun searchEmptyUsername() = runBlockingTest {

        val testObserver = repoViewModel.repoLiveData.testObserver()

        repoViewModel.search(TEST_USERNAME_EMPTY)

        delay(500)

        verify(repository, never()).getRepos(TEST_USERNAME_EMPTY)

        assertThat(testObserver.observedValues.size, Is(1))
        assertTrue(testObserver.observedValues[0] is Result.Failure)
    }

    @Test
    fun selectRepo() {
        val testObserver = repoViewModel.repoDetailLiveData.testObserver()
        repoViewModel.selectRepo(TEST_REPO)
        assertThat(testObserver.observedValues.size, Is(1))
        assertEquals(TEST_REPO, (testObserver.observedValues[0] as Repo))
    }

    @Test
    fun toggleFavoriteRepo() = runBlockingTest {
        val favoritedRepo = TEST_REPO
        favoritedRepo.isFavorite = true
        repoViewModel.selectRepo(favoritedRepo)

        repoViewModel.toggleFavorite()

        verify(favRepository).unfavorite(favoritedRepo.id)
        verify(favRepository, never()).favorite(favoritedRepo.id)
    }

    @Test
    fun toggleUnFavoriteRepo() = runBlockingTest {
        val unfavoritedRepo = TEST_REPO
        unfavoritedRepo.isFavorite = false
        repoViewModel.selectRepo(unfavoritedRepo)

        repoViewModel.toggleFavorite()

        verify(favRepository).favorite(unfavoritedRepo.id)
        verify(favRepository, never()).unfavorite(unfavoritedRepo.id)
    }

    @Test
    fun setFavoritedStatus() {
        val favoritedRepo = TEST_REPO
        favoritedRepo.isFavorite = true
        repoViewModel.selectRepo(favoritedRepo)
        val testObserver = repoViewModel.favRepoLiveData.testObserver()
        repoViewModel.setFavoriteStatus()

        assertThat(testObserver.observedValues.size, Is(1))
        assertTrue((testObserver.observedValues[0] as Boolean))
    }

    @Test
    fun setUnFavoritedStatus() {
        val unfavoritedRepo = TEST_REPO
        unfavoritedRepo.isFavorite = false
        repoViewModel.selectRepo(unfavoritedRepo)
        val testObserver = repoViewModel.favRepoLiveData.testObserver()
        repoViewModel.setFavoriteStatus()

        assertThat(testObserver.observedValues.size, Is(1))
        assertFalse((testObserver.observedValues[0] as Boolean))
    }

    companion object {
        private const val TEST_USERNAME = "jakewharton"
        private const val TEST_USERNAME_EMPTY = ""

        private val TEST_REPO = Repo(
            123, "name",
            "fullName",
            "description",
            false,
            "language",
            0,
            20,
            false, Repo.Owner(
                1,
                "login",
                "avatarUrl"
            )
        )

        private val TEST_LIST: List<Repo> = mutableListOf(TEST_REPO)
    }
}