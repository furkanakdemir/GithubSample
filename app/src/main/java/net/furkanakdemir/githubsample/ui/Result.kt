package net.furkanakdemir.githubsample.ui

import net.furkanakdemir.githubsample.ui.data.Repo

sealed class Result {
    object Loading : Result()
    class Success(val repos: List<Repo>) : Result()
    class Failure(val throwable: Throwable = IllegalStateException()) : Result()
}