package com.roman.bookapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform