package com.saied.dvdprism.backend

import arrow.core.Try
import io.ktor.client.HttpClient
import io.ktor.client.request.get

suspend fun fetchHtml(url: String, httpClient: HttpClient): Try<String> =
    with(httpClient) {
        try {
            Try.just(get(url))
        } catch (exp: Exception) {
            Try.raise(exp)
        }
    }
