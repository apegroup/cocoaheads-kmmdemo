package com.umain.cocoaheads

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

expect fun httpClient(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient

val DEFAULT_TIMEOUT = 20.seconds
val DEFAULT_JSON = Json {
    ignoreUnknownKeys = true
    isLenient = true
    prettyPrint = true
}

/**
 * A basic HTTP client with logging and timeout set up, and optionally request retries enabled.
 */
@Suppress("FunctionName")
fun DefaultHttpClient(
    timeout: Duration = DEFAULT_TIMEOUT,
    retries: Int? = null,
    config: HttpClientConfig<*>.() -> Unit = {},
): HttpClient {
    return httpClient() {
        configureTimeout(timeout)
        retries?.let {
            configureRetries(it)
        }
        defaultLogging()
        apply(config)
    }
}

/**
 * A basic HTTP client for requesting and handling JSON formatted data
 */
@Suppress("FunctionName")
fun JsonApiClient(
    json: Json = DEFAULT_JSON,
    config: HttpClientConfig<*>.() -> Unit = {},
): HttpClient {
    val client = DefaultHttpClient() {
        expectSuccess = true
        contentNegotiation(json, ContentType.Application.Json)
        jsonRequestHeaders()
        defaultLogging()
        apply(config)
    }
    return client
}

fun HttpClientConfig<*>.contentNegotiation(json: Json, contentType: ContentType) {
    install(ContentNegotiation) {
        json(json, contentType)
    }
}

fun HttpClientConfig<*>.jsonRequestHeaders() {
    install(DefaultRequest) {
        headers.append("Content-Type", "application/json")
        headers.append("X-Authorization", "dG9rZW4tbGl6LWFwcC0x==")
    }
}

fun HttpClientConfig<*>.defaultLogging() {
    install(Logging) {
        // level = HEADERS
        level = LogLevel.ALL
        logger = object : Logger {
            override fun log(message: String) {
                Napier.v(tag = "HTTP Client", message = message)
            }
        }
    }
}

fun HttpClientConfig<*>.configureTimeout(duration: Duration) {
    install(HttpTimeout) {
        requestTimeoutMillis = duration.inWholeMilliseconds
        socketTimeoutMillis = duration.inWholeMilliseconds
        connectTimeoutMillis = duration.inWholeMilliseconds
    }
}

fun HttpClientConfig<*>.configureRetries(retries: Int) {
    install(HttpRequestRetry) {
        maxRetries = retries
        retryIf { _, response ->
            !response.status.isSuccess()
        }

        retryOnException()

        retryOnExceptionIf { request, cause -> cause is RuntimeException }

        delayMillis { retry ->
            retry * 3000L
        }

        // Retry conditions
        modifyRequest { request ->
            request.headers.append("x-retry-count", retryCount.toString())
        }
    }
}
