package com.umain.cocoaheads

class GreetPresenter(private val greeting: Greeting) {
    fun print() = greeting.greeting()
}
