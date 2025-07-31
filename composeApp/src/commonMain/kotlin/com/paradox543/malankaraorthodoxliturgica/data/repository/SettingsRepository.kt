package com.paradox543.malankaraorthodoxliturgica.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.paradox543.malankaraorthodoxliturgica.data.model.AppTextStyle
import com.paradox543.malankaraorthodoxliturgica.data.model.AppLanguage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class SettingsRepository constructor(
    private val dataStore: DataStore<Preferences>
) {
    // This scope is essential for stateIn to properly manage the StateFlow's lifecycle.
    // It's good practice to use a custom scope for singletons rather than Dispatchers.IO directly.
    private val repositoryScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val languageKey = stringPreferencesKey("selected_language")
    private val fontSizeKey = floatPreferencesKey("font_style")
    private val hasCompletedOnboardingKey = booleanPreferencesKey("has_completed_onboarding")
    private val songScrollStateKey = booleanPreferencesKey("song_scroll_state")

    val selectedLanguage: StateFlow<AppLanguage> = dataStore.data
        .map { preferences ->
            val code = preferences[languageKey] ?: AppLanguage.MALAYALAM.code
            AppLanguage.fromCode(code) ?: AppLanguage.MALAYALAM
        }
        .stateIn(
            scope = repositoryScope,
            // Lazily is often better than Eagerly to avoid work until needed.
            started = SharingStarted.Lazily,
            initialValue = AppLanguage.MALAYALAM
        )

    val selectedTextStyle: StateFlow<AppTextStyle> = dataStore.data // Using the injected dataStore
        .map { preferences ->
            val sizeInt = preferences[fontSizeKey] ?: 1.0f // Default to basic size
            AppTextStyle.fromScaleFactor(sizeInt)
        }
        .stateIn(
            scope = repositoryScope, // Use the long-lived scope for the repository
            started = SharingStarted.Eagerly, // Start collecting eagerly when the StateFlow is created
            initialValue = AppTextStyle.Default // Provide an initial value that will be emitted immediately
        )

    val hasCompletedOnboarding: StateFlow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[hasCompletedOnboardingKey] == true // Default to false if not set
        }
        .stateIn(
            scope = repositoryScope,
            started = SharingStarted.Eagerly, // Eagerly start collecting this vital preference
            initialValue = false // Initial value for the StateFlow
        )

    val songScrollState: StateFlow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[songScrollStateKey] == true // Default to false if not set
        }
        .stateIn(
            scope = repositoryScope,
            started = SharingStarted.Eagerly, // Eagerly start collecting this vital preference
            initialValue = false // Initial value for the StateFlow
        )

    // --- Debouncing for Font Size ---
    // Internal MutableStateFlow to trigger debounced saves for font size.
    private val _pendingFontSizeUpdate = MutableStateFlow(AppTextStyle.Default)
    private var debounceJob: Job? = null // Holds reference to the current debounce coroutine

    init {
        // Collect from the _pendingFontSizeUpdate flow and debounce writes to DataStore
        repositoryScope.launch {
            _pendingFontSizeUpdate.collectLatest { fontSizeToSave ->
                debounceJob?.cancel() // Cancel any previous pending save
                debounceJob = launch {
                    delay(200L) // Wait for 200ms after the last update
                    // Directly call your existing saveFontSize function
                    saveFontSize(fontSizeToSave)
                }
            }
        }

        // Initialize _pendingFontSizeUpdate with the current stored font size when the repository starts.
        // This ensures debouncing starts from the correct state.
        repositoryScope.launch {
            selectedTextStyle.collectLatest { currentSize ->
                _pendingFontSizeUpdate.value = currentSize
            }
        }
    }

    suspend fun saveLanguage(language: AppLanguage) {
        dataStore.edit { preferences ->
            preferences[languageKey] = language.code
        }
    }

    suspend fun saveFontSize(fontSize: AppTextStyle) {
        dataStore.edit { preferences ->
            preferences[fontSizeKey] = fontSize.scaleFactor
        }
    }

    /**
     * Call this function from your gesture detector to update the font size by one step.
     * It updates the UI immediately and triggers a debounced save to DataStore.
     * @param direction 1 for next size, -1 for previous size.
     */
    // New: Public method for stepping font size, triggering the debounced save
    fun stepFontSize(direction: Int) { // 1 for next, -1 for previous
        val current = selectedTextStyle.value // Get the current value from the publicly exposed StateFlow
        val newSize = if (direction > 0) current.next() else current.prev()
        // Update the internal _pendingFontSizeUpdate, which then triggers the debounced save
        _pendingFontSizeUpdate.value = newSize
    }

    suspend fun saveOnboardingStatus(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[hasCompletedOnboardingKey] = completed
        }
    }

    suspend fun saveSongScrollState(isHorizontal: Boolean) {
        dataStore.edit { preferences ->
            preferences[songScrollStateKey] = isHorizontal
        }
    }
}

/**
    // Helper function to get version name using context
    fun getPackageInfo(packageManager: PackageManager, packageName: String, flags: Int = 0): String? {
        return try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getPackageInfo(
                    packageName,
                    PackageManager.PackageInfoFlags.of(flags.toLong())
                ).versionName
            } else {
                packageManager.getPackageInfo(packageName, flags).versionName
            }
        } catch (e: Exception) {
            "Error: $e"
        }
    }
 */