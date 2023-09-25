package com.egeysn.basicappv2.di

import com.egeysn.basicappv2.data.repositories.SatelliteRepositoryImpl
import com.egeysn.basicappv2.domain.repositories.SatelliteRepository
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
        locationsRepositoryImpl: SatelliteRepositoryImpl,
    ): SatelliteRepository
}
