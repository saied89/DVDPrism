package android.saied.com.backend.fetcher

import android.saied.com.backend.EnvironmentPropertiesReader
import arrow.core.Failure
import arrow.core.Success
import arrow.core.Try
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.response
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class OmdbSearcherTest {

    @Test
    fun appropriateResultIsReturned() {
        val content = javaClass.classLoader.getResource("searchResult.json").readText()
        val omdbApiKey = "omdbApiKey"
        val mockEnvReader = mockk<EnvironmentPropertiesReader> {
            every { getOmdbApiKey() } returns omdbApiKey
        }
        val testEngine = MockEngine {
            when (url.toString()) {
                "http://www.omdbapi.com/?apikey=$omdbApiKey&s=halloween" ->
                    response(
                        content = content,
                        status = HttpStatusCode.OK,
                        headers = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                    )
                else -> error("Unhandled $url")
            }
        }
        val httpClient = HttpClient(testEngine) {
            install(JsonFeature) {
                serializer = JacksonSerializer()
            }
        }

        val subject = OmdbSearcher(httpClient, mockEnvReader)

        runBlocking {
            val res = subject.getImdbId("halloween", 2019)
            assertTrue { res is Try.Success }
            assertEquals("tt1502407", (res as Success).value)
        }
    }

    @Test
    fun `search error is handled gracefully`() {
        val content = javaClass.classLoader.getResource("SearchError.json").readText()
        val omdbApiKey = "omdbApiKey"
        val mockEnvReader = mockk<EnvironmentPropertiesReader> {
            every { getOmdbApiKey() } returns omdbApiKey
        }
        val testEngine = MockEngine {
            when (url.toString()) {
                "http://www.omdbapi.com/?apikey=$omdbApiKey&s=halloween" ->
                    response(
                        content = content,
                        status = HttpStatusCode.OK,
                        headers = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                    )
                else -> error("Unhandled $url")
            }
        }
        val httpClient = HttpClient(testEngine) {
            install(JsonFeature) {
                serializer = JacksonSerializer()
            }
        }

        val subject = OmdbSearcher(httpClient, mockEnvReader)

        runBlocking {
            val res = subject.getImdbId("halloween", 2019)
            assertTrue { res is Try.Failure }
            assertTrue { (res as Failure).exception is OMDBMovieNotFoundException }
        }
    }
}