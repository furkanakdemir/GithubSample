package net.furkanakdemir.githubsample.ui.data.repository

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import net.furkanakdemir.githubsample.network.GithubService
import net.furkanakdemir.githubsample.ui.data.Repo
import net.furkanakdemir.githubsample.ui.data.RepoDomainMapper
import net.furkanakdemir.githubsample.ui.data.RepoRaw
import net.furkanakdemir.githubsample.ui.data.Result
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.exceptions.base.MockitoException
import org.mockito.junit.MockitoJUnitRunner
import org.hamcrest.CoreMatchers.`is` as Is
import org.mockito.Mockito.`when` as whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GithubRepositoryTest {

    private lateinit var repository: Repository
    private lateinit var githubService: GithubService

    private val domainMapper: RepoDomainMapper = mock(RepoDomainMapper::class.java)

    @Before
    fun setup() {
        githubService = mock {
            onBlocking {
                search(TEST_USERNAME)
            } doReturn TEST_LIST
        }
        repository = GithubRepository(githubService, domainMapper)
    }

    @Test
    fun testValidRepos() = runBlockingTest {
        whenever(domainMapper.map(TEST_REPO_RAW)).thenReturn(TEST_REPO)
        repository = GithubRepository(githubService, domainMapper)

        val actual: Result = repository.getRepos(TEST_USERNAME)
        verify(githubService).search(TEST_USERNAME)
        val expected = listOf(TEST_REPO)

        assertTrue(actual is Result.Success)
        assertThat((actual as Result.Success).repos, Is(expected))
    }

    @Test
    fun testMapFailure() = runBlockingTest {
        whenever(domainMapper.map(TEST_REPO_RAW)).thenThrow(IllegalStateException())
        repository = GithubRepository(githubService, domainMapper)

        val actual: Result = repository.getRepos(TEST_USERNAME)
        verify(githubService).search(TEST_USERNAME)

        assertTrue(actual is Result.Failure)
    }

    @Test
    fun testServiceFailure() = runBlockingTest {
        val service = mock<GithubService> {
            onBlocking {
                search(TEST_USERNAME)
            } doThrow MockitoException("Service Failure")
        }
        repository = GithubRepository(service, domainMapper)

        val actual: Result = repository.getRepos(TEST_USERNAME)

        assertTrue(actual is Result.Failure)
    }

    companion object {
        private const val TEST_USERNAME = "jakewharton"

        val TEST_REPO = Repo(
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

        private val TEST_REPO_RAW = RepoRaw(
            123, "name",
            "fullName",
            "description",
            false,
            "language",
            0,
            0,
            20, RepoRaw.OwnerRaw(
                "avatarUrl",
                1,
                "login"
            )
        )

        private val TEST_LIST: List<RepoRaw> = mutableListOf(TEST_REPO_RAW)
    }
}
