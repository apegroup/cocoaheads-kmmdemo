package com.umain.cocoaheads.domain.datasources

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.umain.cocoaheads.JsonApiClient
import com.umain.cocoaheads.domain.models.LocationResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.koin.core.component.KoinComponent

class GeoIpService() : KoinComponent {
    private val client = JsonApiClient()

    @NativeCoroutines
    suspend fun getLocation(): LocationResponse = client.get("https://ipapi.co/json/").body()
}
