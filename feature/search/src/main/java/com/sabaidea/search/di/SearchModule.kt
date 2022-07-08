package com.sabaidea.search.di

import com.sabaidea.search.data.repository.MovieRepository
import com.sabaidea.search.data.repository.MovieRepositoryImpl
import com.sabaidea.search.data.service.SearchService
import com.sabaidea.search.data.source.MovieRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object SearchModule {

    @Provides
    @ViewModelScoped
    fun provideMovieRepository(searchService: SearchService): MovieRepository =
        MovieRepositoryImpl(
            remoteDataSource = MovieRemoteDataSourceImpl(
                searchService = searchService
            )
        )

    @Provides
    @ViewModelScoped
    fun provideSearchService(retrofit: Retrofit): SearchService =
        retrofit.create(SearchService::class.java)
}