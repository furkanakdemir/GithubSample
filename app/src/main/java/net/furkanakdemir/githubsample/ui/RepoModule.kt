package net.furkanakdemir.githubsample.ui

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import net.furkanakdemir.githubsample.network.GithubService
import net.furkanakdemir.githubsample.network.NetworkModule
import net.furkanakdemir.githubsample.ui.data.FavRepository
import net.furkanakdemir.githubsample.ui.data.GithubFavRepository
import net.furkanakdemir.githubsample.ui.data.GithubRepository
import net.furkanakdemir.githubsample.ui.data.RepoDomainMapper
import net.furkanakdemir.githubsample.ui.data.Repository
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class RepoModule {

    @Provides
    fun provideRepoDomainMapper(): RepoDomainMapper = RepoDomainMapper()

    @Provides
    fun provideGithubRepository(
        githubService: GithubService,
        mapper: RepoDomainMapper
    ): Repository = GithubRepository(githubService, mapper)

    @Provides
    @Singleton
    fun provideFavRepository(sharedPreferences: SharedPreferences): FavRepository =
        GithubFavRepository(sharedPreferences)

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(FILENAME_FAVORITES, MODE_PRIVATE)

    companion object {
        const val FILENAME_FAVORITES = "favorites"
    }
}
