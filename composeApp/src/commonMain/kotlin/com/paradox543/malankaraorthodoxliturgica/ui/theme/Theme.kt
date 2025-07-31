package com.paradox543.malankaraorthodoxliturgica.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import com.paradox543.malankaraorthodoxliturgica.data.model.AppTextStyle

// This would be your base typography, defined in Type.kt
val AppTypography = Typography(
    /* Define your default styles here, e.g.:
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    */
)

private val LightColorScheme = lightColorScheme(
    /* Your colors */
)

/**
 * A helper function to scale an entire typography set.
 */
@Composable
private fun scaleTypography(typography: Typography, scaleFactor: Float): Typography {
    return typography.copy(
        displayLarge = typography.displayLarge.scale(scaleFactor),
        displayMedium = typography.displayMedium.scale(scaleFactor),
        displaySmall = typography.displaySmall.scale(scaleFactor),
        headlineLarge = typography.headlineLarge.scale(scaleFactor),
        headlineMedium = typography.headlineMedium.scale(scaleFactor),
        headlineSmall = typography.headlineSmall.scale(scaleFactor),
        titleLarge = typography.titleLarge.scale(scaleFactor),
        titleMedium = typography.titleMedium.scale(scaleFactor),
        titleSmall = typography.titleSmall.scale(scaleFactor),
        bodyLarge = typography.bodyLarge.scale(scaleFactor),
        bodyMedium = typography.bodyMedium.scale(scaleFactor),
        bodySmall = typography.bodySmall.scale(scaleFactor),
        labelLarge = typography.labelLarge.scale(scaleFactor),
        labelMedium = typography.labelMedium.scale(scaleFactor),
        labelSmall = typography.labelSmall.scale(scaleFactor)
    )
}

/**
 * Helper extension function to scale a single text style.
 */
private fun TextStyle.scale(scaleFactor: Float): TextStyle {
    return this.copy(
        fontSize = this.fontSize * scaleFactor,
        lineHeight = this.lineHeight * scaleFactor
    )
}


@Composable
fun MalankaraOrthodoxLiturgicaTheme(
    darkTheme: Boolean = false, // or isSystemInDarkTheme()
    // Accept the user's text style preference as a parameter
    appTextStyle: AppTextStyle = AppTextStyle.Default,
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme // Add logic for dark theme if needed

    // Create the scaled typography based on the user's choice
    val scaledTypography = scaleTypography(AppTypography, appTextStyle.scaleFactor)

    MaterialTheme(
        colorScheme = colorScheme,
        // Apply the scaled typography to the theme
        typography = scaledTypography,
        content = content
    )
}
