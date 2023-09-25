package com.egeysn.basicappv2.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.egeysn.basicappv2.BuildConfig
import com.egeysn.basicappv2.data.local.SatelliteDao
import com.egeysn.basicappv2.data.local.SatelliteDatabase
import com.egeysn.basicappv2.data.services.localStorage.KeyValueStore
import com.egeysn.basicappv2.data.services.localStorage.SharedPreferencesKeyValueStore
import com.egeysn.basicappv2.domain.mappers.SatelliteMapper
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.*

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideGson() = Gson()

    @Singleton
    @Provides
    fun provideSharedPreferencesKeyValueStore(preferences: SharedPreferences): KeyValueStore =
        SharedPreferencesKeyValueStore(preferences)

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences =
        appContext.getSharedPreferences(
            BuildConfig.APPLICATION_ID,
            Activity.MODE_PRIVATE,
        )

    @Provides
    @Singleton
    fun provideLocationsMapper(): SatelliteMapper = SatelliteMapper()

    @Provides
    @Singleton
    fun provideSatelliteDatabase(app: Application, gson: Gson): SatelliteDatabase {
        return Room.databaseBuilder(app, SatelliteDatabase::class.java, "satellite_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(db: SatelliteDatabase): SatelliteDao = db.dao
}
