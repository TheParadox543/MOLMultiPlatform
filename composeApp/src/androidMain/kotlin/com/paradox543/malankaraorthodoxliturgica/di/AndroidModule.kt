package com.paradox543.malankaraorthodoxliturgica.di

import com.paradox543.malankaraorthodoxliturgica.data.AssetReader
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidModule = module {
    single { AssetReader(androidContext()) }
}