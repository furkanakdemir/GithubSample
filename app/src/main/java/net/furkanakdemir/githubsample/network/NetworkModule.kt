package net.furkanakdemir.githubsample.network

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideGithubService(retrofit: Retrofit): GithubService {
        return retrofit.create(GithubService::class.java)
    }

    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        return builder.client(httpClient).build()
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.apply { loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY }
        return loggingInterceptor
    }

    companion object {

        private const val baseUrl = "https://api.github.com/"
    }
}
