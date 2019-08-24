package net.furkanakdemir.githubsample.ui.data

import net.furkanakdemir.githubsample.ui.Result

interface Repository {
    suspend fun getRepos(username: String): Result
}