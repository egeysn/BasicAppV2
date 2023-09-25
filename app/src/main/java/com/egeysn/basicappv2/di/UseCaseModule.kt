package com.egeysn.basicappv2.di

import com.egeysn.basicappv2.domain.use_cases.detail.GetSatelliteDetailUseCase
import com.egeysn.basicappv2.domain.use_cases.detail.GetSatelliteDetailUseCaseImpl
import com.egeysn.basicappv2.domain.use_cases.search.GetLocationsUseCase
import com.egeysn.basicappv2.domain.use_cases.search.GetLocationsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    @Singleton
    abstract fun bindGetLocations(
        getLocationsUseCaseImpl: GetLocationsUseCaseImpl,
    ): GetLocationsUseCase

    @Binds
    @Singleton
    abstract fun bindGetSatelliteDetail(
        getSatelliteDetailUseCaseImpl: GetSatelliteDetailUseCaseImpl,
    ): GetSatelliteDetailUseCase
}
