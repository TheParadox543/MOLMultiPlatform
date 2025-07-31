package com.paradox543.malankaraorthodoxliturgica.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.paradox543.malankaraorthodoxliturgica.data.AssetReader
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidModule = module {
    single { AssetReader(androidContext()) }

    // Provide instance for DataStore
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = { androidContext().preferencesDataStoreFile("app_settings") }
        )
    }
}