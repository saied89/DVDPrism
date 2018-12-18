package android.saied.com.filmcompass

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockHttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MovieRepositoryTest {

    @Test
    fun `http status Error response results in Failure`() = runBlocking {
        val httpMockEngine = MockEngine {
            MockHttpResponse(
                call,
                HttpStatusCode.Unauthorized
            )
        }
        val movieRepository = MovieRepository()
    }

}