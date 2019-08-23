package net.furkanakdemir.githubsample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.furkanakdemir.githubsample.ui.data.Repo
import net.furkanakdemir.githubsample.ui.data.Repository
import javax.inject.Inject

class RepoViewModel @Inject constructor(private val githubRepository: Repository) : ViewModel() {


    private val _repoLiveData = MutableLiveData<List<Repo>>()
    val repoLiveData: LiveData<List<Repo>>
        get() = _repoLiveData


    fun search(username: String) {

        viewModelScope.launch {
            val repos = searchUsername(username)
            _repoLiveData.value = repos
        }
    }

    private suspend fun searchUsername(username: String) = withContext(Dispatchers.IO) {
        githubRepository.getRepos(username)
    }
}