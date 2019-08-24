package net.furkanakdemir.githubsample.ui.data

import net.furkanakdemir.githubsample.network.GithubService
import net.furkanakdemir.githubsample.ui.Result
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val githubService: GithubService,
    private val mapper: RepoDomainMapper
) : Repository {
    override suspend fun getRepos(username: String): Result {
        return try {
            val x = githubService.search(username)
                .map {
                    mapper.map(it)
                }

            Result.Success(x)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}