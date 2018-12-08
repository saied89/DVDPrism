package android.saied.com.filmcompass

import arrow.core.Try
import arrow.core.orNull
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockHttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.io.ByteReadChannel
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MovieRepositoryTest {

    @Test
    fun `no network returns failure`() = runBlocking {

    }

    @Test
    fun `http status code OK response results in Success`() = runBlocking {
        val sampleResStr = "Hello, Saied"
        val httpMockEngine = MockEngine {
            MockHttpResponse(
                call,
                HttpStatusCode.OK,
                ByteReadChannel(sampleResStr.toByteArray(Charsets.UTF_8))
            )
        }
        val movieRepository = MovieRepository(HttpClient(httpMockEngine))

        val res = movieRepository.getRottenTomatoes()

        assert(res is Try.Success)
        val resStr : String = res.orNull() ?: ""
        assert(resStr.isNotEmpty())
        assertEquals(sampleResStr, resStr)
    }

    @Test
    fun `http status Error response results in Failure`() = runBlocking {
        val httpMockEngine = MockEngine {
            MockHttpResponse(
                call,
                HttpStatusCode.Unauthorized
            )
        }
        val movieRepository = MovieRepository(HttpClient(httpMockEngine))

        val res = movieRepository.getRottenTomatoes()

        assert(res is Try.Failure)
    }

}