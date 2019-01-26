package android.saied.com.backend

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


internal class EnviromentPropertiesReaderTest {

    @Test
    fun `apiKey env value is read correctly`() {
        val objectMapper = ObjectMapper()
        val subject = EnvironmentPropertiesReader(objectMapper, "rest-client.private.env.json.sample")

        assertEquals("API_KEY", subject.getOmdbApiKey())
    }

}