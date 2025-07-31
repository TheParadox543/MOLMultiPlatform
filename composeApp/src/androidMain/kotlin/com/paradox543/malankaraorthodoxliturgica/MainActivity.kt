package com.paradox543.malankaraorthodoxliturgica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.paradox543.malankaraorthodoxliturgica.di.androidModule
import com.paradox543.malankaraorthodoxliturgica.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        startKoin {
            androidLogger() // Optional: for Koin logging
            androidContext(this@MainActivity)
            modules(appModule, androidModule) // Load both modules
        }
        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}