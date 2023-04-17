package com.umain.cocoaheads

class Greeting(private val platform: Platform) {

    fun greeting(): String {
        return "Hello, ${platform.name}!"
    }
}

expect fun initLogger()
