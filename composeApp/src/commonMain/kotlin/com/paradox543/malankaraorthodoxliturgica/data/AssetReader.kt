package com.paradox543.malankaraorthodoxliturgica.data

// This "expects" that each platform will provide a way to read assets.
expect class AssetReader {
    fun readJsonAsset(fileName: String): String
}