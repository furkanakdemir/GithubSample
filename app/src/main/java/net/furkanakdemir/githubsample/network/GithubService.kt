package net.furkanakdemir.githubsample.network

import net.furkanakdemir.githubsample.ui.data.RepoRaw
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("/users/{username}/repos")
    suspend fun search(
        @Query("username") username: String
    ): List<RepoRaw>


}
