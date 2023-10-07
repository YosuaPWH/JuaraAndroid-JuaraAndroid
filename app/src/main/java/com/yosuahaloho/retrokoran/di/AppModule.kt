package com.yosuahaloho.retrokoran.di

import com.yosuahaloho.retrokoran.BuildConfig
import com.yosuahaloho.retrokoran.data.local.dao.BookmarkNewsDao
import com.yosuahaloho.retrokoran.data.remote.ApiService
import com.yosuahaloho.retrokoran.data.repository.NewsRepositoryImpl
import com.yosuahaloho.retrokoran.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Yosua on 13/05/2023
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor() = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor {
                val ori = it.request()
                val urlWithApiKey = ori.url
                    .newBuilder()
                    .addQueryParameter("apiKey", BuildConfig.API_KEY)
                    .build()

                val request = ori
                    .newBuilder()
                    .url(urlWithApiKey)
                    .build()

                it.proceed(request)
            }
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideApiAuthInstance(client: OkHttpClient): ApiService =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideNewsRepository(api: ApiService, dao: BookmarkNewsDao): NewsRepository {
        return NewsRepositoryImpl(api, dao)
    }

}