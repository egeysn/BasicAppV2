package com.egeysn.basicappv2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.egeysn.basicappv2.data.local.entities.SatelliteDetailEntity

@Database(
    entities = [SatelliteDetailEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class SatelliteDatabase : RoomDatabase() {
    abstract val dao: SatelliteDao
}
