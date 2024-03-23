package com.example.notehub.di

import com.example.notehub.data.data_source.GoogleDataSource
import com.example.notehub.data.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideAccountRepository(
        googleDataSource: GoogleDataSource
    ) = MainRepository(googleDataSource)

    @Provides
    @Singleton
    fun provideGoogleDataSource() = GoogleDataSource()
}