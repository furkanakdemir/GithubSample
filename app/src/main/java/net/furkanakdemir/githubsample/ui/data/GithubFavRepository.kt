package net.furkanakdemir.githubsample.ui.data

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class GithubFavRepository @Inject constructor(private val sharedPreferences: SharedPreferences) :
    FavRepository {
    override fun favorite(repoId: Int) {
        sharedPreferences.edit {
            putBoolean(repoId.toString(), true)
        }
    }

    override fun unfavorite(repoId: Int) {
        if (sharedPreferences.contains(repoId.toString())) {
            sharedPreferences.edit {
                remove(repoId.toString())
            }
        }
    }

    override fun getFavorites(): List<Int> {
        val allEntries = sharedPreferences.all

        val favs = mutableListOf<Int>()
        for (entry in allEntries.entries) {
            favs.add(entry.key.toInt())
        }

        return favs
    }
}
