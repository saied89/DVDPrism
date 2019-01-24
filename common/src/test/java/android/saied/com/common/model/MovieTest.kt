package android.saied.com.common.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MovieTest {

    @Test
    fun `posterUrl_250p is generated correctly`() {
        val sampleInput98pUrl = "https://static.metacritic.com/images/products/movies/0/f7fab2a23f79b99f37bdef9c2f8ea182-98.jpg"
        val sample250pUrl = "https://static.metacritic.com/images/products/movies/0/f7fab2a23f79b99f37bdef9c2f8ea182-250h.jpg"
        val movie = Movie("", 0L, sampleInput98pUrl, 0, 0, "")

        assertEquals(sample250pUrl, movie.getPosterUrl250p())
    }

    @Test
    fun `correct meta indication is returned`() {
        val subject = Movie(
            name = "test",
            releaseDate = 0L,
            posterUrl_89p = "",
            metaScore = null,
            userScore = null,
            description = ""
        )
        assertEquals(ScoreIndication.TBD, subject.getMetaIndication())
        assertEquals(ScoreIndication.POSITIVE, subject.copy(metaScore = 61).getMetaIndication())
        assertEquals(ScoreIndication.MIXED, subject.copy(metaScore = 60).getMetaIndication())
        assertEquals(ScoreIndication.NEGATIVE, subject.copy(metaScore = 39).getMetaIndication())
    }

    @Test
    fun `correct user indication is returned`() {
        val subject = Movie(
            name = "test",
            releaseDate = 0L,
            posterUrl_89p = "",
            metaScore = null,
            userScore = null,
            description = ""
        )
        assertEquals(ScoreIndication.TBD, subject.getUserIndication())
        assertEquals(ScoreIndication.POSITIVE, subject.copy(metaScore = 61).getUserIndication())
        assertEquals(ScoreIndication.MIXED, subject.copy(metaScore = 60).getUserIndication())
        assertEquals(ScoreIndication.NEGATIVE, subject.copy(metaScore = 39).getUserIndication())
    }




}