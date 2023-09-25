package com.egeysn.basicappv2.di

import com.egeysn.basicappv2.domain.use_cases.detail.GetSatelliteDetailUseCase
import com.egeysn.basicappv2.domain.use_cases.detail.GetSatelliteDetailUseCaseImpl
import com.egeysn.basicappv2.domain.use_cases.positions.GetPositionUseCase
import com.egeysn.basicappv2.domain.use_cases.positions.GetPositionUseCaseImpl
import com.egeysn.basicappv2.domain.use_cases.satellites.GetSatellitesUseCase
import com.egeysn.basicappv2.domain.use_cases.satellites.GetSatellitesUseCaseImpl
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
        getLocationsUseCaseImpl: GetSatellitesUseCaseImpl,
    ): GetSatellitesUseCase

    @Binds
    @Singleton
    abstract fun bindGetSatelliteDetail(
        getSatelliteDetailUseCaseImpl: GetSatelliteDetailUseCaseImpl,
    ): GetSatelliteDetailUseCase

    @Binds
    @Singleton
    abstract fun bindGetPosition(
        getPositionUseCaseImpl: GetPositionUseCaseImpl,
    ): GetPositionUseCase
}
