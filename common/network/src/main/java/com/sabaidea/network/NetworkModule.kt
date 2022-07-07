package com.sabaidea.network

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        val client = OkHttpClient.Builder()
            .build()

        val converterFactory = MoshiConverterFactory.create()

        return Retrofit.Builder()
            .baseUrl("https://www.filimo.com/api/en/v1/")
            .client(client)
            .addConverterFactory(converterFactory)
            .build()
    }
}