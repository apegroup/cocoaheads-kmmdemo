package com.umain.cocoaheads.di

import com.umain.cocoaheads.GreetPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val androidModule = module {
    singleOf(::GreetPresenter)
    single<CoroutineScope> { CoroutineScope(Dispatchers.Default) }
}
