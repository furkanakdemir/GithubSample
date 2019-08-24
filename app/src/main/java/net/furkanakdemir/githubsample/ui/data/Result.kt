package net.furkanakdemir.githubsample.ui.data

sealed class Result {
    object Loading : Result()
    class Success(val repos: List<Repo>) : Result()
    class Failure(val throwable: Throwable = IllegalStateException()) : Result()
}
