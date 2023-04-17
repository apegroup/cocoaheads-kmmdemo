package com.umain.cocoaheads.di

import com.umain.cocoaheads.JsonApiClient
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}): KoinApplication {
    return startKoin {
        appDeclaration()
        modules(platformModule, commonModule, platformModule, networkLibInjection)
    }
}

// called by iOS etc
fun initKoin() = initKoin() {}

val networkLibInjection = module {
    single { JsonApiClient() }
}
