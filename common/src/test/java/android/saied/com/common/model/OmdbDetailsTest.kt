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

        assertEquals("Monty Python and the Holy Grail", subject.title)
    }
}