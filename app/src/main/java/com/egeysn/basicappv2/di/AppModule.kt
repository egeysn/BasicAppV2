package com.egeysn.basicappv2.di

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.egeysn.basicappv2.BuildConfig
import com.egeysn.basicappv2.data.services.localStorage.KeyValueStore
import com.egeysn.basicappv2.data.services.localStorage.SharedPreferencesKeyValueStore
import com.egeysn.basicappv2.domain.mappers.LocationsMapper
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
    fun provideLocationsMapper(): LocationsMapper = LocationsMapper()
}
