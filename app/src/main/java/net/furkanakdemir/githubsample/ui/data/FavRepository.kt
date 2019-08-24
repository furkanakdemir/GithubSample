package net.furkanakdemir.githubsample.ui.data

interface FavRepository {
    fun favorite(repoId: Int)
    fun unfavorite(repoId: Int)
    fun getFavorites(): List<Int>
}
