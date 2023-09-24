package com.egeysn.basicappv2.di

import com.egeysn.basicappv2.data.repositories.LocationsRepositoryImpl
import com.egeysn.basicappv2.domain.repositories.LocationsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindLocationsRepository(
        locationsRepositoryImpl: LocationsRepositoryImpl,
    ): LocationsRepository
}
