package net.furkanakdemir.githubsample.ui.data

import net.furkanakdemir.githubsample.network.GithubService
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val githubService: GithubService,
    private val mapper: RepoDomainMapper
) : Repository {
    override suspend fun getRepos(username: String): List<Repo> {
        return githubService.search(username)
            .map {
                mapper.map(it)
            }
    }

    override suspend fun getRepo(repoId: Int): Repo {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}