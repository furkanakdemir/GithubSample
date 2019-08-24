package net.furkanakdemir.githubsample.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.furkanakdemir.githubsample.network.NetworkModule
import net.furkanakdemir.githubsample.ui.MainActivity

@Module
abstract class ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [FragmentBuilderModule::class, NetworkModule::class])
    internal abstract fun bindMainActivity(): MainActivity
}
