package com.egeysn.basicappv2.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.egeysn.basicappv2.data.local.entities.SatelliteDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SatelliteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSatelliteDetail(entity: SatelliteDetailEntity)

    @Query("SELECT * FROM satelliteDetail WHERE id = :id")
    fun getSatelliteDetail(id: Int): Flow<SatelliteDetailEntity?>
}
