package com.paradox543.malankaraorthodoxliturgica.data.model


enum class AppTextStyle(val scaleFactor: Float, val displayName: String) {
    Small(0.85f, "Small"),
    Default(1.0f, "Default"),
    Large(1.15f, "Large"),
    VeryLarge(1.30f, "Very Large");

    // Helper to get the next size in the enum list, or stay at current if already at max
    fun next(): AppTextStyle = entries.getOrElse(ordinal + 1) { this }

    // Helper to get the previous size in the enum list, or stay at current if already at min
    fun prev(): AppTextStyle = entries.getOrElse(ordinal - 1) { this }

    companion object {
        // Helper to find the enum from a stored float value, defaulting to Default.
        fun fromScaleFactor(scale: Float): AppTextStyle {
            return entries.find { it.scaleFactor == scale } ?: Default
        }
    }
}