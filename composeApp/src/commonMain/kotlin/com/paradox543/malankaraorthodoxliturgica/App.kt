package com.paradox543.malankaraorthodoxliturgica

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paradox543.malankaraorthodoxliturgica.ui.theme.MalankaraOrthodoxLiturgicaTheme
import com.paradox543.malankaraorthodoxliturgica.views.SettingsScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import malankaraorthodoxliturgica.composeapp.generated.resources.Res
import malankaraorthodoxliturgica.composeapp.generated.resources.compose_multiplatform
import org.koin.compose.currentKoinScope

@Composable
@Preview
fun App() {
    MalankaraOrthodoxLiturgicaTheme {
        SettingsScreen()
    }
}

@Composable
inline fun <reified T: ViewModel> koinViewModel(): T {
    val scope = currentKoinScope()
    return viewModel {
        scope.get<T>()
    }
}