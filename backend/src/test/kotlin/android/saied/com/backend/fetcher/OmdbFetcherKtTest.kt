package android.saied.com.backend.fetcher

import android.saied.com.backend.EnviromentPropertiesReader
import arrow.core.Failure
import arrow.core.Success
import arrow.core.Try
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.response
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.http.*
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class OmdbFetcherKtTest {

    @Test
    fun `omdbfetcher returns data on OK response`() {
        val content = javaClass.classLoader.getResource("OmdpSample2.json").readText()
        val dummyApiKey = "dummyApiKey"
        val mockEnvReader = mockk<EnviromentPropertiesReader>()
        every { mockEnvReader.getOmdbApiKey() } returns dummyApiKey
        val testEngine = MockEngine {
            when (url.toString()) {
                "http://www.omdbapi.com/?apikey=$dummyApiKey&t=test" ->
                    response(
                        status = HttpStatusCode.OK,
                        content = content,
                        headers = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                    )
                else -> {
                    error("Unhandled $url")
                }
            }
        }
        val httpClient = HttpClient(testEngine) {
            install(JsonFeature) {
                serializer = JacksonSerializer()
            }
        }

        val subject = OmdbFetcher(httpClient, mockEnvReader)

        runBlocking {
            val res = subject.getOmdbDetailsByTitle("test")
            assert(res is Try.Success)
            assertEquals("2017", (res as Success).value.year)
        }
    }

    @Test
    fun `omdbFetcher returns appropiate error when movie is not found`() {
        val content = javaClass.classLoader.getResource("OmdbNotFound.json").readText()
        val testEngine = MockEngine {
            response(
                content,
                HttpStatusCode.OK,
                headers = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
            )
        }
        val httpClient = HttpClient(testEngine) {
            install(JsonFeature) {
                serializer = JacksonSerializer()
            }
        }
        val mockEnvReader = mockk<EnviromentPropertiesReader>(relaxed = true)
        val subject = OmdbFetcher(httpClient, mockEnvReader)

        runBlocking {
            val res = subject.getOmdbDetailsByTitle("")

            assert(res is Try.Failure)
            assert((res as Failure).exception is OMDBMovieNotFoundException)
        }
    }
}