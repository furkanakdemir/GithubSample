package net.furkanakdemir.githubsample.ui.data

interface Repository {

    suspend fun getRepos(username: String): List<Repo>

    suspend fun getRepo(repoId: Int): Repo
}