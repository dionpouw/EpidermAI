package com.jeflette.epidermai.di

import com.jeflette.epidermai.data.EpidermAIRepository
import com.jeflette.epidermai.data.remote.RemoteDataSource
import com.jeflette.epidermai.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: ApiService) = RemoteDataSource(apiService)

    @Provides
    @Singleton
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
    ) = EpidermAIRepository(remoteDataSource)
}