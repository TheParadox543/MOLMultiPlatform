package com.paradox543.malankaraorthodoxliturgica.di

import com.paradox543.malankaraorthodoxliturgica.data.repository.PrayerRepository
import com.paradox543.malankaraorthodoxliturgica.data.AssetReader
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    // --- SINGLETONS ---
    // Use single { ... } for objects that should have only one instance in the app.

    // Provides the kotlinx.serialization Json instance as a singleton.
    single {
        Json {
            ignoreUnknownKeys = true // Important for robust parsing
            prettyPrint = true      // For readability if you ever debug JSON output
            isLenient = true        // Allows for some non-strict JSON
        }
    }

    // --- REPOSITORIES ---
    single { PrayerRepository(get(), get()) }
}
