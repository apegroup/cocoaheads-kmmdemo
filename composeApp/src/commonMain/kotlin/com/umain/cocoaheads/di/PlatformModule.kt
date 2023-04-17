package com.umain.cocoaheads.di

import com.umain.cocoaheads.Platform
import org.koin.dsl.module

val platformModule = module {
    single<Platform> { get() }
}
