package android.saied.com.filmcompass

import android.saied.com.filmcompass.di.appModule
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MovieRepositoryTest : KoinTest {

    val movieRepository: MovieRepository by inject()

    @BeforeAll
    fun setup(){
        startKoin(listOf(appModule))
    }

    @Test
    fun `html is fetched over network`() = runBlocking {
        val html = movieRepository.getRottenTomatoes()
        assert(html.isNotEmpty())
    }
}