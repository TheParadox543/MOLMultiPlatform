package com.paradox543.malankaraorthodoxliturgica.di

import com.paradox543.malankaraorthodoxliturgica.data.repository.PrayerRepository
import com.paradox543.malankaraorthodoxliturgica.data.repository.SettingsRepository
import com.paradox543.malankaraorthodoxliturgica.viewmodel.SettingsViewModel
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // --- SINGLETONS ---
    single {
        Json {
            ignoreUnknownKeys = true // Important for robust parsing
            prettyPrint = true      // For readability if you ever debug JSON output
            isLenient = true        // Allows for some non-strict JSON
        }
    }

    // --- REPOSITORIES ---
    single { PrayerRepository(get(), get()) }
    single { SettingsRepository(get()) }

    // View Models
    viewModel { SettingsViewModel(get()) }
}
