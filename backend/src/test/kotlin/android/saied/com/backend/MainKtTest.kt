package android.saied.com.backend

import android.saied.com.backend.task.MovieFetcherTask
import android.saied.com.common.model.Movie
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.mongodb.client.MongoCollection
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.test.KoinTest

val dummyMovie get() = Movie("saied", 0, "", 0, 0, "")

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
internal class MainKtTest : KoinTest {

    @Test
    fun `test root`() {
        val testModule = module {

            single {
                mockk<MongoCollection<Movie>>(relaxed = true)
            }

            single {
                val mockRepository = mockk<MovieRepository>()
                every { mockRepository.getMovies() } returns listOf(dummyMovie)
                mockRepository
            }

            single {
                mockk<MovieFetcherTask>(relaxed = true)
            }
        }
        startKoin(listOf(testModule))

        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/").apply {
                kotlin.test.assertEquals(HttpStatusCode.OK, response.status())
                kotlin.test.assertEquals(listOf(dummyMovie), parseMovie(response.content))
            }
        }
    }

    fun Movie.getJson(): String = ObjectMapper().registerModule(KotlinModule()).writeValueAsString(this)
    private fun parseMovie(jsonStr: String?): List<Movie> {
        val objectMapper = ObjectMapper().registerModule(KotlinModule())
        val typeFactory = objectMapper.typeFactory
        return objectMapper.readValue<List<Movie>>(
            jsonStr,
            typeFactory.constructCollectionType(List::class.java, Movie::class.java)
        )
    }

    @AfterEach
    fun tearDown() {
        stopKoin()
    }
}