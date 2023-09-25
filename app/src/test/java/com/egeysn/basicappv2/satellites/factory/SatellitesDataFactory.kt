package com.egeysn.basicappv2.satellites.factory

import com.egeysn.basicappv2.data.remote.models.locations.SatelliteDto
import net.bytebuddy.utility.RandomString

class SatellitesDataFactory {

    companion object {

        private fun fakeSatelliteDto() = SatelliteDto(id = 12123, false, RandomString.make())

        fun generateFakeResponse(): List<SatelliteDto> {
            val list = arrayListOf<SatelliteDto>()
            repeat(3) {
                list.add(
                    fakeSatelliteDto()
                )
            }
            return list
        }
    }
}
