package com.egeysn.basicappv2.satellites

import com.egeysn.basicappv2.common.utils.Resource
import com.egeysn.basicappv2.common.utils.UiText
import com.egeysn.basicappv2.domain.mappers.SatelliteMapper
import com.egeysn.basicappv2.domain.repositories.SatelliteRepository
import com.egeysn.basicappv2.domain.use_cases.satellites.GetSatellitesUseCase
import com.egeysn.basicappv2.domain.use_cases.satellites.GetSatellitesUseCaseImpl
import com.egeysn.basicappv2.satellites.factory.SatellitesDataFactory
import com.egeysn.newsapp.domain.util.MockHelper
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import net.bytebuddy.utility.RandomString
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetSatellitesUseCaseTest {

    private lateinit var getSatellitesUseCase: GetSatellitesUseCase

    @Mock
    private lateinit var newsRepository: SatelliteRepository

    @Mock
    private lateinit var mapper: SatelliteMapper

    @Before
    fun setUp() {
        getSatellitesUseCase = GetSatellitesUseCaseImpl(newsRepository, mapper)
    }

    @Test
    fun `check getSatellites() success case`() = runBlocking {
        // when
        val query = RandomString.make()
        whenever(newsRepository.getSatellites(query)).thenAnswer { SatellitesDataFactory.generateFakeResponse() }
        val result = getSatellitesUseCase.getSatellites(query)
        val flowList = result.toList()
        // then
        assertThat(flowList[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(flowList[1]).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `check getSatellites() http exception error case`() = runBlocking {
        // when
        val query = RandomString.make()
        whenever(newsRepository.getSatellites(query)).thenAnswer { throw MockHelper.getHttpException() }
        val result = getSatellitesUseCase.getSatellites(query)
        val flowList = result.toList()
        // then
        assertThat(flowList[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(flowList[1]).isInstanceOf(Resource.Error::class.java)
        assertThat(flowList[1].message).isInstanceOf(UiText.DynamicString::class.java)
    }

    @Test
    fun `check getSatellites() io exception error case`() = runBlocking {
        // when
        val query = RandomString.make()
        whenever(newsRepository.getSatellites(query)).thenAnswer { throw MockHelper.ioException }
        val result = getSatellitesUseCase.getSatellites(query)
        val flowList = result.toList()
        // then

        assertThat(flowList[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(flowList[1]).isInstanceOf(Resource.Error::class.java)
        assertThat(flowList[1].message).isInstanceOf(UiText.StringResource::class.java)
    }
}
