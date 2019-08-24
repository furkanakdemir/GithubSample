package net.furkanakdemir.githubsample.di

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import net.furkanakdemir.githubsample.GithubApplication
import net.furkanakdemir.githubsample.ui.RepoModule
import net.furkanakdemir.githubsample.viewmodel.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class, AndroidSupportInjectionModule::class,
        AppModule::class, ActivityBuilderModule::class, ViewModelModule::class,
        RepoModule::class]
)
interface AppComponent : AndroidInjector<GithubApplication> {

    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<GithubApplication>
}
