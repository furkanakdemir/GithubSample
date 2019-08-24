package net.furkanakdemir.githubsample.ui.data.repository

import net.furkanakdemir.githubsample.ui.data.Result

interface Repository {
    suspend fun getRepos(username: String): Result
}
