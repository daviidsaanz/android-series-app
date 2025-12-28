package com.david.seriesapp.di

import android.content.Context
import com.david.seriesapp.data.local.dao.SeriesDao
import com.david.seriesapp.data.local.database.SeriesDatabase
import com.david.seriesapp.data.remote.TvSeriesApi
import com.david.seriesapp.data.repository.TvSeriesRepositoryImpl
import com.david.seriesapp.domain.repository.TvSeriesRepository
import com.david.seriesapp.utils.ConnectivityManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "c6aeee577586ba38e487b74dfede5deb"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val url = original.url.newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()
                val request = original.newBuilder()
                    .url(url)
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTvSeriesApi(retrofit: Retrofit): TvSeriesApi {
        return retrofit.create(TvSeriesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSeriesDatabase(@ApplicationContext context: Context): SeriesDatabase {
        return SeriesDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideSeriesDao(database: SeriesDatabase): SeriesDao {
        return database.seriesDao()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class RepositoryModule {

        @Binds
        @Singleton
        abstract fun bindTvSeriesRepository(
            impl: TvSeriesRepositoryImpl
        ): TvSeriesRepository
    }
    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return ConnectivityManager(context)
    }
}