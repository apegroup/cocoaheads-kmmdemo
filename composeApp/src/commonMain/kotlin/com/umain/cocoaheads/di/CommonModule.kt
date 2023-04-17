package com.umain.cocoaheads.di

import com.umain.cocoaheads.Greeting
import com.umain.cocoaheads.domain.datasources.GeoIpService
import com.umain.cocoaheads.domain.datasources.ReqresService
import org.koin.dsl.module

val commonModule = module {
    single<Greeting> { Greeting(get()) }
    single<ReqresService> { ReqresService() }
    single<GeoIpService> { GeoIpService() }
}
