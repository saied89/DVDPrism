package android.saied.com.filmcompass.Network

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.response
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.platform.commons.util.ClassLoaderUtils

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

            val movies = MovieFetcher.MetacriticFetcher(httpClient).fetchMovies()

            assert(movies.isSuccess())

        }
    }

    @Test
    fun `resource is not null`() {
        println(javaClass.classLoader.getResource("."))
        val res = javaClass.classLoader.getResource("rottenTomatoSample.html")
        assertNotNull(res)
    }

}