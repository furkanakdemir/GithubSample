package net.furkanakdemir.githubsample.ui

import dagger.Module
import dagger.Provides
import net.furkanakdemir.githubsample.network.GithubService
import net.furkanakdemir.githubsample.network.NetworkModule
import net.furkanakdemir.githubsample.ui.data.GithubRepository
import net.furkanakdemir.githubsample.ui.data.RepoDomainMapper
import net.furkanakdemir.githubsample.ui.data.RepoViewMapper
import net.furkanakdemir.githubsample.ui.data.Repository

@Module(includes = [NetworkModule::class])
class RepoModule {

    @Provides
    fun provideRepoDomainMapper(): RepoDomainMapper {
        return RepoDomainMapper()
    }

    @Provides
    fun provideRepoViewMapper(): RepoViewMapper {
        return RepoViewMapper()
    }

    @Provides
    fun provideGithubRepository(
        githubService: GithubService,
        mapper: RepoDomainMapper
    ): Repository = GithubRepository(githubService, mapper)
}