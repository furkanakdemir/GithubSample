package net.furkanakdemir.githubsample.di


import android.content.Context
import dagger.Module
import dagger.Provides
import net.furkanakdemir.githubsample.GithubApplication
import net.furkanakdemir.githubsample.image.GlideImageLoader
import net.furkanakdemir.githubsample.image.ImageLoader
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: GithubApplication): Context = application

    @Provides
    @Singleton
    internal fun provideImageLoader(context: Context): ImageLoader = GlideImageLoader(context)
}
