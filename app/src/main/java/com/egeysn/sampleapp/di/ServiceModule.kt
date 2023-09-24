package com.egeysn.sampleapp.di

import com.egeysn.sampleapp.data.services.localStorage.KeyValueStore
import com.egeysn.sampleapp.data.services.localStorage.LocalStorageService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Singleton
    @Provides
    fun provideLocalStorageService(gson: Gson, keyValueStore: KeyValueStore) = LocalStorageService(gson, keyValueStore)
}
