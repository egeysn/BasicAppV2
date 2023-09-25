package com.egeysn.basicappv2.satellites.detail

import com.egeysn.basicappv2.common.utils.Resource
import com.egeysn.basicappv2.domain.mappers.SatelliteMapper
import com.egeysn.basicappv2.domain.repositories.SatelliteRepository
import com.egeysn.basicappv2.domain.use_cases.detail.GetSatelliteDetailUseCase
import com.egeysn.basicappv2.domain.use_cases.detail.GetSatelliteDetailUseCaseImpl
import com.egeysn.basicappv2.satellites.detail.factory.SatelliteDetailFactory
import com.egeysn.newsapp.domain.util.MockHelper
import com.google.common.truth.Truth.assertThat
import kotlin.random.Random
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner.Silent::class)
class GetSatelliteDetailUseCaseTest {

    @Mock
    lateinit var repository: SatelliteRepository

    @Mock
    lateinit var mapper: SatelliteMapper

    private lateinit var getSatelliteDetailUseCase: GetSatelliteDetailUseCase

    @Before
    fun setUp() {
        getSatelliteDetailUseCase = GetSatelliteDetailUseCaseImpl(repository, mapper)
    }

    @Test
    fun `check getSatelliteDetail success - data from cache`() = runBlocking {
        val satelliteId = 1
        val cacheData = SatelliteDetailFactory.generateCacheEntity()
        val expectedData = SatelliteDetailFactory.entityToDto(cacheData)
        whenever(repository.getSatelliteDetailFromCache(satelliteId)).thenReturn(cacheData)
        whenever(mapper.entityToSatelliteDetail(cacheData)).thenReturn(expectedData)

        val result = getSatelliteDetailUseCase.getSatelliteDetail(satelliteId).toList()

        Assert.assertEquals(2, result.size) // Loading and Success states
        Assert.assertTrue(result[0] is Resource.Loading)
        Assert.assertTrue(result[1] is Resource.Success)
        Assert.assertEquals(expectedData, (result[1] as Resource.Success).data)
    }

    @Test
    fun `check getSatelliteDetail success - data from JSON`() = runBlocking {
        val satelliteId = 9402
        val cacheData = SatelliteDetailFactory.generateCacheEntity()
        val expectedData = SatelliteDetailFactory.generateFakeResponse()
        whenever(repository.getSatelliteDetailFromCache(satelliteId)).thenReturn(cacheData)
        whenever(repository.getSatelliteDetail(satelliteId)).thenReturn(expectedData)
        whenever(mapper.satelliteDetailToEntity(expectedData)).thenReturn(SatelliteDetailFactory.dtoToEntity(expectedData))
        whenever(mapper.entityToSatelliteDetail(cacheData)).thenReturn(SatelliteDetailFactory.entityToDto(cacheData))
        val result = getSatelliteDetailUseCase.getSatelliteDetail(satelliteId).toList()

        Assert.assertTrue(result[0] is Resource.Loading)
        Assert.assertEquals(expectedData, (result[1] as Resource.Success).data)
    }

    @Test
    fun `check getSatelliteDetail error - HTTP exception`() = runBlocking {
        val satelliteId = 1
        val exception = MockHelper.getHttpException()
        whenever(repository.getSatelliteDetailFromCache(satelliteId)).thenReturn(null)
        whenever(repository.getSatelliteDetail(satelliteId)).thenThrow(exception)

        val result = getSatelliteDetailUseCase.getSatelliteDetail(satelliteId).toList()

        Assert.assertEquals(2, result.size) // Loading and Error states
        Assert.assertTrue(result[0] is Resource.Loading)
        Assert.assertTrue(result[1] is Resource.Error)
    }

    @Test
    fun `check getSatelliteDetail error - IO exception`() = runBlocking {
        val satelliteId = Random.nextInt()

        whenever(repository.getSatelliteDetailFromCache(satelliteId)).thenAnswer { throw MockHelper.ioException }
        whenever(repository.getSatelliteDetail(satelliteId)).thenAnswer { throw MockHelper.ioException }
        val result = getSatelliteDetailUseCase.getSatelliteDetail(satelliteId).toList()
        assertThat(result[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(result[1]).isInstanceOf(Resource.Error::class.java)
    }
}
