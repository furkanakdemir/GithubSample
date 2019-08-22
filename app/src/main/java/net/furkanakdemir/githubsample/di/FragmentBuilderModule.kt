package net.furkanakdemir.githubsample.di


import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.furkanakdemir.githubsample.ui.RepoDetailFragment
import net.furkanakdemir.githubsample.ui.RepoListFragment

@Module
abstract class FragmentBuilderModule {

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun bindRepoListFragment(): RepoListFragment

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun bindRepoDetailFragment(): RepoDetailFragment
}
