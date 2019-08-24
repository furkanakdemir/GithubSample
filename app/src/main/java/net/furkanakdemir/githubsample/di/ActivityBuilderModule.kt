package net.furkanakdemir.githubsample.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.furkanakdemir.githubsample.MainActivity
import net.furkanakdemir.githubsample.network.NetworkModule

@Module
abstract class ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [FragmentBuilderModule::class, NetworkModule::class])
    internal abstract fun bindMainActivity(): MainActivity
}
