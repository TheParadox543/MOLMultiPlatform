package com.paradox543.malankaraorthodoxliturgica

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform