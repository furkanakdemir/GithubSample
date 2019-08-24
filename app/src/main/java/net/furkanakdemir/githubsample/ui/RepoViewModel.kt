package net.furkanakdemir.githubsample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.furkanakdemir.githubsample.ui.data.FavRepository
import net.furkanakdemir.githubsample.ui.data.Repo
import net.furkanakdemir.githubsample.ui.data.Repository
import javax.inject.Inject

class RepoViewModel @Inject constructor(
    private val githubRepository: Repository,
    private val favRepository: FavRepository
) : ViewModel() {

    private val _repoLiveData = MutableLiveData<Result>()
    val repoLiveData: LiveData<Result>
        get() = _repoLiveData

    private val _repoDetailLiveData = MutableLiveData<Repo>()
    private val repoDetailLiveData: LiveData<Repo>
        get() = _repoDetailLiveData

    private val _favRepoLiveData = MutableLiveData<Boolean>()
    val favRepoLiveData: LiveData<Boolean>
        get() = _favRepoLiveData

    fun search(username: String) {

        if (username.isEmpty()) {
            _repoLiveData.value = Result.Failure()
        } else {
            _repoLiveData.value = Result.Loading

            viewModelScope.launch {
                val reposResult = searchUsername(username)
                val favs = getFavs()

                when (reposResult) {
                    is Result.Success -> {
                        mergeFavs(reposResult.repos, favs)
                        _repoLiveData.value = Result.Success(reposResult.repos)
                    }

                    is Result.Failure -> {
                        _repoLiveData.value = reposResult
                    }
                }
            }
        }
    }

    private fun mergeFavs(repos: List<Repo>, favs: List<Int>) {
        favs.forEach { favId ->
            val repo = repos.find { repo ->
                favId == repo.id
            }

            repo?.let {
                repo.isFavorite = true
            }
        }
    }

    private suspend fun getFavs() = withContext(Dispatchers.IO) {
        favRepository.getFavorites()
    }

    private suspend fun searchUsername(username: String) = withContext(Dispatchers.IO) {
        githubRepository.getRepos(username)
    }

    fun selectRepo(repo: Repo) {
        _repoDetailLiveData.value = repo
    }

    fun toggleFavorite() {
        val selectedRepo = repoDetailLiveData.value

        selectedRepo?.let {

            val isFavorite = it.isFavorite
            if (!isFavorite) {
                favRepository.favorite(it.id)
            } else {
                favRepository.unfavorite(it.id)
            }

            it.isFavorite = !isFavorite
            _favRepoLiveData.value = !isFavorite
        }
    }

    fun setFavoriteStatus() {
        _favRepoLiveData.value = repoDetailLiveData.value?.isFavorite ?: false
    }
}