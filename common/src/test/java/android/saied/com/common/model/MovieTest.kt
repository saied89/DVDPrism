package android.saied.com.common.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MovieTest {

    @Test
    fun `posterUrl_250p is generated correctly`() {
        val sampleInput98pUrl = "https://static.metacritic.com/images/products/movies/0/f7fab2a23f79b99f37bdef9c2f8ea182-98.jpg"
        val sample250pUrl = "https://static.metacritic.com/images/products/movies/0/f7fab2a23f79b99f37bdef9c2f8ea182-250h.jpg"
        val movie = Movie("", 0L, sampleInput98pUrl, 0)

        assertEquals(sample250pUrl, movie.posterUrl_250p)
    }
}