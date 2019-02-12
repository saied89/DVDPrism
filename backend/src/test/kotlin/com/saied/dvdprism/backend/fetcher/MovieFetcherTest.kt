package com.saied.dvdprism.backend.fetcher

import arrow.core.Try
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.response
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MovieFetcherTest {
    @Test
    fun `fetchMovies works`() {
        println(javaClass.getResource(".").path)
        val resource = javaClass.classLoader.getResource("metacriticSample.html")
        val content = String(resource.readBytes())
        runBlocking {
            val mockEngine = MockEngine {
                response(
                    status = HttpStatusCode.OK,
                    content = content
                )
            }
            val httpClient = HttpClient(mockEngine)

            val subject = MovieFetcher(httpClient)
            val movies = subject.fetchMovies()

            assert(movies.isSuccess())
            assertEquals(100 * subject.sources.size, (movies as Try.Success).value.size)
            assertNull(movies.value[0].userScore)
            assertEquals(60, movies.value[1].userScore)
            assert(movies.value[0].description.isNotEmpty())
        }
    }

    @Test
    fun `resource is not null`() {
        println(javaClass.classLoader.getResource("."))
        val res = javaClass.classLoader.getResource("rottenTomatoSample.html")
        assertNotNull(res)
    }

    @Test
    fun movieWithAmpersandIsParsedCorrectly() {
        val content = javaClass.classLoader.getResource("metacriticAmpersandSample.html").readText()
        val movies = parseMetacriticHtml(content)
        assertEquals(1, movies.size)
        assertEquals("The Old Man & the Gun", movies[0].name)
    }
}