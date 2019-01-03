package android.saied.com.backend.fetcher

import android.saied.com.common.model.OmdbDetails
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.response
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class OmdbFetcherKtTest {

    @Test
    fun `omdbfetcher sends correct request`() {
        val content = with(javaClass.classLoader.getResource("OmdpSample.json")) {
            readText()
        }
        val dummyApiKey = "dummyApiKey"

        val testEngine = MockEngine {
            when (url.toString()) {
                "http://www.omdbapi.com/?apikey=$dummyApiKey&t=cold+war" -> response(
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
        val subject = OmdbFetcher(httpClient, dummyApiKey)

        runBlocking {
            val res = subject.getOmdbDetails("cold war")
            assertEquals("1975", res.Year)
        }

    }
}