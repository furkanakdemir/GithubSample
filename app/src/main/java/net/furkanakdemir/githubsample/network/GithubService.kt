package net.furkanakdemir.githubsample.network

import net.furkanakdemir.githubsample.ui.data.RepoRaw
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("/users/{username}/repos")
    suspend fun search(
        @Path("username") username: String
    ): List<RepoRaw>


}
