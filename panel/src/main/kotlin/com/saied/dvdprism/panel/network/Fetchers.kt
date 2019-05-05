package com.saied.dvdprism.panel.network

import com.saied.dvdprism.panel.models.MoviePanel
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol

object Fetchers {
    private const val SERVER_ADDRESS = "localhost"
    private const val SERVER_PORT = 8080
    suspend fun fetchDetaillessMovies(client: HttpClient): List<MoviePanel> =
        client.get {
            url {
                protocol = URLProtocol.HTTP
                host = SERVER_ADDRESS
                port = SERVER_PORT
                encodedPath = "detailless"
            }
        }
}