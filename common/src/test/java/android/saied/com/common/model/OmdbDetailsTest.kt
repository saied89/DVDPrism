package android.saied.com.common.model

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class OmdbDetailsTest {

    @Test
    fun `is parsed correctly`() {
        val objectMapper = ObjectMapper().registerModule(KotlinModule())

        val content = javaClass.classLoader.getResource("OmdpSample.json").readText()
        val subject = objectMapper.readValue(content, OmdbDetails::class.java)

        subject.run {
            assertEquals("Graham Chapman,John Cleese,Eric Idle,Terry Gilliam", actors)
            assertEquals("Terry Gilliam,Terry Jones", director)
            assertEquals("Adventure,Comedy,Fantasy", genre)
            assertEquals("Monty Python and the Holy Grail", title)
            assertEquals(OmdbType.MOVIE, type)
            assertEquals("tt0071853", imdbID)
            assertEquals("91 min", runtime)
            assertEquals(1975, year)
            assertTrue(poster.isNotEmpty() && poster.isNotBlank())
            assertTrue(response)
        }
    }
}