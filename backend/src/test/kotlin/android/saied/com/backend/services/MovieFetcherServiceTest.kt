package android.saied.com.backend.services

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.koin.standalone.inject
import org.koin.test.KoinTest

internal class MovieFetcherServiceTest : KoinTest {

    @Test
    fun fetchMoviesTest() {
        val subject: MovieFetcherTask by inject()
        runBlocking {
            subject.initRepeatingTask()
        }
    }
}