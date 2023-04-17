package com.umain.cocoaheads

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
