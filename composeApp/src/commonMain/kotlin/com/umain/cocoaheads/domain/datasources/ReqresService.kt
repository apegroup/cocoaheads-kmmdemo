package com.umain.cocoaheads.domain.datasources

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.umain.cocoaheads.JsonApiClient
import com.umain.cocoaheads.domain.models.RootResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.koin.core.component.KoinComponent

class ReqresService() : KoinComponent {
    private val client = JsonApiClient()

    @NativeCoroutines
    suspend fun getUsers(): RootResponse = client.get("https://reqres.in/api/users/").body()
}
