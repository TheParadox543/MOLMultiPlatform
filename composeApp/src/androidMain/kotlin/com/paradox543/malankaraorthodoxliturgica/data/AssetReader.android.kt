package com.paradox543.malankaraorthodoxliturgica.data

import android.content.Context

actual class AssetReader(private val context: Context) {
    actual fun readJsonAsset(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }
}